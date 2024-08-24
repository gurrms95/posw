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
@Schema(description = "역할(ROLE)")
public class RoleDTO {
    
    @Schema(description = "순번", example = "1")
    private Integer no;
    @Schema(description = "역할아이디", example = "ROLE_ADMIN")
    private String roleId;
    @Schema(description = "역할명", example = "관리자")
    private String roleName;
    @Schema(description = "역할 설명", example = "OSW 시스템 관리자")
    private String roleDescrip;
    @Schema(description = "사용여부", example = "Y")
    private String useYn;
    @Schema(description = "등록일시", example = "20240701 15:00:00")
    private String inpDt;
    @Schema(description = "등록id", example = "abcd12345")
    private String inpId;
    @Schema(description = "수정일시", example = "20240701 15:00:00")
    private String updDt;
    @Schema(description = "수정id", example = "abcd12345")
    private String updId;
    @Schema(description = "그리드 편집 구분", example = "I")
    private String gridStatus;
    @Schema(description = "사용자ID", example = "hongkildong12345")
    private String userId;

}
