package com.omid.osw.system.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;


/**
 * 시스템 관리 - 메인 컨트롤러
 */
@Controller("systemMainController")
@RequestMapping(value = "/system")
public class MainController {

    /**
     * 시스템 관리 - 메인화면 호출
     */
    @GetMapping(value = "/main")
    public String main(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        return "/system/main";
    }
    
}
