package com.omid.osw.system.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "사용자 조회 조건")
public class UserSerchDTO {
    
    @Schema(description = "사용자계정일련번호", example = "1")
    private Integer userSen;
    @Schema(description = "사용자계정아이디", example = "abcd12345")
    private String userId;
    @Schema(description = "한글성명", example = "홍길동")
    private String userKornNam;
    @Schema(description = "역할아이디", example = "ROLE_ADMIN")
    private String roleId;

}
