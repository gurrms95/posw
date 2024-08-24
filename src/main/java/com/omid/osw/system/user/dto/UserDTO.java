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
@Schema(description = "사용자")
public class UserDTO {
    
    @Schema(description = "순번", example = "1")
    private Integer no;
    @Schema(description = "사용자계정일련번호", example = "1")
	private Integer userSen;
    @Schema(description = "사용자계정아이디", example = "abcd12345")
    private String userId;
    @Schema(description = "사용자계정비밀번호", example = "abcd12345!")
    private String userPassword;
    @Schema(description = "사용여부", example = "Y")
    private String useYn;
    @Schema(description = "로그인잠금여부", example = "Y")
    private String loginLockYn;
    @Schema(description = "사용자유형코드", example = "A01001")
    private String userSecCd;
    @Schema(description = "비고", example = "특이사항없음")
    private String remark;
    @Schema(description = "가입일자", example = "20240701")
    private String joinDe;
    @Schema(description = "탈퇴일자", example = "99999999")
    private String outDe;
    @Schema(description = "탈퇴사유비고", example = "특이사항없음")
    private String outRemark;
    @Schema(description = "프로필사진", example = "")
    private String userPhoto;
    @Schema(description = "한글성명", example = "홍길동")
    private String userKornNam;
    @Schema(description = "영문성명", example = "HONG GIL DONG")
    private String userEngNam;
    @Schema(description = "전화번호", example = "042-000-0000")
    private String userTelNo;
    @Schema(description = "핸드폰번호", example = "010-0000-0000")
    private String userPhoneNo;
    @Schema(description = "이메일", example = "abcd12345@abc.com")
    private String userEmail;
    @Schema(description = "성별구분명", example = "남")
    private String genderSecNae;
    @Schema(description = "생일", example = "19900101")
    private String brthDe;
    @Schema(description = "내부사용자정보 비고", example = "특이사항없음")
    private String infoRemark;
    @Schema(description = "마지막로그일일자", example = "20240701")
    private String lasLoginDt;
    @Schema(description = "마지막로그인아이피", example = "192.168.0.1")
    private String lasLoginIp;
    @Schema(description = "등록일시", example = "20240701 15:00:00")
    private String inpDt;
    @Schema(description = "등록id", example = "abcd12345")
    private String inpId;
    @Schema(description = "수정일시", example = "20240701 15:00:00")
    private String updDt;
    @Schema(description = "수정id", example = "abcd12345")
    private String updId;

}
