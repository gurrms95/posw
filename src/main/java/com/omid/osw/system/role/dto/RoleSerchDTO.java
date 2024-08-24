package com.omid.osw.system.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "역할(ROLE) 조회 조건")
public class RoleSerchDTO {
    
    @Schema(description = "역할명", example = "관리자")
    private String roleName;
    @Schema(description = "역할 설명", example = "OSW 시스템 관리자")
    private String roleDescrip;
    @Schema(description = "사용여부", example = "Y")
    private String useYn;
    @Schema(description = "등록일시", example = "20240701 15:00:00")
    private String inpDt;

}
