package com.example.exception.model;

import java.util.ArrayList;
import java.util.List;

public class ExceptionRepresenter {
    private String errorMessage;
    private List<String> additionalDetails = new ArrayList<>();

    public ExceptionRepresenter() {

    }

    public ExceptionRepresenter(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ExceptionRepresenter(String errorMessage, List<String> additionalDetails) {
        this.errorMessage = errorMessage;
        this.additionalDetails = additionalDetails;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(List<String> additionalDetails) {
        this.additionalDetails = additionalDetails;
    }
}
