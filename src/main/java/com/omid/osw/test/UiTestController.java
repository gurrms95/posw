package com.omid.osw.test;

import com.omid.osw.common.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ui")
public class UiTestController {

    @GetMapping("/test1")
    public String uiTest1() {
        return "/ui/test1";
    }

    @GetMapping("/test2")
    public String uiTest2() {
        return "/ui/test2";
    }

    @GetMapping("/test3")
    public String uiTest3() {
        return "/ui/test3";
    }

}