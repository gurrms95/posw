package com.omid.osw.web.alarm.dto;

import com.omid.osw.web.taking.dto.TakingDetailDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDTO {

    @Schema(description = "사용자ID", example = "")
    private String userId;
    @Schema(description = "구분", example = "")
    private int gubun;
    @Schema(description = "등록일자", example = "")
    private String inpDt;
    @Schema(description = "알람 여부", example = "")
    private boolean isAlarm;
    @Schema(description = "복용명", example = "")
    private String takingNm;
    @Schema(description = "금일", example = "")
    private String today;
    @Schema(description = "일일 체크", example = "")
    private String checkDt;
    @Schema(description = "투약 시작일", example = "")
    private String takingStartDt;
    @Schema(description = "복용상태(0:복용중 / 1:복용완료)", example = "")
    private int takingState;
    @Schema(description = "알람 상세", example = "")
    private List<AlarmDetailDTO> alarmDetailDTOList;

}
