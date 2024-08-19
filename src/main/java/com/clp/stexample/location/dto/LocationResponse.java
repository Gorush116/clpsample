package com.clp.stexample.location.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record LocationResponse(
        @JsonProperty("items") List<Item> items,
        @JsonProperty("_links") Object links  // Null 값을 수용할 수 있는 타입으로 정의
) {}