package com.omid.osw.web.alarm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDetailDTO {

    @Schema(description = "복용 ID", example = "")
    private int tkId;
    @Schema(description = "약품명", example = "")
    private String mediNm;
    @Schema(description = "메모", example = "")
    private String memo;
    @Schema(description = "총 복용량", example = "")
    private String totalDoseCnt;
    @Schema(description = "복용량", example = "")
    private String eatenDoseCnt;
    @Schema(description = "1일 투약 횟수", example = "")
    private int dailyDoseCnt;
    @Schema(description = "1일 복용 횟수", example = "")
    private int todayEatenDoseCnt;
}
