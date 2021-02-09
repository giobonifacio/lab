package com.labforwardtask.lab.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeDefinitionDTO {

    private String key;
    private String description;
    private Boolean isRequired;
    private Boolean shouldCheckType;
    private String type;

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
