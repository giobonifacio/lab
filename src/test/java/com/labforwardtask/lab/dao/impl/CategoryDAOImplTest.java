package com.labforwardtask.lab.dao.impl;

import com.labforwardtask.lab.dto.CategoryDTO;
import com.labforwardtask.lab.dto.mapper.CategoryMapper;
import com.labforwardtask.lab.model.Category;
import com.labforwardtask.lab.repository.CategoryRepository;
import com.labforwardtask.lab.util.CategoryTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.labforwardtask.lab.util.CategoryTestUtil.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryDAOImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryDAOImpl categoryService;

    @Test
    void findAll() {
        List<Category> categories = asList(createDefaultCategory(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.to(categories)).thenReturn(asList(createDefaultCategoryDTO(), new CategoryDTO()));

        List<CategoryDTO> categoryList = categoryService.findAll();

        assertThat(categoryList)
                .hasSize(2)
                .extracting(CategoryDTO::getName)
                .contains(CATEGORY_NAME)
                .containsNull();
    }

    @Test
    void insert() {
        Category defaultCategory = CategoryTestUtil.createDefaultCategory();
        CategoryDTO defaultCategoryDTO = CategoryTestUtil.createDefaultCategoryDTO();

        when(categoryRepository.insert(defaultCategory)).thenReturn(defaultCategory);
        when(categoryMapper.from(defaultCategoryDTO)).thenReturn(defaultCategory);
        when(categoryMapper.to(defaultCategory)).thenReturn(defaultCategoryDTO);

        CategoryDTO insertedCategory = categoryService.insert(defaultCategoryDTO);

        assertThat(insertedCategory).hasNoNullFieldsOrPropertiesExcept(ID);

        assertThat(insertedCategory.getName()).isEqualTo(CATEGORY_NAME);
        assertThat(insertedCategory.getDescription()).isEqualTo(CATEGORY_DESCRIPTION);
        assertThat(insertedCategory.getItems()).isEmpty();
        assertThat(insertedCategory.getAttributeDefinitions()).isEmpty();
    }

}