package com.omid.osw.system.code.contoller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 시스템 관리 - 공통코드 관리 컨트롤러
 */
@Controller("CodeController")
@RequestMapping(value = "/system/code")
public class CodeController {

    /**
     * 공통코드 관리 화면
     */
    @GetMapping(value = {"/", "/manage"})
    public String manage(Model model, HttpServletRequest request) {
        model.addAttribute("isVisible", true);
        return "/system/code/manage";
    }

    /**
     * 공통코드 관리 - 리스트 조회
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCodeList() {

        List<Map<String, Object>> codeList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {

            Map<String, Object> row = new HashMap<>();

            row.put("no", i + 1);
            row.put("rerstCd", "A01");
            row.put("rerstCdNae", "사용자 엔티티");
            row.put("cmmnCd", "A01002");
            row.put("cdNae", "약사");
            row.put("useYn", "Y");
            row.put("inpDt", "2024/06/30");
            
            codeList.add(row);
        }
        
        Map<String, Object> response = new HashMap<>();

        response.put("codeList", codeList);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 공통코드 관리 - 코드 상세 정보 조회
     */
    @GetMapping("/detail")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCodeDetail() {

        Map<String, Object> code = new HashMap();

        code.put("rerstCd", "A01");
        code.put("rerstCdNae", "사용자 엔티티");
        code.put("cmmnCd", "A01002");
        code.put("cdNae", "약사");
        code.put("useYn", "Y");
        code.put("inpDt", "2024/06/30");
        
        Map<String, Object> response = new HashMap<>();

        response.put("code", code);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
