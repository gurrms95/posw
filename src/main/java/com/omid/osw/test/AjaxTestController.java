package com.omid.osw.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * Ajax 호출 테스트를위한 컨트롤러
 */
@Controller
@RequestMapping("/ajaxTest")
public class AjaxTestController {

    /*
     * @return html
     */
    @GetMapping("/getDataTable")
    public String getDataTable() {
        return "/ajaxTest/dataTable";
    }

    /*
     * @return html
     */
    @GetMapping("/getDataTable2")
    public String getDataTable2() {
        return "/ajaxTest/dataTable2";
    }

    /*
     * @return data
     */
    @GetMapping("/getDataTableData")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getDataTableData() {

        List<Map<String, Object>> mockData = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {

            Map<String, Object> row = new HashMap<>();

            row.put("no", i + 1);
            row.put("name", "홍길동");
            row.put("position", "IT 개발자");
            row.put("office", "대전");
            row.put("age", 35);
            row.put("startDate", "2020/05/10");
            row.put("salary", "50,000,000원");
            
            mockData.add(row);
        }
        
        Map<String, Object> response = new HashMap<>();

        response.put("data", mockData);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * @return data
     */
    @GetMapping("/getDataTableData2")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getDataTableData2() {

        List<Map<String, Object>> mockData = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {

            Map<String, Object> row = new HashMap<>();

            row.put("no", i + 1);
            row.put("name", "김영희");
            row.put("position", "디자이너");
            row.put("office", "서울");
            row.put("age", 35);
            row.put("startDate", "2020/03/20");
            row.put("salary", "50,000,000원");
            
            mockData.add(row);
        }
        
        Map<String, Object> response = new HashMap<>();

        response.put("data", mockData);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}