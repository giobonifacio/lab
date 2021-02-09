package com.labforwardtask.lab.dto.mapper;

import com.labforwardtask.lab.dto.ItemDTO;
import com.labforwardtask.lab.model.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
public class ItemMapper {

    public List<Item> from(List<ItemDTO> dtos) {
        if (isNull(dtos)) {
            return new ArrayList<>();
        }

        return dtos.stream().map(this::from).collect(Collectors.toList());
    }

    public Item from(ItemDTO dto) {
        Item item = new Item();
        item.setItemAttributes(dto.getItemAttributes());

        return item;
    }

    public List<ItemDTO> to(List<Item> items) {
        if (isNull(items)) {
            return new ArrayList<>();
        }

        return items.stream().map(this::to).collect(toList());
    }

    public ItemDTO to(Item item) {
        ItemDTO dto = new ItemDTO();

        dto.setId(item.getId());
        dto.setItemAttributes(item.getItemAttributes());

        return dto;
    }

}
