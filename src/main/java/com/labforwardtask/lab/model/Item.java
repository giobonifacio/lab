package com.labforwardtask.lab.model;

import org.springframework.data.annotation.Id;

import java.util.Map;

public class Item {

    @Id
    private String id;

    private Map<String, Object> itemAttributes;

    public String getId() {
        return id;
    }

    public Map<String, Object> getItemAttributes() {
        return itemAttributes;
    }

    public void setItemAttributes(Map<String, Object> itemAttributes) {
        this.itemAttributes = itemAttributes;
    }
}
