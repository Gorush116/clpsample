package com.clp.stexample.location.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Parent(
        @JsonProperty("type") String type,
        @JsonProperty("id") String id
) {}