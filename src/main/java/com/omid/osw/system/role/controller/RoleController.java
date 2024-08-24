package com.omid.osw.system.role.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.omid.osw.common.security.dto.OswUser;
import com.omid.osw.common.utils.SecurityUtils;
import com.omid.osw.system.role.dto.RoleDTO;
import com.omid.osw.system.role.dto.RoleMappingDTO;
import com.omid.osw.system.role.dto.RoleMappingSerchDTO;
import com.omid.osw.system.role.dto.RoleSerchDTO;
import com.omid.osw.system.role.service.RoleService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 시스템 관리 - 권한(역할, 사용자별 역할 매핑, 메뉴별 역할 매핑) 관리 컨트롤러
 */
@Log4j2
@RequiredArgsConstructor
@Controller("RoleController")
@RequestMapping(value = "/system/role")
public class RoleController {

    private final RoleService roleService;

    /**
     * 역할(ROLE) 관리 화면
     */
    @GetMapping(value = {"/", "/manage"})
    public String roleManage(Model model, HttpServletRequest request) {

        OswUser user = SecurityUtils.getAuthenticatedUser().getOswUser(); // 로직에서 로그인 유저 정보가 필요할 때 꺼내서 사용 ( ID : user.getUserId(); )
        boolean isAdmin = com.omid.osw.common.utils.AuthorizationUtils.isAdmin(); // 권한 보유 여부를 확인 하고싶을 때 사용 - true
        boolean isUser = com.omid.osw.common.utils.AuthorizationUtils.isUser(); // 권한 보유 여부를 확인 하고싶을 때 사용 - false

        log.debug("getAuthenticatedUser : ", user);
        log.debug("isAdmin : ", isAdmin);
        log.debug("isUser : ", isUser);

        return "/system/role/manage";
    }

    /**
     * 역할(ROLE) 목록 반환
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getRoleList(RoleSerchDTO roleSerchDTO) {
        List<RoleDTO> roleList = roleService.getRoleList(roleSerchDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("roleList", roleList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 역할(ROLE) 저장
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveRole(@RequestBody List<RoleDTO> roles) {
        this.roleService.saveRole(roles);
        return ResponseEntity.ok("success");
    }

    /**
     * 역할(ROLE)별 사용자 매핑 관리 화면
     */
    @GetMapping(value = "/user/manage")
    public String userMappingManage(Model model, HttpServletRequest request) {
        return "/system/role/user_mapping/manage";
    }

    /**
     * 역할(ROLE)별 사용자 매핑 목록 반환
     */
    @GetMapping("/userMapping/list")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getRoleUserMappingList(RoleMappingSerchDTO roleMappingSerchDTO) {
        List<RoleMappingDTO> userMappingList = roleService.getRoleUserMappingList(roleMappingSerchDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("userMappingList", userMappingList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 역할(ROLE)별 사용자 매핑 저장
     */
    @PostMapping("/userMapping/save")
    public ResponseEntity<String> saveRoleUserMapping(@RequestBody List<RoleMappingDTO> roles) {
        this.roleService.saveRoleUserMapping(roles);
        return ResponseEntity.ok("success");
    }
    
}
