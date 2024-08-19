package com.clp.clpservice.sit.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
public class SiteRes {
    private long siteNo;                // 사이트ID
    private String siteNm;              // 사이트명
    private String natnCd;              // 국가코드
    private String siteAddr;            // 사이트주소
    private BigDecimal siteAr;          // 사이트면적 (NUMERIC 20,6)
    private long siteSpacCnt;           // 공간갯수
    private int siteHcnt;               // 세대수
    private LocalDate siteCpltDt;   // 준공일
    private String siteStsCd;           // 사이트상태
    private ZonedDateTime regDtm;       // 등록일
    private ZonedDateTime updDtm;       // 수정일
    private String regPgmId;            // 등록자
    private String updUserId;           // 수정자

    public SiteRes() {
    }

    @Builder
    public SiteRes(long siteNo,
                   String siteNm,
                   String natnCd,
                   String siteAddr,
                   BigDecimal siteAr,
                   long siteSpacCnt,
                   int siteHcnt,
                   LocalDate siteCpltDt,
                   String siteStsCd,
                   ZonedDateTime regDtm,
                   ZonedDateTime updDtm,
                   String regPgmId,
                   String updUserId) {
        this.siteNo = siteNo;
        this.siteNm = siteNm;
        this.natnCd = natnCd;
        this.siteAddr = siteAddr;
        this.siteAr = siteAr;
        this.siteSpacCnt = siteSpacCnt;
        this.siteHcnt = siteHcnt;
        this.siteCpltDt = siteCpltDt;
        this.siteStsCd = siteStsCd;
        this.regDtm = regDtm;
        this.updDtm = updDtm;
        this.regPgmId = regPgmId;
        this.updUserId = updUserId;
    }
}
