package com.clp.stexample.common.response;

import lombok.Getter;

@Getter
public class ApiSuccessResponse<T> extends ApiResponse {

    private final T data;

    private static final String SUCCESS_STATUS = "success";
    private static final String DEFAULT_SUCCESS_MESSAGE = "Operation successful";

    public ApiSuccessResponse(T data) {
        super(SUCCESS_STATUS, DEFAULT_SUCCESS_MESSAGE);
        this.data = data;
    }

    public static <T> ApiSuccessResponse<T> of(T data) {
        return new ApiSuccessResponse<>(data);
    }
}
