package com.omid.osw.web.taking.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omid.osw.common.utils.ApiUtils;
import com.omid.osw.web.taking.dto.TakingDTO;
import com.omid.osw.web.taking.dto.TakingDetailDTO;
import com.omid.osw.web.taking.dto.TakingSearchDTO;
import com.omid.osw.web.taking.service.TakingService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 복용 관련 Controller
 *
 * @author 고용수
 * @date 2024. 8. 1.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/taking")
public class TakingController {

    private final ApiUtils apiUtils;
    private final TakingService takingService;

    @Operation(summary = "검색 화면", description = "검색 화면 호출")
    @GetMapping(value = "/search")
    public String search(Model model, HttpServletRequest request) {
        return "/web/taking/taking_search";
    }

    @Operation(summary = "검색 API", description = "검색한 결과를 화면에 리턴")
    @PostMapping(value = "/getSearch")
    public ResponseEntity<String> getSearch(@RequestBody TakingSearchDTO takingSearchDTO) throws Exception {
        ResponseEntity<String> apiResponse = apiUtils.getMediApi(takingSearchDTO.getItemName());
        String response = apiResponse.getBody();
        if (StringUtils.isEmpty(response)) {
            return ResponseEntity.ok("");
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "검색 상세", description = "검색 상세 화면 호출")
    @GetMapping(value = "/searchDetail")
    public String getSearchDetail(@RequestParam("itemName") String itemName, Model model) throws Exception {
        ResponseEntity<String> apiResponse = apiUtils.getMediApi(itemName);
        String response = apiResponse.getBody();

        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, Object>> dataArr;

        if (StringUtils.isEmpty(response)) {
            dataArr = new ArrayList<>();
        } else{
            dataArr = mapper.readValue(response, new TypeReference<List<Map<String,Object>>>() {});
        }

        model.addAttribute("dataArr", dataArr);
        model.addAttribute("searchItemName", itemName);

        return "/web/taking/taking_search_detail";
    }

    @Operation(summary = "복용 등록 메인", description = "복용 등록 메인 화면 호출")
    @GetMapping(value = "/registrationMain")
    public String registration(Model model) {
        TakingDTO takingDTO = new TakingDTO();
        takingDTO.setTakingState(0);
        model.addAttribute("latestTakingList", takingService.getLatestTakingState(takingDTO));
        takingDTO.setTakingState(1);
        model.addAttribute("latestTakedList", takingService.getLatestTakingState(takingDTO));
        return "/web/taking/taking_registration";
    }

    @Operation(summary = "복용 등록 폼", description = "복용 등록 폼 화면 호출")
    @GetMapping(value = "/registrationForm")
    public String registrationForm(Model model, HttpServletRequest request) {
        return "/web/taking/taking_registration_form";
    }

    @Operation(summary = "복용 등록", description = "복용 등록 폼 화면에서 등록 시 Insert")
    @PostMapping(value = "/insertTaking")
    @ResponseBody
    public ResponseEntity<String> insertTaking(@RequestBody List<TakingDTO> takingList) {

        takingService.insertTaking(takingList);

        return ResponseEntity.ok("등록이 완료되었습니다.(환경지킴이점수 5점 적립!!)");
    }

    @Operation(summary = "복용 일정 리스트", description = "복용 일정 리스트 화면 호출")
    @GetMapping(value = "/scheduleList")
    public String getScheduleList(TakingDTO takingDTO, Model model){
        model.addAttribute("groupedTakingList", takingService.getGroupedTakingList(takingDTO));
        return "/web/taking/taking_schedule_list";
    }

    @Operation(summary = "복용 상세 화면", description = "복용 상세 화면 호출")
    @GetMapping(value = "/takingDetail/{userId}/{gubun}/{inpDt}")
    public String getTakingDetailPage(@ModelAttribute TakingDTO takingDTO, Model model){
        model.addAttribute("paramUserId", takingDTO.getUserId());
        model.addAttribute("paramGubun", takingDTO.getGubun());
        model.addAttribute("paramInpDt", takingDTO.getInpDt());

        return "/web/taking/taking_detail";
    }

    @Operation(summary = "복용 상세 저장", description = "복용 상세 저장")
    @PostMapping(value = "/saveTakingDetail")
    @ResponseBody
    public int saveTakingDetail(@RequestBody TakingDetailDTO takingDetailDTO){
        return takingService.saveTakingDetail(takingDetailDTO);
    }

    @Operation(summary = "복용 상세 조회", description = "복용 상세 조회(데이터)")
    @PostMapping(value = "/takingDetailData")
    @ResponseBody
    public ResponseEntity<List<TakingDTO>> getTakingDetailData(@RequestBody TakingDTO takingDTO){
        List<TakingDTO> dtoList = takingService.getTakingDetailList(takingDTO);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @Operation(summary = "테스트 데이터", description = "Mock 데이터")
    @GetMapping("/getDataTableData")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getDataTableData() {

        List<Map<String, Object>> mockData = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {

            Map<String, Object> row = new HashMap<>();

            row.put("key1", i + 1);
            row.put("key2", "NO IMAGE");
            row.put("key3", "비타민디/Vitamin D3-Vicotrat Inj.");
            row.put("key4", "리퓨어헬스케어(주)/REPURE Healthcare Inc.");
            row.put("key5", "전문의약품");
            row.put("key6", "2016-2437");
            row.put("key7", "비타민 A 및 D제");
            
            mockData.add(row);
        }
        
        Map<String, Object> response = new HashMap<>();

        response.put("data", mockData);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
