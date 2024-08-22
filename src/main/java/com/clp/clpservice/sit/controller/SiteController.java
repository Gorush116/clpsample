package com.clp.clpservice.sit.controller;

import com.clp.clpservice.sit.response.SiteRes;
import com.clp.clpservice.spa.response.SpaceRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Site", description = "CLP 기능 중 사이트 관리 API 목록입니다.")
public class SiteController {

}
