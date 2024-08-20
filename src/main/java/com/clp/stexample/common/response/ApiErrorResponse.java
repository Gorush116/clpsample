package com.clp.stexample.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class ApiErrorResponse extends CommonResponse {

    private String errorCode;
    private Map<String, Object> errorDetails;

    public ApiErrorResponse(String status, String message, String errorCode, Map<String, Object> errorDetails) {
        super(status, message);
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
    }

    public ApiErrorResponse(HttpStatus httpStatus, Map<String, Object> errorDetails) {
        super("error", httpStatus.getReasonPhrase());
        this.errorCode = String.valueOf(httpStatus.value());
        this.errorDetails = errorDetails;
    }

    // 에러 응답을 쉽게 생성할 수 있는 정적 메서드
    public static ApiErrorResponse of(String errorCode, Map<String, Object> errorDetails) {
        return new ApiErrorResponse("error", "Operation failed", errorCode, errorDetails);
    }

    // HttpStatus로 에러 응답 생성하는 정적 메서드
    public static ApiErrorResponse from(HttpStatus httpStatus, Map<String, Object> errorDetails) {
        return new ApiErrorResponse(httpStatus, errorDetails);
    }

    // 또 다른 정적 메서드: 상태 코드만으로 에러 응답 생성
    public static ApiErrorResponse fromException(HttpStatusCodeException e) {
        log.error("HttpStatusCodeException occurred", e);
        return new ApiErrorResponse(resolveHttpStatus(e), parseErrorDetails(e));
    }

    private static HttpStatus resolveHttpStatus(HttpStatusCodeException e) {
        HttpStatus status = HttpStatus.resolve(e.getStatusCode().value());
        if (status == null) {
            log.warn("Unknown HTTP status code: {}", e.getStatusCode().value());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return status;
    }

    private static Map<String, Object> parseErrorDetails(HttpStatusCodeException e) {
        Map<String, Object> errorDetails = new HashMap<>();
        try {
            // 예외의 응답 본문을 JSON으로 변환
            ObjectMapper mapper = new ObjectMapper();
            errorDetails = mapper.readValue(e.getResponseBodyAsString(), new TypeReference<>() {});
        } catch (JsonProcessingException ex) {
            log.error("Failed to parse error details from response: {}", ex.getMessage(), ex);
            errorDetails.put("message", "Failed to parse error details from response.");
        }
        return errorDetails;
    }
}
