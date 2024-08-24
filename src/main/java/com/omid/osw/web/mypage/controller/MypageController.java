package com.omid.osw.web.mypage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omid.osw.common.security.dto.OswUser;
import com.omid.osw.common.security.dto.OswUserDetails;
import com.omid.osw.common.security.service.OswUserService;
import com.omid.osw.common.utils.SecurityUtils;
import com.omid.osw.web.envirScore.dto.EnvirScoreDTO;
import com.omid.osw.web.envirScore.service.EnvirScoreService;
import com.omid.osw.web.taking.dto.TakingDTO;
import com.omid.osw.web.taking.service.TakingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final TakingService takingService;
    private final EnvirScoreService envirScoreService;
    private final OswUserService oswUserService;

    /***
     * 마이페이지 화면 이동
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/home")
    public String gotoMyPage(Model model) throws JsonProcessingException {
        OswUserDetails userDetails = SecurityUtils.getAuthenticatedUser();
        String userId = userDetails.getOswUser().getUserId();
        TakingDTO takingDTO = new TakingDTO();
        takingDTO.setUserId(userId);

        //1) 최근 복용 내역(리스트 3개만 가져오기)
        List<TakingDTO> tkDtlList = takingService.getTakingDetailList(takingDTO);
        List<TakingDTO> limitMax3;
        if (tkDtlList.size() > 3) {
            limitMax3 = new ArrayList<>(tkDtlList.subList(0, 3));
        } else {
            limitMax3 = new ArrayList<>(tkDtlList);
        }

        //2)환경지킴이 포인트
        EnvirScoreDTO envirScore = envirScoreService.getUserScore(userId); //로그인 사용자 정보로 코드 변경해줘야 함
        EnvirScoreDTO envirRank = envirScoreService.getUserRank(userId);
        String jsonEnvirScore = new ObjectMapper().writeValueAsString(envirScore);

        //3 개인 정보
//        OswUser user = oswUserService.getOswUserById(userId);
        OswUser user = oswUserService.getOswUserById(userId);

        model.addAttribute("tkDtlList", limitMax3);
        model.addAttribute("envirScore", jsonEnvirScore);
        model.addAttribute("envirRank", envirRank);
        model.addAttribute("user", user);


        return "/web/mypage/home";
    }
}
