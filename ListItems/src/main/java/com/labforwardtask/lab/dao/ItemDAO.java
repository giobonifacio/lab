package com.labforwardtask.lab.dao;

import com.labforwardtask.lab.dto.ItemDTO;

import java.util.List;

public interface ItemDAO {
    public List<ItemDTO> findAll();
    public ItemDTO findById(String id);
    public ItemDTO insert(ItemDTO item);
    public ItemDTO update(ItemDTO item);
}
