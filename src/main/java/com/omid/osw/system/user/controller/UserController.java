package com.omid.osw.system.user.controller;

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

import com.omid.osw.system.user.dto.UserDTO;
import com.omid.osw.system.user.dto.UserSerchDTO;
import com.omid.osw.system.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * 시스템 관리 - 사용자 관리 컨트롤러
 */
@RequiredArgsConstructor
@Controller("UserController")
@RequestMapping(value = "/system/user")
public class UserController {

    private final UserService userService;

    /**
     * 사용자 관리 화면
     */
    @GetMapping(value = {"/", "/manage"})
    public String roleManage(Model model, HttpServletRequest request) {
        return "/system/user/manage";
    }

    /**
     * 시스템 사용자 목록 반환
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUserList(UserSerchDTO userSerchDTO) {
        List<UserDTO> userList = userService.getUserList(userSerchDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("userList", userList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
