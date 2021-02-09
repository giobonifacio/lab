package com.labforwardtask.lab.exception;

import java.util.List;

import static java.util.Arrays.asList;

public class LabPreconditionFailedException extends RuntimeException {
    public static final String PRECONDITION_ERROR_MESSAGE = "Operation could not be completed due to one or more failed preconditions!";
    private final List<String> failedConditions;

    public LabPreconditionFailedException(List<String> failedConditions) {
        super(PRECONDITION_ERROR_MESSAGE);
        this.failedConditions = failedConditions;
    }
    public LabPreconditionFailedException(String failedConditions) {
        super(PRECONDITION_ERROR_MESSAGE);
        this.failedConditions = asList(failedConditions);
    }

    public List<String> getFailedConditions() {
        return failedConditions;
    }
}
