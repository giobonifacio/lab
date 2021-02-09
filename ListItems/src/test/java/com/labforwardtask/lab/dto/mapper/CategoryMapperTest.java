package com.labforwardtask.lab.dto.mapper;

import com.labforwardtask.lab.dto.CategoryDTO;
import com.labforwardtask.lab.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.labforwardtask.lab.util.CategoryTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CategoryMapperTest {

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private AttributeDefinitionMapper attributeDefinitionMapper;

    @InjectMocks
    private CategoryMapper categoryMapper;

    @Test
    void from() {
        CategoryDTO dto = createDefaultCategoryDTO();

        Category category = categoryMapper.from(dto);

        assertThat(category).hasNoNullFieldsOrPropertiesExcept(ID);

        assertThat(category.getName()).isEqualTo(CATEGORY_NAME);
        assertThat(category.getDescription()).isEqualTo(CATEGORY_DESCRIPTION);
        assertThat(category.getItems()).isEmpty();
        assertThat(category.getAttributeDefinitions()).isEmpty();
    }

    @Test
    void fromNullValues() {
        Category category = categoryMapper.from(new CategoryDTO());

        assertThat(category.getId()).isNull();
        assertThat(category.getName()).isNull();
        assertThat(category.getDescription()).isNull();
        assertThat(category.getAttributeDefinitions()).isEmpty();
        assertThat(category.getItems()).isEmpty();
    }

    @Test
    void to() {
        Category category = createDefaultCategory();

        CategoryDTO dto = categoryMapper.to(category);

        assertThat(dto).hasNoNullFieldsOrPropertiesExcept(ID);

        assertThat(dto.getName()).isEqualTo(CATEGORY_NAME);
        assertThat(dto.getDescription()).isEqualTo(CATEGORY_DESCRIPTION);
        assertThat(dto.getItems()).isEmpty();
        assertThat(dto.getAttributeDefinitions()).isEmpty();
    }

    @Test
    void toNullValues() {
        CategoryDTO dto = categoryMapper.to(new Category());

        assertThat(dto.getId()).isNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getDescription()).isNull();
        assertThat(dto.getAttributeDefinitions()).isEmpty();
        assertThat(dto.getItems()).isEmpty();
    }

    @Test
    void toList() {
        List<Category> list = new ArrayList<>();
        list.add(createDefaultCategory());
        list.add(new Category());

        List<CategoryDTO> dtoList = categoryMapper.to(list);
        assertThat(dtoList)
                .hasSize(2)
                .extracting(CategoryDTO::getName)
                .contains(CATEGORY_NAME)
                .containsNull();

    }
}