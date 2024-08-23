package com.clp.stexample.common.service;

import com.clp.stexample.common.enums.ApiEndpoint;
import com.clp.stexample.common.exception.ApiException;
import com.clp.stexample.common.response.ApiFailResponse;
import com.clp.stexample.common.response.ApiResponse;
import com.clp.stexample.common.response.ApiSuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class ApiService {

    private final RestClient restClient;

    // Base Url 정보(자원정보 참조)
    @Value("${api.base.url}")
    private String apiBaseUrl;

    // 토큰값
    @Value("${smart-things.token}")
    private String token;

    /**
     * request param이 없는 API 요청을 호출합니다.
     * - path parameter 를 제외한 parameter를 포함하여 호출시 callWithRequest를 통해 호출하여야 합니다.
     * - 메서드 호출시 path parameter 가 2개 이상일 때, parameter 순서를 맞추어 인자를 넘겨주어야 합니다.
     * @param endpoint ApiEndpoint를 구현한 Enum
     * @param uriVariables @PathVariable로 지정한 1개 이상의 parameter
     * @return API 요청의 응답
     */
    public ApiResponse call(ApiEndpoint endpoint, Object... uriVariables) {
        return callWithRequest(endpoint, null, uriVariables);
    }

    /**
     * request param이 포함된 API 요청을 호출합니다.
     * - 메서드 호출시 path parameter 가 2개 이상일 때, parameter 순서를 맞추어 인자를 넘겨주어야 합니다.
     * 1. uriVariables 존재시 Enum의 pathVariable의 내용을 replace
     * 2. header 생성
     * 3. API 요청
     *
     * @param endpoint ApiEndpoint를 구현한 Enum
     * @param request 요청 본문
     * @param uriVariables @PathVariable로 지정한 1개 이상의 parameter
     * @return API 요청의 응답
     */
    public ApiResponse callWithRequest(ApiEndpoint endpoint, Object request, Object... uriVariables) {
        String uri = buildUri(endpoint, uriVariables);
        HttpEntity<?> entity = new HttpEntity<>(request, createHeaders());
        return exchange(uri, endpoint.getMethod(), entity);
    }

    /**
     * restClient를 통하여 API 요청을 호출합니다.
     * 1. HTTP Method/uri/header SET
     * 2. (body 내용 존재시) body SET
     * 3. SEND REQUEST & GET RESPONSE
     * 4. 응답값에 따른 처리
     *      - 성공시 ApiSuccessResponse return 
     *      - 예외 발생시 ApiFailResponse return 또는 throw CustomException
     * @param uri API 요청 uri
     * @param method HTTP Method
     * @param entity Header 및 request body 정보 포함
     * @return API 요청의 응답
     */
    public ApiResponse exchange(
            String uri,
            HttpMethod method,
            HttpEntity<?> entity
    )  {
        try {
            // HTTP METHOD & URI SET
            var request = restClient.method(method)
                    .uri(uri)
                    .headers(headersSpec -> headersSpec.putAll(entity.getHeaders()));

            // Optional을 사용하여 body가 있을 때만 SET
            Optional.ofNullable(entity.getBody()).ifPresent(request::body);

            // SEND REQUEST & GET RESPONSE
            Map<String, Object> responseBody = request.retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            return ApiSuccessResponse.of(responseBody);

        } catch (HttpStatusCodeException ex) {
            log.error("HttpStatusCodeException occurred when calling : {} ", uri, ex);
            return ApiFailResponse.fromHttpException(ex, entity.getBody());
        } catch (ApiException ex) {
            log.error("ApiException occurred when calling : {} ", uri, ex);
            throw new ApiException(ex.getMessage(), 40001);
        } catch (Exception ex) {
            log.error("Exception occurred when calling : {}", uri, ex);
            return ApiFailResponse.fromException(ex, entity.getBody());
        }
    }

    /**
     * API 호출을 위한 HTTP 헤더를 반환합니다.
     *
     * @return HTTP 헤더
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");
        return headers;
    }

    /**
     * API 호출을 위한 uri를 세팅합니다.
     * - Enum path parameter 를 request pathParameter 에 매핑합니다.
     * - 이 때, Enum 명시된 uri path parameter 순서와 uriVariables에 포함된 path parameter의 순서가 일치해야 합니다.
     *   (동작방식 : for문을 통해 replaceFirst 메서드로 최초 문자열 대체)
     * @param endpoint
     * @param uriVariables
     * @return uri
     */
    public String buildUri(ApiEndpoint endpoint, Object... uriVariables) {
        String uri = apiBaseUrl + endpoint.getUrl();

        for (Object uriVariable : uriVariables) {
            uri = uri.replaceFirst("\\{[^/]+\\}", uriVariable.toString());
        }

        return uri;
    }
}
