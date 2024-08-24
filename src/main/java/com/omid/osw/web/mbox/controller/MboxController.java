package com.omid.osw.web.mbox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omid.osw.common.utils.ApiUtils;
import com.omid.osw.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mbox")
public class MboxController {

    @Value("${kakao.map.js.key}")
    private String mKey;

    @Autowired
    private ApiUtils apiUtils;

    @GetMapping(value = "/mboxDescrpt.do")
    public String mboxDescrpt() throws JsonProcessingException {
        return "web/mbox/mboxDescript";
    }

    @GetMapping(value = "/mbox.do")
    public String mbox(Model model) throws JsonProcessingException {

        String jsonData = new ObjectMapper().writeValueAsString(apiUtils.getWasteMedicineCollectionBoxApi());

        model.addAttribute("MAPKEY",mKey);
        model.addAttribute("WMCB",jsonData);

        return "web/mbox/mbox";
    }

    @GetMapping(value = "/mboxSearch.do")
    public String mboxSearch(Model model) throws JsonProcessingException {

        String jsonData = new ObjectMapper().writeValueAsString(apiUtils.getWasteMedicineCollectionBoxApi());

        model.addAttribute("MAPKEY",mKey);
        model.addAttribute("WMCB",jsonData);

        return "web/mbox/mboxSerach";
    }
}
