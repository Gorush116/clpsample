package com.clp.stexample.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.HashMap;
import java.util.Map;

@Getter
@Slf4j
public class ApiFailResponse<T> extends ApiResponse {

    private final T inputData;
    private final String errorCode;
    private final Map<String, Object> errorDetails;

    public ApiFailResponse(String status, String message, String errorCode, Map<String, Object> errorDetails, T inputData) {
        super(status, message);
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.inputData = inputData;
    }

    public ApiFailResponse(HttpStatus httpStatus, Map<String, Object> errorDetails, T inputData) {
        super("error", httpStatus.getReasonPhrase());
        this.errorCode = String.valueOf(httpStatus);
        this.errorDetails = errorDetails;
        this.inputData = inputData;
    }

    public ApiFailResponse(Exception e, T inputData) {
        super("Exception", e.getMessage());
        this.errorCode = null;
        this.errorDetails = Map.of("Exception", e.toString());
        this.inputData = inputData;
    }

    public static <T> ApiFailResponse<T> of(String errorCode, Map<String, Object> errorDetails) {
        return new ApiFailResponse<>("error", "Operation failed", errorCode, errorDetails, null);
    }

    public static <T> ApiFailResponse<T> fromException(Exception e, T inputData) {
        return new ApiFailResponse<>(e, inputData);
    }

    public static <T> ApiFailResponse<T> fromHttpException(HttpStatusCodeException e, T inputData) {
        return new ApiFailResponse<>(resolveHttpStatus(e), parseErrorDetails(e), inputData);
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

            // 응답 본문 가져오기
            if (StringUtils.isEmpty(e.getResponseBodyAsString())) {
                // 응답 본문이 없을 경우 기본 메시지 설정
                errorDetails.put("message", "No error details provided in the response.");
            } else {
                // 예외의 응답 본문을 JSON으로 변환
                ObjectMapper mapper = new ObjectMapper();
                errorDetails = mapper.readValue(e.getResponseBodyAsString(), new TypeReference<>() {});
            }
        } catch (JsonProcessingException ex) {
            log.error("Failed to parse error details from response: {}", ex.getMessage(), ex);
            errorDetails.put("message", "Failed to parse error details from response.");
        }
        return errorDetails;
    }
}
