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
@Schema(description = "사용자 역할(ROLE) 매핑 조회 조건")
public class RoleMappingSerchDTO {
    
    @Schema(description = "역할아이디", example = "ROLE_ADMIN")
    private String roleId;
    @Schema(description = "사용자ID", example = "hongkildong12345")
    private String userId;

}
