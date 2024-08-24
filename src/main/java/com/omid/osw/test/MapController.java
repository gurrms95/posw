package com.omid.osw.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map")
public class MapController {

    @Value("${kakao.map.js.key}")
    private String mKey;

    @GetMapping("/test.do")
    public String getMap(Model model){

        model.addAttribute("MAPKEY",mKey);

        return "map/map";
    }

}
