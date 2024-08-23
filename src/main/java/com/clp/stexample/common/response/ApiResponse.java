package com.clp.stexample.common.response;

import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
public abstract class ApiResponse {

    private final String status;
    private final String message;
    private final ZonedDateTime timestamp;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = ZonedDateTime.now(ZoneId.systemDefault());
    }

}