package com.omid.osw.web.taking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "복용상세")
public class TakingDetailDTO {
    @Schema(description = "복용 ID", example = "")
    private int tkId;
    @Schema(description = "복용 SUB", example = "")
    private String tkSub;
    @Schema(description = "사용자 ID", example = "")
    private String userId;
    @Schema(description = "구분", example = "")
    private int gubun;
    @Schema(description = "등록일자", example = "")
    private String inpDt;
    @Schema(description = "복용유무", example = "")
    private boolean isTk;
    @Schema(description = "복용일자", example = "")
    private String tkDt;
}
