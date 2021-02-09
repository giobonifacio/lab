package com.labforwardtask.lab.service;

import com.labforwardtask.lab.dao.CategoryDAO;
import com.labforwardtask.lab.dao.ItemDAO;
import com.labforwardtask.lab.dto.AttributeDefinitionDTO;
import com.labforwardtask.lab.dto.CategoryDTO;
import com.labforwardtask.lab.dto.ItemDTO;
import com.labforwardtask.lab.exception.LabPreconditionFailedException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class LabService {

    private final CategoryDAO categoryDAO;
    private final ItemDAO itemDAO;


    public LabService(CategoryDAO categoryDAO, ItemDAO itemDAO) {
        this.categoryDAO = categoryDAO;
        this.itemDAO = itemDAO;
    }

    public List<CategoryDTO> findAllCategories() {
        return categoryDAO.findAll();
    }

    public CategoryDTO findCategoryById(String categoryId) {
        return categoryDAO.findById(categoryId);
    }

    public CategoryDTO insertCategory(CategoryDTO categoryDTO) {
        checkRequiredCategoryValues(categoryDTO);

        return categoryDAO.insert(categoryDTO);
    }

    public ItemDTO insertItem(ItemDTO itemDTO) {
        checkRequiredItemValues(itemDTO);

        CategoryDTO category = categoryDAO.findById(itemDTO.getCategoryId());

        checkAttributeDefinitionCriteria(category, itemDTO);

        addItemToCategory(category, itemDTO);

        return itemDTO;
    }

    public ItemDTO updateItem(ItemDTO itemDTO, String id) {
        if (isEmpty(id)) {
            throw new LabPreconditionFailedException("Item 'id' is missing!");
        }

        checkRequiredItemValues(itemDTO);

        CategoryDTO category = categoryDAO.findById(id);

        ItemDTO itemToUpdate = getItemFromCategoryAndCheckValues(itemDTO, category);

        itemToUpdate.setItemAttributes(itemDTO.getItemAttributes());

        checkAttributeDefinitionCriteria(category, itemToUpdate);

        categoryDAO.update(category);

        return itemToUpdate;
    }

    private ItemDTO getItemFromCategoryAndCheckValues(ItemDTO itemDTO, CategoryDTO category) {
        if (isNull(category.getItems())) {
            throw new LabPreconditionFailedException("Provided category does not have any item added!");
        }

        List<ItemDTO> itemsMatchingId = category.getItems().stream().filter(
                item -> item.getId().equals(itemDTO.getId())).collect(Collectors.toList()
        );

        if (itemsMatchingId.size() > 1) {
            throw new LabPreconditionFailedException("Provided category return multiple items matching item id!");
        }

        return itemsMatchingId.stream().findFirst().orElseThrow(
                () -> new LabPreconditionFailedException("Provided category does not have any Item that matches provided 'itemId'!")
        );
    }

    public void eraseAll() {
        categoryDAO.eraseAll();
    }

    private void checkAttributeDefinitionCriteria(CategoryDTO category, ItemDTO item) {
        List<String> errors = new ArrayList<>();
        category.getAttributeDefinitions().forEach(
                attr -> {
                    boolean containsKey = item.getItemAttributes().containsKey(attr.getKey());
                    if (TRUE.equals(attr.getRequired()) && FALSE.equals(containsKey)) {
                        errors.add(format("Item missing attribute %s", attr.getKey()));
                    }
                }
        );

        if (!errors.isEmpty()) {
            throw new LabPreconditionFailedException(errors);
        }
    }

    private void addItemToCategory(CategoryDTO category, ItemDTO item) {
        if (isNull(category.getItems())) {
            category.setItems(new ArrayList<>());
        }

        category.getItems().add(itemDAO.insert(item));
        categoryDAO.update(category);
    }


    private void checkRequiredCategoryValues(CategoryDTO categoryDTO) {
        List<String> failedConditions = new ArrayList<>();

        if (isNull(categoryDTO)) {
            failedConditions.add("Category was not informed!");
            throw new LabPreconditionFailedException(failedConditions);
        }

        if (isEmpty(categoryDTO.getName())) {
            failedConditions.add("Field 'Name' cannot be empty!");
        }

        if (isEmpty(categoryDTO.getDescription())) {
            failedConditions.add("Field 'Description' cannot be empty!");
        }

        if (isNull(categoryDTO.getAttributeDefinitions()) || categoryDTO.getAttributeDefinitions().isEmpty()) {
            failedConditions.add("Cannot create category without attributes definition!");
        }

        if (nonNull(categoryDTO.getItems()) && !categoryDTO.getItems().isEmpty()) {
            failedConditions.add("Items should be added using the POST Item API!");
        }

        checkRequiredAttributeDefinitionValues(categoryDTO.getAttributeDefinitions(), failedConditions);

        if (!failedConditions.isEmpty()) {
            throw new LabPreconditionFailedException(failedConditions);
        }
    }

    private void checkRequiredAttributeDefinitionValues(List<AttributeDefinitionDTO> attributeDefinitions,
                                                                List<String> failedConditions) {

        for(AttributeDefinitionDTO attr : attributeDefinitions) {

            if (isEmpty(attr.getKey())) {
                failedConditions.add("Field 'Key' cannot be empty for Attribute Definition!");
            }

            if (isEmpty(attr.getDescription())) {
                failedConditions.add("Field 'Description' cannot be empty for Attribute Definition!");
            }

            if (isNull(attr.getRequired())) {
                failedConditions.add("Field 'Required' cannot be empty for Attribute Definition!");
            }

            if (TRUE.equals(attr.getShouldCheckType()) && isEmpty(attr.getType())) {
                failedConditions.add("Field 'Type' cannot be empty for Attribute Definition when field 'shouldCheckType' is true!");
            }
        }
    }

    private void checkRequiredItemValues(ItemDTO itemDTO) {
        List<String> failedConditions = new ArrayList<>();

        if (isNull(itemDTO)) {
            failedConditions.add("Item was not informed!");
            throw new LabPreconditionFailedException(failedConditions);
        }

        if (isNull(itemDTO.getItemAttributes()) || itemDTO.getItemAttributes().isEmpty()) {
            failedConditions.add("Item cannot have empty attributes! Item attributes need to comply by category attribute definition criteria!");
        }

        if (isEmpty(itemDTO.getCategoryId())) {
            failedConditions.add("It is mandatory to inform a category in which the item should be included!");
        }

        if (!failedConditions.isEmpty()) {
            throw new LabPreconditionFailedException(failedConditions);
        }
    }

}
