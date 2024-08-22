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
public class ApiErrorResponse extends CommonResponse {

    private final String errorCode;
    private final Map<String, Object> errorDetails;

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

    public static ApiErrorResponse of(String errorCode, Map<String, Object> errorDetails) {
        return new ApiErrorResponse("error", "Operation failed", errorCode, errorDetails);
    }

    public static ApiErrorResponse from(HttpStatus httpStatus, Map<String, Object> errorDetails) {
        return new ApiErrorResponse(httpStatus, errorDetails);
    }

    public static ApiErrorResponse fromException(HttpStatusCodeException e) {
        log.error("HttpStatusCodeException occurred : {} ", e.getMessage(), e);
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
            if (StringUtils.isEmpty(e.getResponseBodyAsString())) {
                throw e;
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
