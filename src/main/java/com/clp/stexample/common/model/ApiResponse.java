package com.clp.stexample.common.model;

import com.clp.stexample.common.error.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class ApiResponse<T> {
    private final T data;
    private final int statusCode;
    private final String message;
    private final ErrorResponse errorResponse;

    private ApiResponse(T data, int statusCode, String message, ErrorResponse errorResponse) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
        this.errorResponse = errorResponse;
    }

    public static <T> ApiResponse<T> success(T data, HttpStatusCode statusCode) {
        return new ApiResponse<>(data, statusCode.value(), "Success", new ErrorResponse());
    }

    public static <T> ApiResponse<T> error(HttpStatusCode statusCode, String message) {
        return new ApiResponse<>(null, statusCode.value(), message, new ErrorResponse());
    }

    public static <T> ApiResponse<T> error(HttpStatusCode statusCode, ErrorResponse errorResponse) {
        return new ApiResponse<>(null, statusCode.value(), errorResponse.getError().getMessage(), errorResponse);
    }

}
