package com.labforwardtask.lab.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDTO {

    private String id;
    private String categoryId;
    private Map<String, Object> itemAttributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Map<String, Object> getItemAttributes() {
        return itemAttributes;
    }

    public void setItemAttributes(Map<String, Object> itemAttributes) {
        this.itemAttributes = itemAttributes;
    }
}
