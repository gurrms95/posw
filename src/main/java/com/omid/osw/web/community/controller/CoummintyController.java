package com.omid.osw.web.community.controller;

import com.omid.osw.common.security.dto.OswUserDetails;
import com.omid.osw.common.utils.SecurityUtils;
import com.omid.osw.web.community.dto.CommunityDto;
import com.omid.osw.web.community.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/osw")
public class CoummintyController {

    private final CommunityService communityService;

    @Operation(summary = "커뮤니티 접속", description = "")
    @GetMapping("/comView")
    public String getComView(Model model){
        List<CommunityDto> list =  communityService.getCommunityList();

        model.addAttribute("COMLIST",list);
        return "/web/community/com_view";
    }

    @Operation(summary = "커뮤니티 등록페이지", description = "")
    @GetMapping("/comRegist")
    public String getCommunity(Model model){
        SecurityUtils utils = new SecurityUtils();
        OswUserDetails details =  utils.getAuthenticatedUser();

        model.addAttribute("USERNM",details.getUsername());
        model.addAttribute("USERROLE",details.getAuthorities());
        return "/web/community/com_regist";
    }

    @Operation(summary = "커뮤니티 등록페이지", description = "")
    @PostMapping("/setComRegist")
    public String setCommunity(CommunityDto dto){

        communityService.setCommunity(dto);

        return "/web/community/com_view";
    }

    @Operation(summary = "커뮤니티 상세", description = "")
    @GetMapping("/comDetail")
    public String getcomDetail(@RequestParam("bno") int bno, Model model){

        SecurityUtils utils = new SecurityUtils();
        OswUserDetails details =  utils.getAuthenticatedUser();

        CommunityDto dto =  communityService.getcomDetail(bno);

        model.addAttribute("USERNM",details.getUsername());
        model.addAttribute("COMDETAIL",dto);

        return "/web/community/com_detail";
    }
}
