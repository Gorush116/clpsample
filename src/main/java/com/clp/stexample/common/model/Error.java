package com.clp.stexample.common.model;

import com.clp.stexample.common.error.HTTPErrorCode;

public class Error {
    private final HTTPErrorCode errorCode;

    public Error(HTTPErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public int getStatusCode() {
        return errorCode.getStatusCode();
    }

    public String getName() {
        return errorCode.getName();
    }

    public String getDescription() {
        return errorCode.getDescription();
    }

    @Override
    public String toString() {
        return "Error{" +
                "code=" + getStatusCode() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}

