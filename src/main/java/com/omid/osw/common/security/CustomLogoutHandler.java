package com.omid.osw.common.security;

import com.omid.osw.common.security.dto.OswUserDetails;
import com.omid.osw.common.security.service.OswUserService;
import com.omid.osw.common.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    private OswUserService oswUserService;

    public CustomLogoutHandler() {

    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OswUserDetails user = SecurityUtils.getAuthenticatedUser();
        String userId = user.getOswUser().getUserId();

        oswUserService.saveLasLoginDt(userId); //사용자 로그아웃 시간 기록 (최근 로그인에 사용)
    }
}
