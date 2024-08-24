package com.omid.osw.web.envirScore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omid.osw.common.security.dto.OswUserDetails;
import com.omid.osw.common.utils.ApiUtils;
import com.omid.osw.common.utils.SecurityUtils;
import com.omid.osw.web.envirScore.dto.EnvirScoreDTO;
import com.omid.osw.web.envirScore.service.EnvirScoreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller("EnvirScoreController")
@RequiredArgsConstructor
@RequestMapping(value = "/envir_score")
public class EnvirScoreController {

    @Value("${kakao.map.js.key}")
    private String mKey;


    private final EnvirScoreService envirScoreService;

    private final ApiUtils apiUtils;

    @GetMapping(value = "/my_envir_score") //나의 환경지킴이 점수 조회
    public String myScore(){
        return "/web/envir_score/my_envir_score";
    }

    @GetMapping(value = "/getPoint") //나의 환경지킴이 점수 조회
    public String getPoint(Model model) throws JsonProcessingException {

        OswUserDetails authenticatedUser =  SecurityUtils.getAuthenticatedUser();

        String jsonData = new ObjectMapper().writeValueAsString(apiUtils.getWasteMedicineCollectionBoxApi());

        model.addAttribute("MAPKEY",mKey);
        model.addAttribute("WMCB",jsonData);
        model.addAttribute("USERID",authenticatedUser.getUsername());

        return "/web/envir_score/get_envir_point";
    }

    /**
     * 나의 환경지킴이 점수 조회
     */
    @GetMapping(value = "/getScore") //나의 환경지킴이 점수 조회
    public String getScore(Model model) throws JsonProcessingException {

        getAuthenticateUser(model);

        return "/web/envir_score/my_envir_score";
    }

    private void getAuthenticateUser(Model model) throws JsonProcessingException {
        OswUserDetails authenticatedUser =  SecurityUtils.getAuthenticatedUser();

        EnvirScoreDTO envirScoreDTO = envirScoreService.getUserScore(authenticatedUser.getUsername());

        String jsonEnvirScoreDTO = new ObjectMapper().writeValueAsString(envirScoreDTO);
        model.addAttribute("jsonEnvirScoreDTO", jsonEnvirScoreDTO);

        EnvirScoreDTO envirScoreRankDTO = envirScoreService.getUserRank(authenticatedUser.getUsername());
        String jsonEnvirScoreRankDTO = new ObjectMapper().writeValueAsString(envirScoreRankDTO);
        model.addAttribute("jsonEnvirScoreRankDTO", jsonEnvirScoreRankDTO);
    }

    /**
     * 나의 환경지킴이 점수 분석
     */
    @GetMapping(value = "/getRank") // 나의 환경지킴이 순위 조회
    public String myScoreRank(Model model) throws JsonProcessingException {

        getAuthenticateUser(model);

        EnvirScoreDTO avgScoresDTO = envirScoreService.getAvgScoreRank();
        String jsonAvgScoresDTO = new ObjectMapper().writeValueAsString(avgScoresDTO);
        model.addAttribute("jsonAvgScoresDTO", jsonAvgScoresDTO);

        return "/web/envir_score/my_envir_score_rank";
    }

     /**
     * 나의 환경지킴이 점수 저장
     */
    @PostMapping("/saveEnvirScore")
    public ResponseEntity<?> saveEnvirScore(@RequestBody EnvirScoreDTO envirScoreDTO) {
        envirScoreService.saveEnvirScore(envirScoreDTO);
        return ResponseEntity.ok().build();

    }
}
