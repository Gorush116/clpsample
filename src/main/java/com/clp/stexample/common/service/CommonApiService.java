package com.clp.stexample.common.service;

import com.clp.stexample.common.enums.ApiEndpoint;
import com.clp.stexample.common.error.ErrorResponse;
import com.clp.stexample.common.error.HTTPErrorCode;
import com.clp.stexample.common.model.ApiResponse;
import com.clp.stexample.common.response.ApiErrorResponse;
import com.clp.stexample.common.response.CommonResponse;
import com.clp.stexample.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommonApiService {

    private final RestClient restClient;

    @Value("${api.base.url}")
    private String apiBaseUrl;

    @Value("${smart-things.token}")
    private String token;

    // 공통모듈 시작
    public CommonResponse call(ApiEndpoint endpoint, Object... uriVariables) {
        return callWithRequest(endpoint, null, uriVariables);
    }

    public CommonResponse callWithRequest(ApiEndpoint endpoint, Object request, Object... uriVariables) {
        String url = buildUrl(endpoint, uriVariables);
        HttpEntity<?> entity = new HttpEntity<>(request, createHeaders());
        return exchangeFor(url, endpoint.getMethod(), entity, uriVariables);
    }

    public CommonResponse exchangeFor(
            String url,
            HttpMethod method,
            HttpEntity<?> entity,
            Object... uriVariables
    )  {
        try {
            // HTTP METHOD & URI SET
            var request = restClient.method(method)
                    .uri(url, uriVariables)
                    .headers(headersSpec -> headersSpec.putAll(entity.getHeaders()));

            // Optional을 사용하여 body가 있을 때만 SET
            Optional.ofNullable(entity.getBody()).ifPresent(request::body);

            // SEND REQUEST & GET RESPONSE
            Map<String, Object> responseBody = request.retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);

//            return SuccessResponse.of(responseBody);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            return ApiErrorResponse.fromException(ex);
        }
    }
    // 공통모듈 끝

    // Header 생성
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");
        return headers;
    }

    // Enum과 Request의 pathParameter 매핑
    public String buildUrl(ApiEndpoint endpoint, Object... uriVariables) {
        String url = apiBaseUrl + endpoint.getUrl();

        for (Object uriVariable : uriVariables) {
            url = url.replaceFirst("\\{[^/]+\\}", uriVariable.toString());
        }

        return url;
    }
}
