package com.labforwardtask.lab.util;

import com.labforwardtask.lab.dto.ItemDTO;
import com.labforwardtask.lab.model.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemTestUtil {

    public static final String ID = "id";
    public static final String CATEGORY_ID = "a0b00001423";
    public static final String NAME_VALUE = "keyboard";
    public static final int SIZE_VALUE = 20;
    public static final String KEY_NAME = "name";
    public static final String KEY_SIZE = "size";

    public static ItemDTO createDefaultItemDTO() {
        ItemDTO dto = new ItemDTO();

        dto.setItemAttributes(createDefaultAttributesMap());
        dto.setCategoryId(CATEGORY_ID);

        return dto;
    }

    public static Map<String, Object> createDefaultAttributesMap() {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put(KEY_NAME, NAME_VALUE);
        attributes.put(KEY_SIZE, SIZE_VALUE);

        return attributes;
    }

    public static Item createDefaultItem() {
        Item item = new Item();

        item.setItemAttributes(createDefaultAttributesMap());

        return item;
    }
}
