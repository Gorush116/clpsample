package com.clp.stexample.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SuccessResponse<T> extends CommonResponse {

    private final T data;

    private static final String SUCCESS_STATUS = "success";
    private static final String DEFAULT_SUCCESS_MESSAGE = "Operation successful";

    // 생성자
    public SuccessResponse(T data) {
        super(SUCCESS_STATUS, DEFAULT_SUCCESS_MESSAGE);
        this.data = data;
    }

    // 성공 응답을 쉽게 생성할 수 있는 정적 메서드
    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<>(data);
    }
}
