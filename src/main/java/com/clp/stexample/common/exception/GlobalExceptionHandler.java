package com.clp.stexample.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 해당 클래스가 전역적인 예외처리를 담당함
@ControllerAdvice
public class GlobalExceptionHandler {

    // 특정 타입의 예외가 발생했을 떄 호출되는 메서드를 정의
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleApiException(ApiException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getStatusCode());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    // 기타 예외 처리기 추가 가능
}
