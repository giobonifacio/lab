package com.labforwardtask.lab.util;

import com.labforwardtask.lab.dto.CategoryDTO;
import com.labforwardtask.lab.model.Category;

import java.util.ArrayList;

public class CategoryTestUtil {
    public static final String ID = "id";
    public static final String CATEGORY_NAME = "category name";
    public static final String CATEGORY_DESCRIPTION = "category description";

    public static CategoryDTO createDefaultCategoryDTO() {
        CategoryDTO dto = new CategoryDTO();

        dto.setName(CATEGORY_NAME);
        dto.setDescription(CATEGORY_DESCRIPTION);
        dto.setItems(new ArrayList<>());
        dto.setAttributeDefinitions(new ArrayList<>());

        return dto;
    }

    public static CategoryDTO createCategoryDTO(String name) {
        CategoryDTO dto = new CategoryDTO();

        dto.setName(name);
        dto.setDescription(CATEGORY_DESCRIPTION);
        dto.setItems(new ArrayList<>());
        dto.setAttributeDefinitions(new ArrayList<>());

        return dto;
    }

    public static Category createDefaultCategory() {
        Category category = new Category();

        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);
        category.setItems(new ArrayList<>());
        category.setAttributeDefinitions(new ArrayList<>());

        return category;
    }

}
