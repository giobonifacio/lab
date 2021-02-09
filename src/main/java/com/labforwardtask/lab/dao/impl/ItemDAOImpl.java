package com.labforwardtask.lab.dao.impl;

import com.labforwardtask.lab.dto.ItemDTO;
import com.labforwardtask.lab.dto.mapper.ItemMapper;
import com.labforwardtask.lab.model.Item;
import com.labforwardtask.lab.repository.ItemRepository;
import com.labforwardtask.lab.dao.ItemDAO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;


@Component
public class ItemDAOImpl implements ItemDAO {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    public ItemDAOImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public List<ItemDTO> findAll() {
        return itemMapper.to(itemRepository.findAll());
    }

    public ItemDTO insert(ItemDTO itemDTO) {
        Item item = itemMapper.from(itemDTO);
        return itemMapper.to(itemRepository.insert(item));
    }

    public ItemDTO findById(String id) {
        Item item = itemRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return itemMapper.to(item);
    }

    public ItemDTO update(ItemDTO itemDTO) {
        Item item = itemMapper.from(itemDTO);
        return itemMapper.to(itemRepository.save(item));
    }
}
