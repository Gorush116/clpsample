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

    /**
     * 에러 코드와 에러 메시지를 통한 응답 실패를 반환합니다.
     * @param errorCode 에러코드
     * @param errorDetails 에러내용
     * @return API 통신 실패 응답값
     * @param <T> 응답 타입
     */
    public static <T> ApiFailResponse<T> of(String errorCode, Map<String, Object> errorDetails) {
        return new ApiFailResponse<>("error", "Operation failed", errorCode, errorDetails, null);
    }

    /**
     * 예외와 입력데이터를 통한 응답 실패를 반환합니다.
     * @param e 예외
     * @param inputData 입력 데이터
     * @return API 통신 실패 응답값
     * @param <T> 응답 타입
     */
    public static <T> ApiFailResponse<T> fromException(Exception e, T inputData) {
        return new ApiFailResponse<>(e, inputData);
    }

    /**
     * HttpStatusCodeException 와 입력데이터를 통한 응답 실패를 반환합니다.
     * @param e 예외
     * @param inputData 입력 데이터
     * @return API 통신 실패 응답값
     * @param <T> 응답 타입
     */
    public static <T> ApiFailResponse<T> fromHttpException(HttpStatusCodeException e, T inputData) {
        return new ApiFailResponse<>(resolveHttpStatus(e), parseErrorDetails(e), inputData);
    }

    /**
     * HttpStatusCodeException 를 통해 HTTP 상태코드를 반환합니다.
     * - 일치하는 상태코드가 없을 때 INTERNAL_SERVER_ERROR 를 반환합니다.
     * @param e 예외
     * @return HTTP 상태 코드
     */
    private static HttpStatus resolveHttpStatus(HttpStatusCodeException e) {
        HttpStatus status = HttpStatus.resolve(e.getStatusCode().value());
        if (status == null) {
            log.warn("Unknown HTTP status code: {}", e.getStatusCode().value());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return status;
    }

    /**
     * 에러 상세내용을 변환하여 반환합니다.
     * - 응답 본문이 존재하지 않을 경우 기본 메시지를 반환합니다.
     * - 응답 본문 JSON 변환 실패시 에러 로그와 동시에 메시지를 반환합니다.
     * @param e 예외
     * @return Map
     */
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
