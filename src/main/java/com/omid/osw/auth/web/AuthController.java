package com.omid.osw.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.omid.osw.common.exception.BizException;

/**
 * 인증(로그인) 컨트롤러
 */
@Controller("authController")
@RequestMapping(value = "/auth")
public class AuthController {

    /**
     * 로그인 페이지
     */
    @GetMapping("/loginPage")
    public String goToLoginPage() {
        return "/auth/loginPage";
    }

    /**
     * 로그인 에러 페이지
     */
    @GetMapping("/login/error")
    public String goToLoginPageWithError(Model model) {
        throw new BizException("ID 또는 비밀번호를 확인해주세요.");
    }

    /**
     * 회원 가입 페이지
     */
    @GetMapping("/joinPage")
    public String goToJoinPage(Model model) {
        return "/auth/joinPage";
    }

    /**
     * 아이디 및 비밀번호찾기 페이지
     */
    @GetMapping("/forgetPage")
    public String goToForgetPage(Model model) {
        return "/auth/forgetPage";
    }

}