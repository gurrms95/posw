package com.omid.osw.system.role.service;

import java.util.List;

import com.omid.osw.system.role.dto.RoleDTO;
import com.omid.osw.system.role.dto.RoleMappingDTO;
import com.omid.osw.system.role.dto.RoleMappingSerchDTO;
import com.omid.osw.system.role.dto.RoleSerchDTO;

/**
 * 역할(ROLE) 사용자 서비스
 */
public interface RoleService {

    /**
	 * 역할(ROLE) 목록 조회
	 */
    public List<RoleDTO> getRoleList(RoleSerchDTO roleSerchDTO);

    /**
	 * 역할(ROLE) 저장
	 */
    public void saveRole(List<RoleDTO> roleList);
    
    /**
	 * 역할(ROLE) 신규 등록
	 */
    public void addRole(RoleDTO roleDTO);

    /**
	 * 역할(ROLE) 수정
	 */
    public void modifyRole(RoleDTO roleDTO);

    /**
	 * 역할(ROLE) 삭제
	 */
    public void removeRole(RoleDTO roleDTO);

    /**
	 * 사용자 역할(ROLE) 매핑 목록 조회
	 */
    public List<RoleMappingDTO> getRoleUserMappingList(RoleMappingSerchDTO roleMappingSerchDTO);

    /**
	 * 사용자 역할(ROLE) 매핑 저장
	 */
    public void saveRoleUserMapping(List<RoleMappingDTO> roleMappingList);
    
    /**
	 * 사용자 역할(ROLE) 매핑 신규 등록
	 */
    public void addRoleUserMapping(RoleMappingDTO roleMappingDTO);

    /**
	 * 사용자 역할(ROLE) 매핑 삭제
	 */
    public void removeRoleUserMapping(RoleMappingDTO roleMappingDTO);

}
