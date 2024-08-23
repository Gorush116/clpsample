package com.clp.stexample.common.exception;

import com.clp.stexample.common.response.ApiFailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    // 특정 예외 처리
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleApiException(ApiException ex) {
        HttpStatus status = Optional.ofNullable(HttpStatus.resolve(ex.getStatusCode()))
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiFailResponse<?>> handleGlobalException(Exception ex) {
        log.error("Unhandled exception occurred : {} ", ex.getMessage(), ex);

        // 기본 상태 코드 설정
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof HttpStatusCodeException) {
            // 예외가 HttpStatusCodeException일 경우, 상태 코드를 추출
            status = HttpStatus.valueOf(((HttpStatusCodeException) ex).getStatusCode().value());
        }

        ApiFailResponse<?> response = new ApiFailResponse<>(
                status,
                Map.of("message", "An unexpected error occurred " + ex.getMessage()),
                Map.of()
        );

        return new ResponseEntity<>(response, status);
    }
}
