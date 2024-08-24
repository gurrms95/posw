package com.omid.osw.common.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.authenticator.SpnegoAuthenticator;

public interface LogoutHandler {
    void logout(HttpServletRequest request, HttpServletResponse response, SpnegoAuthenticator.AuthenticateAction authenticateAction);
}
