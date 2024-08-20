package com.clp.stexample.common.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SuccessResponse<T> extends CommonResponse {

    private T data;

    // 생성자
    public SuccessResponse(String status, String message, T data) {
        super(status, message);
        this.data = data;
    }

    // 성공 응답을 쉽게 생성할 수 있는 정적 메서드
    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<>("success", "Operation successful", data);
    }
}
