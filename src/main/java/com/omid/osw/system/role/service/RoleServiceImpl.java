package com.omid.osw.system.role.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omid.osw.common.security.dto.OswUser;
import com.omid.osw.common.utils.SecurityUtils;
import com.omid.osw.system.role.dto.RoleDTO;
import com.omid.osw.system.role.dto.RoleMappingDTO;
import com.omid.osw.system.role.dto.RoleMappingSerchDTO;
import com.omid.osw.system.role.dto.RoleSerchDTO;
import com.omid.osw.system.role.mapper.RoleMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 역할(ROLE) 서비스 구현체
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    
    @Override
    public List<RoleDTO> getRoleList(RoleSerchDTO userSerchDTO) {
        return roleMapper.getRoleList(userSerchDTO);
    }

    @Transactional
    @Override
    public void saveRole(List<RoleDTO> roleList) {
        OswUser user = SecurityUtils.getAuthenticatedUser().getOswUser();

        String userId = user.getUserId();

        for (RoleDTO role : roleList) {
            role.setUserId(userId);
            if (role.getGridStatus().equals("I")) {
                this.addRole(role);
            } else if (role.getGridStatus().equals("U")) {
                this.modifyRole(role);
            } else if (role.getGridStatus().equals("D")) {
                this.removeRole(role);
            }
        }        
    }

    @Transactional
    @Override
    public void addRole(RoleDTO roleDTO) {
        roleMapper.addRole(roleDTO);
    }

    @Transactional
    @Override
    public void modifyRole(RoleDTO roleDTO) {
        roleMapper.modifyRole(roleDTO);
    }

    @Transactional
    @Override
    public void removeRole(RoleDTO roleDTO) {
        roleMapper.removeRole(roleDTO);
    }

    @Override
    public List<RoleMappingDTO> getRoleUserMappingList(RoleMappingSerchDTO roleMappingSerchDTO) {
        return roleMapper.getRoleUserMappingList(roleMappingSerchDTO);
    }

    @Transactional
    @Override
    public void saveRoleUserMapping(List<RoleMappingDTO> roleMappingList) {
        OswUser user = SecurityUtils.getAuthenticatedUser().getOswUser();

        String userId = user.getUserId();

        for (RoleMappingDTO mappingInfo : roleMappingList) {
            mappingInfo.setInpId(userId);
            mappingInfo.setUpdId(userId);
            if (mappingInfo.getGridStatus().equals("I")) {
                this.addRoleUserMapping(mappingInfo);
            } else if (mappingInfo.getGridStatus().equals("D")) {
                this.removeRoleUserMapping(mappingInfo);
            }
        }        
    }

    @Transactional
    @Override
    public void addRoleUserMapping(RoleMappingDTO roleMappingDTO) {
        roleMapper.addRoleUserMapping(roleMappingDTO);
    }

    @Transactional
    @Override
    public void removeRoleUserMapping(RoleMappingDTO roleMappingDTO) {
        roleMapper.removeRoleUserMapping(roleMappingDTO);
    }

}
