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
public class CommonApiService {

    private final RestClient restClient;

    // Base Url 정보(자원정보 참조)
    @Value("${api.base.url}")
    private String apiBaseUrl;

    // 토큰값
    @Value("${smart-things.token}")
    private String token;

    /**
     * 주어진 API 요청을 호출합니다.
     * - path parameter 를 제외한 parameter를 포함하여 호출시 callWithRequest를 통해 호출하여야 합니다.
     * - 메서드 호출시 path parameter 가 2개 이상일 때, parameter 순서를 맞추어 인자를 넘겨주어야 합니다.
     * @param endpoint
     * @param uriVariables
     * @return
     */
    public ApiResponse call(ApiEndpoint endpoint, Object... uriVariables) {
        return callWithRequest(endpoint, null, uriVariables);
    }

    public ApiResponse callWithRequest(ApiEndpoint endpoint, Object request, Object... uriVariables) {
        String uri = buildUri(endpoint, uriVariables);
        HttpEntity<?> entity = new HttpEntity<>(request, createHeaders());
        return exchange(uri, endpoint.getMethod(), entity);
    }

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

    // Header 생성
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");
        return headers;
    }

    // Enum과 Request의 pathParameter 매핑
    public String buildUri(ApiEndpoint endpoint, Object... uriVariables) {
        String uri = apiBaseUrl + endpoint.getUrl();

        for (Object uriVariable : uriVariables) {
            uri = uri.replaceFirst("\\{[^/]+\\}", uriVariable.toString());
        }

        return uri;
    }
}
