package com.omid.osw.web.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "커뮤니티")
public class CommunityDto {

    @Schema(description = "번호", example = "")
    private String bno;

    @Schema(description = "제목", example = "")
    private String comTitle;

    @Schema(description = "종류", example = "")
    private int comKind;

    @Schema(description = "작성자", example = "")
    private String comWriter;

    @Schema(description = "내용", example = "")
    private String comContent;

    @Schema(description = "조회수", example = "")
    private int comHits;

    @Schema(description = "좋아요", example = "")
    private int comLike;

    @Schema(description = "등록일", example = "")
    private Date comRegDate;

    @Schema(description = "수정일", example = "")
    private Date comModiDate;
}
