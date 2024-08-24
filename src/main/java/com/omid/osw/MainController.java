package com.omid.osw;

import com.omid.osw.common.security.dto.OswUserDetails;
import com.omid.osw.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

@Controller("mainController")
public class MainController {

    @Value("${kakao.map.js.key}")
    private String mapKey;

    @GetMapping(value = "/")
    public String main(Model model, HttpServletRequest request) throws UnsupportedEncodingException {

        OswUserDetails user = SecurityUtils.getAuthenticatedUser();
        String userId = user.getOswUser().getUserId();

        model.addAttribute("isInMain", true);
        model.addAttribute("MAPKEY", mapKey);
        model.addAttribute("userId", userId);

        return "main";
    }
}
