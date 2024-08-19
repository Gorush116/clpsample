package com.clp.clpservice.sit.controller;

import com.clp.clpservice.sit.response.SiteRes;
import com.clp.clpservice.spa.response.SpaceRes;
import com.clp.stexample.common.model.ApiResponse;
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

    LocalDateTime ldt = LocalDateTime.of(2030, 1, 1, 13, 30, 50);
    ZonedDateTime zdt1 = ZonedDateTime.of(ldt, ZoneId.of("Asia/Seoul"));

    @Operation(
            summary="API_SIT_00010(사이트 목록 조회)",
            description=
                    " 1. 운영자는 Tenant 하위 등록된 사이트 목록을 확인한다.\n" +
                    " 2. 사이트 목록 및 사이트 상세정보를 확인한다."
    )
    @GetMapping("/sit/{clcpId}")
    public ApiResponse<List<SiteRes>> getSites(@PathVariable String clcpId) {

        return ApiResponse.success(
                List.of(SiteRes.builder()
                                .siteNo(1234)
                                .siteNm("Test Site")
                                .natnCd("ko-kr")
                                .siteAddr("Site")
                                .siteAr(BigDecimal.TEN)
                                .siteSpacCnt(10)
                                .siteHcnt(5)
                                .siteCpltDt(zdt1.toLocalDate())
                                .siteStsCd("1")
                                .regDtm(zdt1)
                                .updDtm(zdt1)
                                .regPgmId("regPgm")
                                .updUserId("updUser")
                                .build()
                ),
                HttpStatus.OK);
    }

    @Operation(
            summary="API_SIT_00060(사이트 공간 목록 조회)",
            description=
                    "1. 운영자는 사이트에 등록된 공간목록을 확인한다. " +
                    "2. 사이트에 등록된 공간 목록 및 공간 상세정보를 확인한다."
    )
    @GetMapping("/sit/spac/{siteNo}")
    public ApiResponse<List<SpaceRes>> getSiteSpaces(@PathVariable String siteNo) {

        return ApiResponse.success(List.of(getSpaceRes()), HttpStatus.OK);
    }

    @Operation(
            summary="API_SIT_00100(사이트 공간 상세 정보 확인)",
            description=
                    "1. 운영자는 사이트 하위 공간 상세 정보를 확인한다. " +
                    "2. 공간 상세정보를 확인한다."
    )
    @GetMapping("/sit/spac/detail/{siteNo}")
    public ApiResponse<SpaceRes> getspaceDetail(@PathVariable String siteNo) {

        return ApiResponse.success(getSpaceRes(), HttpStatus.OK);
    }

    private static SpaceRes getSpaceRes() {
        return SpaceRes.builder()
                .siteNo(1001L)                        // 사이트ID
                .siteNm("Test Site")                  // 사이트명
                .spacId("SP001")                      // 공간ID
                .spacNm("Main Hall")                  // 공간명
                .spacClsCd("CLS001")                  // 공간구분코드
                .spacTypeNo(10)                       // 공간유형번호
                .cntrStDt(ZonedDateTime.now().toLocalDate())        // 계약일
                .mvnDt(ZonedDateTime.now().toLocalDate())  // 입주일
                .mbrId("MEM001")                      // 입주자ID(회원ID)
                .spacStsCd("ACTIVE")                  // 공간상태
                .locationGroup("Group A")             // Location Group
                .location("Seoul")                    // Location
                .room("Room 101")                     // Room
                .userId("USER001")                    // 운영자ID
                .regDtm(ZonedDateTime.now())          // 등록일
                .updDtm(ZonedDateTime.now())          // 수정일
                .regPgmId("Admin")                    // 등록자
                .updUserId("Admin")                   // 수정자
                .build();
    }
}
