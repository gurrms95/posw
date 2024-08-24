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
@Schema(description = "복용약 검색")
public class TakingSearchDTO {
    @Schema(description = "약품명", example = "")
    private String itemName;
}
