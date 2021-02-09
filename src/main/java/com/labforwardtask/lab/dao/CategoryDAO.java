package com.labforwardtask.lab.dao;

import com.labforwardtask.lab.dto.CategoryDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryDAO {

    List<CategoryDTO> findAll();
    CategoryDTO insert(CategoryDTO category);
    CategoryDTO findById(String id);
    CategoryDTO update(CategoryDTO category);
    void eraseAll();
}
