# 외부 API 통신 공통 인터페이스

---

## 목차

1. [소개](#인터페이스 소개)
2. [설치 및 설정](#설정)
   1. [의존성](#2-1-의존성)
   2. [설정](#2-2-설정) 
3. [사용 방법](#3-사용-방법)
   1. [API 호출 방법](#3-1-api-호출-모듈)
   2. [HTTP 메소드 사용법](#3-2-http-메소드-사용법)
   3. [예외 처리](#3-3-예외-처리)
4. [확장 방법](#4-확장-방법)
    1. [확장 포인트](#4-1-확장-포인트)
    2. [커스터마이징 예제](#4-2-커스터마이징-예제-) 
5. [참고 자료](#5-참고-자료)
    1. [주석 및 코드 예제](#5-1-주석-및-코드-예제)

---

## 1. 소개

CLP Service 외부 REST API 통신을 위한 공통 인터페이스의 목적과 주요 기능에 대한 설명입니다.

이 문서는 SmartThings API 를 기준으로 작성으며, SmartThings 개인 권한 토큰이 필요합니다.

- SmartThings API Docs : <https://developer.smartthings.com/docs/api/public>

- 토큰 발급 주소(삼성 계정 필요) : <https://account.smartthings.com/tokens>

---

## 2. 설치 및 설정

### 버전 정보
```
[자바] Java : 22
[스프링 부트] org.springframework.boot : 3.3.2
```


### 디렉토리 구조
```text
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─clp
│  │  │          └─stexample
│  │  │              ├─common
│  │  │              │  ├─config                    // 설정정보 
│  │  │              │  ├─enums                     // API endpoint Enum 정보
│  │  │              │  │  ├─automations
│  │  │              │  │  ├─devices
│  │  │              │  │  └─locations
│  │  │              │  ├─error                     // error에 대한 Enum 정보
│  │  │              │  ├─exception                 // 예외 처리에 대한 클래스
│  │  │              │  ├─model
│  │  │              │  ├─request                  // 공통 요청에 대한 클래스
│  │  │              │  ├─response                  // 공통 응답에 대한 클래스
│  │  │              │  └─service                   // 공통 API 호출에 대한 클래스
│  │  │              ├─device
│  │  │              │  └─controller
│  │  │              ├─location
│  │  │              │  ├─controller                // Sample Controller
│  │  │              │  ├─dto                       // Sample Location Dto
│  │  │              │  ├─request                   // Sample Location Request
│  │  │              │  └─service                   // Sample Service
│  │  │              └─room
│  │  │                  └─controller
│  │  └─resources
```


### 2-1. 의존성
`[pom.xml]`

```angular2html
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!--validation dependency-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <!--springdoc-openapi dependency-->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.1.0</version>
        </dependency>
    </dependencies>
```


### 2-2. 설정
`[기본 url 및 토큰값 설정(application.yml)]`

```yaml
# SmartThings token
smart-things:
  token: 발급받은 토큰값 입력

# API 기본 주소
api:
  base:
    url: https://api.smartthings.com/v1
```



`[RestClient 설정]`

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
```
---

## 3. 사용 방법

### 3-1. API 호출 모듈

`com.clp.stexample.common.service`

####  1. Enum을 통한 API 호출
```java
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
```


#### 2. API 호출시 직접 입력(추가 예정)

```java

```


### 3-2. HTTP 메소드 사용법

#### 1. Enum에 저장된 HTTP 메소드 사용
`com.clp.stexample.common.enums.automations`

>  * Enum 사용시 ApiEndpoint 인터페이스를 구현해야 합니다. 

`ApiEndPoint 인터페이스`
```java
public interface ApiEndpoint {
    String getTitle();
    String getUrl();
    HttpMethod getMethod();
}
```

`Sample Enum(Locations)`

```java
public enum Locations implements ApiEndpoint {

    LIST_LOCATIONS("List Locations", "/locations", GET),
    CREATE_A_LOCATION("Create a Location", "/locations", POST),
    GET_A_LOCATION("Get a Location", "/locations/{locationId}", GET),
    UPDATE_A_LOCATION("Update a Location", "/locations/{locationId}", PUT),
    PATCH_A_LOCATION("Patch a Location", "/locations/{locationId}", PATCH),
    DELETE_A_LOCATION("Delete a Loacation", "/locations/{locationId}", DELETE);

    private final String title;
    private final String url;
    private final HttpMethod method;

    Locations(String title, String url, HttpMethod method) {
        this.title = title;
        this.url = url;
        this.method = method;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public HttpMethod getMethod() {
        return method;
    }
}
```

### 3-3. 예외 처리

`com.clp.stexample.common.exception`

> * GlobalExceptionHandler 클래스에서 특정 예외나 전역 예외 핸들링 정의
>   * 커스텀 예외의 경우 클래스 생성 후 @ExceptionHandler 어노테이션에 매핑하여 핸들링 가능

`Sample ExceptionHandler(GlobalExceptionHandler)`

```java
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
                null
        );

        return new ResponseEntity<>(response, status);
    }
}
```

> 특정 예외 클래스 정의

`Sample Custom Exception(ApiException)`

```java
@Getter
public class ApiException extends RuntimeException {
    private final int statusCode;

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
```

---

## 4. 확장 방법

### 4-1. 확장 포인트

> 1. [Enum 을 통한 외부 API 정보 정의(Sample Enum 참조)](#3-2-http-메소드-사용법)
> 2. [사용자 정의 예외 처리(Sample Custom Exception 참조)](#3-3-예외-처리)
> 3. [요청 및 응답 타입 지정(4-2. 커스터마이징 예제 참조)](#4-2-커스터마이징-예제-)


### 4-2. 커스터마이징 예제 

#### 사용자 정의 요청 본문 정의


`Sample RequestBody(LocationReq)`

```java
@Data
public class LocationReq {

    @NotBlank
    @Size(min = 1, max = 100)
    @Schema(description = "The name of the location.", example = "Central Park")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$")
    @Schema(description = "The country code in ISO 3166-1 alpha-3 format.", example = "USA")
    private String countryCode;

    @DecimalMin(value = "-90.0", inclusive = true)
    @DecimalMax(value = "90.0", inclusive = true)
    @Schema(description = "The latitude of the location, ranging from -90.0 to 90.0.", example = "40.7128")
    private Double latitude;

    @DecimalMin(value = "-180.0", inclusive = true)
    @DecimalMax(value = "180.0", inclusive = true)
    @Schema(description = "The longitude of the location, ranging from -180.0 to 180.0.", example = "-74.0060")
    private Double longitude;

    @Min(20)
    @Max(500000)
    @Schema(description = "The radius of the region in meters.", example = "1000")
    private Integer regionRadius;

    @Pattern(regexp = "^(F|C)$")
    @Schema(description = "The temperature scale, either 'F' for Fahrenheit or 'C' for Celsius.", example = "C")
    private String temperatureScale;

    @Schema(description = "The locale for the location, e.g., en-US.")
    private String locale;

    @Schema(description = "Additional properties related to the location.")
    private Map<String, Object> additionalProperties;

    @Schema(description = "The parent location object.")
    private LocationParent parent;

    @Getter
    @Setter
    @Schema(description = "Parent location information.")
    public static class LocationParent {

        @NotBlank
        @Size(min = 7, max = 14)
        @Schema(description = "The type of the parent location.", example = "region")
        private String type;

        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
        @Schema(description = "The unique identifier of the parent location in UUID format.", example = "123e4567-e89b-12d3-a456-426614174000")
        private String id;
    }
}
```

---

## 5. 참고 자료

### 5-1. 주석 및 코드 예제

---