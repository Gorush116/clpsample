package com.clp.clpservice.das;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "CLP 기능 중 Dashboard API 목록입니다.")
public class DasController {


    @Operation(
            summary="API_DAS_00050(이상상태 알림)",
            description=
                    " HRM에서 기기가 고장/비정상/교체 등 이상상태를 탐지 시, API로 CLP에 이상발생한 기기정보 및 이상상태를 전송하고, CLP에서는 API로 운영자에게 알림을 전달한다.\n" +
                            " 1) HRM에서 시리얼넘버 수신 \n" +
                            " 2) 시리얼넘버로 ST 에서 디바이스 정보 조회 \n" +
                            " 3) 디바이스 정보 조회후 CS ticket 등록 \n" +
                            " 4) 알람 정보 리턴"
    )
    @Parameter(name="DVC_MFT_NO", description="디바이스제조번호(시리얼넘버)")
    @Parameter(name="DVC_ID", description="디바이스ID")
    @Parameter(name="DVC_CLS_CD", description="디바이스구분코드")
    @Parameter(name="DVC_NM", description="디바이스명")
    @Parameter(name="DVC_STS_VAL", description="디바이스상태값")
    @Parameter(name="DVC_INFM_SND_DTM", description="디바이스알림발송일시")
    @Parameter(name="DVC_MSG_CN", description="디바이스메시지내용")
    @GetMapping("/send")
    public Object send() {
        return null;
    }

}
