package com.labforwardtask.lab.model;

import org.springframework.data.annotation.Id;

public class AttributeDefinition {

    @Id
    private String id;

    private String key;

    private String description;

    private Boolean isRequired;

    private Boolean shouldCheckType;

    private String type;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequired() {
        return isRequired;
    }

    public void setRequired(Boolean required) {
        isRequired = required;
    }

    public Boolean getShouldCheckType() {
        return shouldCheckType;
    }

    public void setShouldCheckType(Boolean shouldCheckType) {
        this.shouldCheckType = shouldCheckType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
