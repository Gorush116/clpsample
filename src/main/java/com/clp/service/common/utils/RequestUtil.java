package com.clp.service.common.utils;

import com.clp.service.common.enums.ApiEndpoint;
import org.springframework.http.HttpHeaders;

public class RequestUtil {

    /**
     * API 호출을 위한 HTTP 헤더를 반환합니다.
     *
     * @return HTTP 헤더
     */
    public static HttpHeaders createHeaders(String token) {
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
     * @param endpoint url
     * @param uriVariables path parameter
     * @return uri
     */
    public static String buildUri(String apiBaseUrl, ApiEndpoint endpoint, Object... uriVariables) {
        String uri = apiBaseUrl + endpoint.getUrl();

        for (Object uriVariable : uriVariables) {
            uri = uri.replaceFirst("\\{[^/]+\\}", uriVariable.toString());
        }

        return uri;
    }
}
