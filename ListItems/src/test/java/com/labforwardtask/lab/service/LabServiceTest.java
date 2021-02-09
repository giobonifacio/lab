package com.labforwardtask.lab.service;

import com.labforwardtask.lab.dao.CategoryDAO;
import com.labforwardtask.lab.dao.ItemDAO;
import com.labforwardtask.lab.dto.AttributeDefinitionDTO;
import com.labforwardtask.lab.dto.CategoryDTO;
import com.labforwardtask.lab.dto.ItemDTO;
import com.labforwardtask.lab.exception.LabPreconditionFailedException;
import com.labforwardtask.lab.model.AttributeDefinition;
import com.labforwardtask.lab.util.AttributeDefinitionTestUtil;
import com.labforwardtask.lab.util.CategoryTestUtil;
import com.labforwardtask.lab.util.ItemTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.labforwardtask.lab.util.AttributeDefinitionTestUtil.*;
import static com.labforwardtask.lab.util.CategoryTestUtil.*;
import static com.labforwardtask.lab.util.ItemTestUtil.*;
import static com.labforwardtask.lab.util.ItemTestUtil.SIZE_VALUE;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LabServiceTest {

    @Mock
    private CategoryDAO categoryDAO;

    @Mock
    private ItemDAO itemDAO;

    @InjectMocks
    private LabService labService;


    @Test
    void findAllCategories() {
        when(categoryDAO.findAll()).thenReturn(
                asList(createDefaultCategoryDTO(), createCategoryDTO("Second category"))
        );

        List<CategoryDTO> allCategories = labService.findAllCategories();

        assertThat(allCategories)
            .hasSize(2)
            .extracting(CategoryDTO::getName)
            .contains(CATEGORY_NAME, "Second category");
    }

    @Test
    void findCategoryById() {
        when(categoryDAO.findById(any())).thenReturn(createDefaultCategoryDTO());

        CategoryDTO categoryDTO = labService.findCategoryById(ItemTestUtil.CATEGORY_ID);

        assertThat(categoryDTO.getName()).isEqualTo(CATEGORY_NAME);
    }

    @Test
    void insertCategory() {
        CategoryDTO defaultCategoryDTO = createDefaultCategoryDTO();
        defaultCategoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));

        when(categoryDAO.insert(any())).thenReturn(defaultCategoryDTO);

        CategoryDTO categoryDTO = labService.insertCategory(defaultCategoryDTO);

        assertThat(categoryDTO.getName()).isEqualTo(CATEGORY_NAME);
        assertThat(categoryDTO.getAttributeDefinitions()).hasSize(1);
    }

    @Test
    void insertCategoryWithoutAttributeDefinition() {
        CategoryDTO defaultCategoryDTO = createDefaultCategoryDTO();

        assertThatThrownBy(() -> {
            labService.insertCategory(defaultCategoryDTO);
            }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions", asList("Cannot create category without attributes definition!"));
    }

    @Test
    void insertCategoryWithoutName() {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setDescription(CATEGORY_DESCRIPTION);
        categoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));

        assertThatThrownBy(() -> {
                    labService.insertCategory(categoryDTO);
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions", asList("Field 'Name' cannot be empty!"));
    }

    @Test
    void insertCategoryWithoutDescription() {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setName(CATEGORY_DESCRIPTION);
        categoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));

        assertThatThrownBy(() -> {
                    labService.insertCategory(categoryDTO);
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions", asList("Field 'Description' cannot be empty!"));
    }

    @Test
    void insertCategoryNull() {
        assertThatThrownBy(() -> {
                    labService.insertCategory(null);
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions", asList("Category was not informed!"));
    }

    @Test
    void insertCategoryWithItems() {

        CategoryDTO defaultCategoryDTO = createDefaultCategoryDTO();
        defaultCategoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));
        defaultCategoryDTO.setItems(singletonList(createDefaultItemDTO()));

        assertThatThrownBy(() -> {
                    labService.insertCategory(defaultCategoryDTO);
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions", asList("Items should be added using the POST Item API!"));
    }

    @Test
    void insertCategoryWithMissingAttributeDefinitionValues() {
        CategoryDTO defaultCategoryDTO = createDefaultCategoryDTO();

        AttributeDefinitionDTO attributeDefinitionDTO = new AttributeDefinitionDTO();
        attributeDefinitionDTO.setDescription(DEFAULT_DESCRIPTION);

        defaultCategoryDTO.setAttributeDefinitions(singletonList(attributeDefinitionDTO));

        assertThatThrownBy(() -> {
                    labService.insertCategory(defaultCategoryDTO);
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
                .isInstanceOf(LabPreconditionFailedException.class)
                .hasFieldOrPropertyWithValue("failedConditions",
                        asList("Field 'Key' cannot be empty for Attribute Definition!",
                                "Field 'Required' cannot be empty for Attribute Definition!"));
    }

    @Test
    void insertCategoryWithAttributeDefinitionFieldShouldCheckTypeButNoTypeDefined() {
        CategoryDTO defaultCategoryDTO = createDefaultCategoryDTO();

        AttributeDefinitionDTO attributeDefinitionDTO = createDefaultAttributeDefinitionDTO();
        attributeDefinitionDTO.setShouldCheckType(true);
        attributeDefinitionDTO.setType(null);

        defaultCategoryDTO.setAttributeDefinitions(singletonList(attributeDefinitionDTO));

        assertThatThrownBy(() -> {
                    labService.insertCategory(defaultCategoryDTO);
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
                .isInstanceOf(LabPreconditionFailedException.class)
                .hasFieldOrPropertyWithValue("failedConditions",
                asList("Field 'Type' cannot be empty for Attribute Definition when field 'shouldCheckType' is true!"));
    }

    @Test
    void insertItem() {
        CategoryDTO categoryDTO = createDefaultCategoryDTO();
        categoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));
        categoryDTO.setId(CATEGORY_ID);

        ItemDTO itemDTO = createDefaultItemDTO();
        itemDTO.getItemAttributes().put(DEFAULT_KEY, "Attribute value");

        when(categoryDAO.findById(CATEGORY_ID)).thenReturn(categoryDTO);
        when(categoryDAO.update(categoryDTO)).thenReturn(categoryDTO);
        when(itemDAO.insert(itemDTO)).thenReturn(itemDTO);

        ItemDTO insertedItem = labService.insertItem(itemDTO);

        assertThat(insertedItem.getItemAttributes()).contains(
                entry(DEFAULT_KEY, "Attribute value"),
                entry(KEY_NAME, NAME_VALUE),
                entry(KEY_SIZE, SIZE_VALUE)
        );
    }

    @Test
    void insertItemNull() {
        assertThatThrownBy(() -> {
                    labService.insertItem(null);
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions", asList("Item was not informed!"));
    }

    @Test
    void insertItemWithoutAttributes() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setCategoryId(CATEGORY_ID);

        assertThatThrownBy(() -> {
                    labService.insertItem(itemDTO);
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions",
        asList("Item cannot have empty attributes! Item attributes need to comply by category attribute definition criteria!"));
    }

    @Test
    void insertItemWithoutCategory() {
        ItemDTO itemDTO = createDefaultItemDTO();
        itemDTO.setCategoryId(null);

        assertThatThrownBy(() -> {
                    labService.insertItem(itemDTO);
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions",
        asList("It is mandatory to inform a category in which the item should be included!"));

    }

    @Test
    void insertItemMissingRequiredAttributesFromCategory() {
        CategoryDTO categoryDTO = createDefaultCategoryDTO();
        categoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));
        categoryDTO.setId(CATEGORY_ID);

        ItemDTO itemDTO = createDefaultItemDTO();

        when(categoryDAO.findById(CATEGORY_ID)).thenReturn(categoryDTO);

        assertThatThrownBy(() -> {
                    labService.insertItem(itemDTO);
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
                .isInstanceOf(LabPreconditionFailedException.class)
                .hasFieldOrPropertyWithValue("failedConditions",
                        asList(format("Item missing attribute %s", AttributeDefinitionTestUtil.DEFAULT_KEY)));
    }

    @Test
    void updateItem() {
        CategoryDTO categoryDTO = createDefaultCategoryDTO();
        categoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));
        categoryDTO.setId(CATEGORY_ID);

        ItemDTO itemDTO = createDefaultItemDTO();
        itemDTO.setId("0001");
        itemDTO.getItemAttributes().put(DEFAULT_KEY, "Attribute value");

        categoryDTO.setItems(singletonList(itemDTO));

        assertThat(itemDTO.getId()).isEqualTo("0001");
        assertThat(itemDTO.getItemAttributes()).contains(
                entry(DEFAULT_KEY, "Attribute value"),
                entry(KEY_NAME, NAME_VALUE),
                entry(KEY_SIZE, SIZE_VALUE)
        );

        when(categoryDAO.findById(any())).thenReturn(categoryDTO);

        itemDTO.getItemAttributes().replace(DEFAULT_KEY, "Changed attribute value");

        ItemDTO insertedItem = labService.updateItem(itemDTO, "0001");

        assertThat(itemDTO.getId()).isEqualTo("0001");
        assertThat(insertedItem.getItemAttributes()).contains(
                entry(DEFAULT_KEY, "Changed attribute value"),
                entry(KEY_NAME, NAME_VALUE),
                entry(KEY_SIZE, SIZE_VALUE)
        );
    }

    @Test
    void updateItemNull() {
        assertThatThrownBy(() -> {
                    labService.updateItem(null, "0001");
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
                .isInstanceOf(LabPreconditionFailedException.class)
                .hasFieldOrPropertyWithValue("failedConditions", asList("Item was not informed!"));
    }

    @Test
    void updateItemWithoutItemId() {
        ItemDTO defaultItemDTO = createDefaultItemDTO();
        assertThatThrownBy(() -> {
            labService.updateItem(defaultItemDTO, "");
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
                .isInstanceOf(LabPreconditionFailedException.class)
                .hasFieldOrPropertyWithValue("failedConditions", asList("Item 'id' is missing!"));
    }

    @Test
    void updateItemWithoutAttributes() {
        CategoryDTO categoryDTO = createDefaultCategoryDTO();
        categoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));
        categoryDTO.setId(CATEGORY_ID);

        ItemDTO itemDTO = createDefaultItemDTO();
        itemDTO.setId("0001");
        itemDTO.getItemAttributes().put(DEFAULT_KEY, "Attribute value");

        categoryDTO.setItems(singletonList(itemDTO));

        assertThat(itemDTO.getId()).isEqualTo("0001");
        assertThat(itemDTO.getItemAttributes()).contains(
                entry(DEFAULT_KEY, "Attribute value"),
                entry(KEY_NAME, NAME_VALUE),
                entry(KEY_SIZE, SIZE_VALUE)
        );

        itemDTO.setItemAttributes(null);

        assertThatThrownBy(() -> {
                    labService.updateItem(itemDTO, "0001");
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions",
        asList("Item cannot have empty attributes! Item attributes need to comply by category attribute definition criteria!"));

    }

    @Test
    void updateItemWithoutCategory() {
        CategoryDTO categoryDTO = createDefaultCategoryDTO();
        categoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));
        categoryDTO.setId(CATEGORY_ID);

        ItemDTO itemDTO = createDefaultItemDTO();
        itemDTO.setId("0001");
        itemDTO.getItemAttributes().put(DEFAULT_KEY, "Attribute value");

        categoryDTO.setItems(singletonList(itemDTO));

        assertThat(itemDTO.getId()).isEqualTo("0001");
        assertThat(itemDTO.getItemAttributes()).contains(
                entry(DEFAULT_KEY, "Attribute value"),
                entry(KEY_NAME, NAME_VALUE),
                entry(KEY_SIZE, SIZE_VALUE)
        );

        itemDTO.setCategoryId("");

        assertThatThrownBy(() -> {
                    labService.updateItem(itemDTO, "0001");
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
                .isInstanceOf(LabPreconditionFailedException.class)
                .hasFieldOrPropertyWithValue("failedConditions",
                        asList("It is mandatory to inform a category in which the item should be included!"));
    }

    @Test
    void updateItemOnCategoryWithoutItem() {
        CategoryDTO categoryDTO = createDefaultCategoryDTO();
        categoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));
        categoryDTO.setId(CATEGORY_ID);
        categoryDTO.setItems(null);

        ItemDTO itemDTO = createDefaultItemDTO();
        itemDTO.setId("0001");
        itemDTO.getItemAttributes().put(DEFAULT_KEY, "Attribute value");

        assertThat(itemDTO.getId()).isEqualTo("0001");
        assertThat(itemDTO.getItemAttributes()).contains(
                entry(DEFAULT_KEY, "Attribute value"),
                entry(KEY_NAME, NAME_VALUE),
                entry(KEY_SIZE, SIZE_VALUE)
        );

        when(categoryDAO.findById(any())).thenReturn(categoryDTO);

        assertThatThrownBy(() -> {
                    labService.updateItem(itemDTO, "0001");
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions",
        asList("Provided category does not have any item added!"));
    }

    @Test
    void updateItemOnCategoryWithMultipleMatchingItems() {
        CategoryDTO categoryDTO = createDefaultCategoryDTO();
        categoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));
        categoryDTO.setId(CATEGORY_ID);
        categoryDTO.setItems(new ArrayList<>());

        ItemDTO itemDTO = createDefaultItemDTO();
        itemDTO.setId("0001");
        itemDTO.getItemAttributes().put(DEFAULT_KEY, "Attribute value");
        categoryDTO.getItems().add(itemDTO);

        ItemDTO repeatedItem = createDefaultItemDTO();
        repeatedItem.setId("0001");
        repeatedItem.getItemAttributes().put(DEFAULT_KEY, "Attribute value 2");
        categoryDTO.getItems().add(repeatedItem);

        assertThat(itemDTO.getId()).isEqualTo("0001");
        assertThat(repeatedItem.getId()).isEqualTo("0001");

        when(categoryDAO.findById(any())).thenReturn(categoryDTO);

        assertThatThrownBy(() -> {
                    labService.updateItem(itemDTO, "0001");
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions",
        asList("Provided category return multiple items matching item id!"));
    }

    @Test
    void updateItemOnCategoryWithoutMatchingItem() {
        CategoryDTO categoryDTO = createDefaultCategoryDTO();
        categoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));
        categoryDTO.setId(CATEGORY_ID);

        ItemDTO itemDTO = createDefaultItemDTO();
        itemDTO.setId("0001");
        itemDTO.setCategoryId(CATEGORY_ID);
        itemDTO.getItemAttributes().put(DEFAULT_KEY, "Attribute value");
        categoryDTO.setItems(singletonList(itemDTO));

        ItemDTO secondItem = createDefaultItemDTO();
        secondItem.setId("0002");
        secondItem.setCategoryId(CATEGORY_ID);
        secondItem.getItemAttributes().put(DEFAULT_KEY, "Attribute value 2");

        assertThat(itemDTO.getId()).isEqualTo("0001");
        assertThat(secondItem.getId()).isEqualTo("0002");

        when(categoryDAO.findById(any())).thenReturn(categoryDTO);

        assertThatThrownBy(() -> {
                    labService.updateItem(secondItem, "0002");
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions",
        asList("Provided category does not have any Item that matches provided 'itemId'!"));
    }

    @Test
    void updateItemMissingRequiredAttributesFromCategory() {
        CategoryDTO categoryDTO = createDefaultCategoryDTO();
        categoryDTO.setAttributeDefinitions(singletonList(createDefaultAttributeDefinitionDTO()));
        categoryDTO.setId(CATEGORY_ID);

        ItemDTO itemDTO = createDefaultItemDTO();
        itemDTO.setId("0001");
        itemDTO.getItemAttributes().put(DEFAULT_KEY, "Attribute value");

        categoryDTO.setItems(singletonList(itemDTO));

        assertThat(itemDTO.getId()).isEqualTo("0001");
        assertThat(itemDTO.getItemAttributes()).contains(
                entry(DEFAULT_KEY, "Attribute value"),
                entry(KEY_NAME, NAME_VALUE),
                entry(KEY_SIZE, SIZE_VALUE)
        );

        when(categoryDAO.findById(any())).thenReturn(categoryDTO);

        itemDTO.getItemAttributes().remove(DEFAULT_KEY);

        assertThatThrownBy(() -> {
                    labService.updateItem(itemDTO, "0001");
                }
        ).hasMessage(LabPreconditionFailedException.PRECONDITION_ERROR_MESSAGE)
        .isInstanceOf(LabPreconditionFailedException.class)
        .hasFieldOrPropertyWithValue("failedConditions",
        asList((format("Item missing attribute %s", DEFAULT_KEY))));
    }
}