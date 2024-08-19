package com.clp.clpservice.spa.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
public class SpaceRes {

    private long siteNo;            // 사이트ID
    private String siteNm;            // 사이트명
    private String spacId;          // 공간ID
    private String spacNm;          // 공간명
    private String spacClsCd;       // 공간구분코드
    private int spacTypeNo;         // 공간유형번호
    private LocalDate cntrStDt;     // 계약일
    private LocalDate mvnDt;        // 입주일
    private String mbrId;           // 입주자ID(회원ID)
    private String spacStsCd;       // 공간상태
    private String locationGroup;   // Location Group(ST Data)
    private String location;        // Location(ST Data)
    private String room;            // Room(ST Data)
    private String userId;          // 운영자ID
    private ZonedDateTime regDtm;   // 등록일
    private ZonedDateTime updDtm;   // 수정일
    private String regPgmId;        // 등록자
    private String updUserId;       // 수정자

    public SpaceRes() {
    }

    @Builder
    public SpaceRes(long siteNo,
                    String siteNm,
                    String spacId,
                    String spacNm,
                    String spacClsCd,
                    int spacTypeNo,
                    LocalDate cntrStDt,
                    LocalDate mvnDt,
                    String mbrId,
                    String spacStsCd,
                    String locationGroup,
                    String location,
                    String room,
                    String userId,
                    ZonedDateTime regDtm,
                    ZonedDateTime updDtm,
                    String regPgmId,
                    String updUserId) {
        this.siteNo = siteNo;
        this.siteNm = siteNm;
        this.spacId = spacId;
        this.spacNm = spacNm;
        this.spacClsCd = spacClsCd;
        this.spacTypeNo = spacTypeNo;
        this.cntrStDt = cntrStDt;
        this.mvnDt = mvnDt;
        this.mbrId = mbrId;
        this.spacStsCd = spacStsCd;
        this.locationGroup = locationGroup;
        this.location = location;
        this.room = room;
        this.userId = userId;
        this.regDtm = regDtm;
        this.updDtm = updDtm;
        this.regPgmId = regPgmId;
        this.updUserId = updUserId;
    }
}