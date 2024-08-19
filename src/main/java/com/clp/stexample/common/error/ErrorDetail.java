package com.clp.stexample.common.error;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorDetail {
    private String code;
    private String message;
    private String target;
    private List<ErrorDetail> details;

}
