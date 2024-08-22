# 외부 API 통신 공통 인터페이스

---

## 목차

1. [소개](#인터페이스 소개)
2. [설치 및 설정](#설정)
   1. [의존성](#2-1-의존성)
   2. [설정](#2-2-설정) 
3. [사용 방법](#3-사용-방법)
   1. [API 호출 방법](#3-1-api-호출-방법)
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

### 3-1. API 호출 방법

`[공통 인터페이스 API 서비스 클래스]`


### 3-2. HTTP 메소드 사용법



### 3-3. 예외 처리

---

## 4. 확장 방법

### 4-1. 확장 포인트



### 4-2. 커스터마이징 예제 

---

## 5. 참고 자료

### 5-1. 주석 및 코드 예제

---