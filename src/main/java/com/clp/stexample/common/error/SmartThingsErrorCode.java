package com.clp.stexample.common.error;

import lombok.Getter;

@Getter
public enum SmartThingsErrorCode {
    PATTERN_ERROR(400, "Pattern Error", "The client has provided input that does not match the expected pattern."),
    CONSTRAINT_VIOLATION_ERROR(422, "Constraint Violation Error", "The client has provided input that has violated one or more constraints."),
    NOT_NULL_ERROR(422, "Not Null Error", "The client has provided a null input for a field that is required to be non-null."),
    NULL_ERROR(422, "Null Error", "The client has provided an input for a field that is required to be null."),
    NOT_EMPTY_ERROR(422, "Not Empty Error", "The client has provided an empty input for a field that is required to be non-empty."),
    SIZE_ERROR(400, "Size Error", "The client has provided a value that does not meet size restrictions."),
    UNEXPECTED_ERROR(500, "Unexpected Error", "A non-recoverable error condition has occurred. A problem occurred on the SmartThings server that is no fault of the client."),
    UNPROCESSABLE_ENTITY_ERROR(422, "Unprocessable Entity Error", "The client has sent a malformed request body."),
    TOO_MANY_REQUEST_ERROR(429, "Too Many Request Error", "The client issued too many requests too quickly."),
    LIMIT_ERROR(422, "Limit Error", "The client has exceeded certain limits an API enforces."),
    UNSUPPORTED_OPERATION_ERROR(400, "Unsupported Operation Error", "The client has issued a request to a feature that currently isn't supported by the SmartThings platform.");

    private final int statusCode;
    private final String name;
    private final String description;

    SmartThingsErrorCode(int statusCode, String name, String description) {
        this.statusCode = statusCode;
        this.name = name;
        this.description = description;
    }

}
