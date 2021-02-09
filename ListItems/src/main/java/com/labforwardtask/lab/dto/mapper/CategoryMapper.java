package com.labforwardtask.lab.dto.mapper;

import com.labforwardtask.lab.dto.CategoryDTO;
import com.labforwardtask.lab.model.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
public class CategoryMapper {

    private final AttributeDefinitionMapper attributeDefinitionMapper;

    private final ItemMapper itemMapper;

    public CategoryMapper(AttributeDefinitionMapper attributeDefinitionMapper, ItemMapper itemMapper) {
        this.attributeDefinitionMapper = attributeDefinitionMapper;
        this.itemMapper = itemMapper;
    }

    public Category from(CategoryDTO dto) {
        Category category = new Category();
        category.setDescription(dto.getDescription());
        category.setName(dto.getName());
        category.setItems(itemMapper.from(dto.getItems()));
        category.setAttributeDefinitions(attributeDefinitionMapper.from(dto.getAttributeDefinitions()));

        return category;
    }

    public List<CategoryDTO> to(List<Category> categories) {
        if (isNull(categories)) {
            return new ArrayList<>();
        }

        return categories.stream().map(this::to).collect(toList());
    }

    public CategoryDTO to(Category category) {
        CategoryDTO dto = new CategoryDTO();

        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setItems(itemMapper.to(category.getItems()));
        dto.setAttributeDefinitions(attributeDefinitionMapper.to(category.getAttributeDefinitions()));

        return dto;
    }
}
