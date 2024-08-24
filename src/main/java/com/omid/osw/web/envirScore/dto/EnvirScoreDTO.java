package com.omid.osw.web.envirScore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "환경지킴이")
public class EnvirScoreDTO {

    @Schema(description = "사용자ID", example = "id1234")
    private String userId;

    @Schema(description = "연도", example = "2024")
    private int year;

    @Schema(description = "복용등록점수 합계", example = "10")
    private int takingAddScore;

    @Schema(description = "복용완료점수 합계", example = "20")
    private int takingEndScore;

    @Schema(description = "배출점수 합계", example = "30")
    private int disposalScore;

    @Schema(description = "기타점수 합계", example = "40")
    private int etcScore;

    @Schema(description = "출석점수 합계", example = "50")
    private int attendScore;

    @Schema(description = "총합계", example = "150")
    private int totalScore;

    @Schema(description = "전체 순위", example = "1")
    private int totalScoreRank;

    @Schema(description = "복용점수(등록+완료) 순위", example = "2")
    private int takingScoreRank;

    @Schema(description = "복용등록점수 순위", example = "2")
    private int takingAddScoreRank;

    @Schema(description = "복용완료점수 순위", example = "3")
    private int takingEndScoreRank;

    @Schema(description = "배출점수 순위", example = "4")
    private int disposalScoreRank;

    @Schema(description = "기타점수 순위", example = "5")
    private int etcScoreRank;

    @Schema(description = "출석점수 순위", example = "6")
    private int attendScoreRank;

    @Schema(description = "전체 백분율", example = "90")
    private double totalScorePercent;

    @Schema(description = "최초등록일자", example = "20240101")
    private String inpDt;

    @Schema(description = "수정일자", example = "20240101")
    private String udtDt;

    @Schema(description = "사용여부", example = "Y")
    private String useYn;

    @Schema(description = "전체사용자 평균복용등록점수", example = "Y")
    private int avgTakingAddScore;

    @Schema(description = "전체사용자 평균복용완료점수", example = "Y")
    private int avgTakingEndScore;

    @Schema(description = "전체사용자 평균배출점수", example = "Y")
    private int avgDisposalScore;

    @Schema(description = "전체사용자 평균기타점수", example = "Y")
    private int avgEtcScore;

    @Schema(description = "전체사용자 평균출석점수", example = "Y")
    private int avgAttendScore;

}
