package com.omid.osw.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.omid.osw.common.security.dto.OswUser;
import com.omid.osw.common.security.dto.OswUserDetails;

public class SecurityUtils {

	public static final String ROLE_PREFIX = "ROLE_";
	public static final String ROLE_ANONYMOUS_USER = "ROLE_ANONYMOUS_USER";

	/**
	 * 인증 완료된 사용자 정보 반환
	 */
	public static OswUserDetails getAuthenticatedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
		if (authentication != null && !"anonymousUser".equals(authentication.getName())) {
            return (OswUserDetails) authentication.getPrincipal();
        } else {
            return null;
        }
	}

	/**
	 * 권한 여부를 반환
	 */
	public static boolean hasRole(String role) {

		OswUser oswUser = getAuthenticatedUser().getOswUser();

		if (null == oswUser || StringUtils.isBlank(role)) {
			return false;
		}

		if (!role.startsWith(ROLE_PREFIX)) {
			role = ROLE_PREFIX + role;
		}

		boolean hasRole = false;

		for (GrantedAuthority auth : oswUser.getAuthorities()) {
			if (role.equals(auth.getAuthority())) {
				hasRole = true;
				break;
			}
		}

		return hasRole;
	}

}
