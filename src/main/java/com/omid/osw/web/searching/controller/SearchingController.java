package com.omid.osw.web.searching.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/searching")
public class SearchingController {

    @GetMapping(value = "/main")
    public String main(Model model, HttpServletRequest request) {
        return "/web/searching/main";
    }

    @GetMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        return "/web/searching/list";
    }

}
