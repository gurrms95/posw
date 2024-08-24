package com.omid.osw.web.alarm.controller;

import com.omid.osw.common.security.dto.OswUserDetails;
import com.omid.osw.common.utils.SecurityUtils;
import com.omid.osw.web.alarm.dto.AlarmDTO;
import com.omid.osw.web.alarm.service.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @Operation(summary = "알람 목록 조회 API", description = "")
    @GetMapping("/getAlarmApi")
    @ResponseBody
    public ResponseEntity<List<AlarmDTO>> getAlarmApi(@ModelAttribute AlarmDTO alarmDTO) {
        OswUserDetails user = SecurityUtils.getAuthenticatedUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        alarmDTO.setUserId(user.getOswUser().getUserId());
        return new ResponseEntity<>(alarmService.getAlarm(alarmDTO), HttpStatus.OK);
    }

    @Operation(summary = "알람 목록 조회", description = "")
    @GetMapping("/getAlarm/{userId}")
    public String getAlarm(@ModelAttribute AlarmDTO alarmDTO, Model model) {
        List<AlarmDTO> alarmList = alarmService.getAlarm(alarmDTO);
        model.addAttribute("alarmList", alarmList);

        return "/web/taking/taking_alarm";
    }

    @Operation(summary = "알람 저장", description = "")
    @PostMapping("/saveAlarm")
    @ResponseBody
    public int saveAlarm(@RequestBody AlarmDTO alarmDTO) {
        return alarmService.saveAlarm(alarmDTO);
    }

    @Operation(summary = "알람 여부 확인", description = "")
    @GetMapping("/hasAlarm/{userId}")
    public ResponseEntity<Integer> hasAlarm(@ModelAttribute AlarmDTO alarmDTO) {
        int cnt = alarmService.hasAlarm(alarmDTO);
        return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
    }

    @Operation(summary = "체크 알람 저장", description = "")
    @PostMapping("/updateCheckDt")
    @ResponseBody
    public int updateCheckDt(@RequestBody AlarmDTO alarmDTO) {
        return alarmService.updateCheckDt(alarmDTO);
    }
}