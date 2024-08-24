package com.omid.osw.common.security.dto;

import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

/**
 * 사용자 정보
 */
@Data
public class OswUser {

	private String username;
	private String userPassword;
	
	private String userId;
	private String userAcnt;
	private String userSecCd;
	private String userRole;

	private String userKornNam;
	private String userEngNam;
	private String userPhoneNo;
	private String userEmail;
	private String genderSecNae;
	private String userPhoto;
	private String brthDe;
	private String remark;
	private String lasLoginDt;

	private List<GrantedAuthority> authorities;
	
}
