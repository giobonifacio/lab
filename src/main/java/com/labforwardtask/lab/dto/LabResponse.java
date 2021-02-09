package com.labforwardtask.lab.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LabResponse {

    private Object entity;
    private Exception exception;

    public LabResponse(Object entity) {
        this.entity = entity;
    }

    public LabResponse(Object entity, Exception exception) {
        this.entity = entity;
        this.exception = exception;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
