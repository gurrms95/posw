package com.omid.osw.common.utils;

public class AuthorizationUtils {
	
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";

	/**
	 * 인증 - 최고관리자 여부
	 */
	public static boolean isAdmin() {
		return com.omid.osw.common.utils.SecurityUtils.hasRole(ROLE_ADMIN);
	}

	/**
	 * 인증 - 일반사용자 여부
	 */
	public static boolean isUser() {
		return com.omid.osw.common.utils.SecurityUtils.hasRole(ROLE_USER);
	}

}
