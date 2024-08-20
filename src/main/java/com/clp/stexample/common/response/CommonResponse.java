package com.clp.stexample.common.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class CommonResponse {
    // getter와 setter
    private String status;
    private String message;

    // 생성자
    public CommonResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

}