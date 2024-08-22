package com.clp.stexample.common.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public abstract class CommonResponse {
    private final String status;
    private final String message;
    private final LocalDateTime timestamp;

    public CommonResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}