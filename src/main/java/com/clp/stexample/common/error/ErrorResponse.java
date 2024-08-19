package com.clp.stexample.common.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ErrorResponse {
    private String requestId;
    private ErrorDetail error;
}
