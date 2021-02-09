package com.labforwardtask.lab.dao.impl;

import com.labforwardtask.lab.dto.CategoryDTO;
import com.labforwardtask.lab.dto.mapper.CategoryMapper;
import com.labforwardtask.lab.model.Category;
import com.labforwardtask.lab.repository.CategoryRepository;
import com.labforwardtask.lab.dao.CategoryDAO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;


@Component
public class CategoryDAOImpl implements CategoryDAO {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryDAOImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDTO> findAll() {
        return categoryMapper.to(categoryRepository.findAll());
    }

    public CategoryDTO insert(CategoryDTO categoryDTO) {
        Category category = categoryMapper.from(categoryDTO);
        return categoryMapper.to(categoryRepository.insert(category));
    }

    public CategoryDTO findById(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return categoryMapper.to(category);
    }

    public CategoryDTO update(CategoryDTO categoryDTO) {
        Category category = categoryMapper.from(categoryDTO);
        return categoryMapper.to(categoryRepository.save(category));
    }

    public void eraseAll() {
        categoryRepository.deleteAll();
    }
}
