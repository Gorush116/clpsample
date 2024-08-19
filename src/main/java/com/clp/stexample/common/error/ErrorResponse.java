package com.clp.stexample.common.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String requestId;
    private ErrorDetail error;
}
