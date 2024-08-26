package com.clp.service.location.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Item(
        @JsonProperty("locationId") String locationId,
        @JsonProperty("name") String name,
        @JsonProperty("allowed") String allowed,  // Null 값이 가능한 경우, String 또는 Object로 처리
        @JsonProperty("parent") Parent parent
) {}