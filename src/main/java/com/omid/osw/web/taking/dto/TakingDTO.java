package com.omid.osw.web.taking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "복용")
public class TakingDTO {
    @Schema(description = "복용 ID", example = "")
    private int takingId;
    @Schema(description = "약품명", example = "")
    private String mediNm;
    @Schema(description = "투약 시작일", example = "")
    private String takingStartDt;
    @Schema(description = "총 투약 일 수", example = "")
    private int allTakingDt;
    @Schema(description = "함량", example = "")
    private int drugContent;
    @Schema(description = "1회 투약량", example = "")
    private int dailyDose;
    @Schema(description = "1일 투약 횟수", example = "")
    private int dailyDoseCnt;
    @Schema(description = "메모", example = "")
    private String memo;
    @Schema(description = "등록일자", example = "")
    private String inpDt;
    @Schema(description = "구분", example = "")
    private int gubun;
    @Schema(description = "사용자ID", example = "")
    private String userId;
    @Schema(description = "복용상태(0:복용중 / 1:복용완료)", example = "")
    private int takingState;
    @Schema(description = "복용명", example = "")
    private String takingNm;
    @Schema(description = "총 복용량", example = "")
    private String totalDoseCnt;
    @Schema(description = "복용량", example = "")
    private String eatenDoseCnt;
    @Schema(description = "알람 여부", example = "")
    private boolean isAlarm;
    @Schema(description = "복용상세", example = "")
    private List<TakingDetailDTO> takingDetailDTOList;
}
