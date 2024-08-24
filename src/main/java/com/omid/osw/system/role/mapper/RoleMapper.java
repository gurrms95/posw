package com.omid.osw.system.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.omid.osw.system.role.dto.RoleDTO;
import com.omid.osw.system.role.dto.RoleMappingDTO;
import com.omid.osw.system.role.dto.RoleMappingSerchDTO;
import com.omid.osw.system.role.dto.RoleSerchDTO;

@Mapper
public interface RoleMapper {

    /**
	 * 역할(ROLE) 목록 조회
	 */
    public List<RoleDTO> getRoleList(RoleSerchDTO roleSerchDTO);

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
	 * 사용자 역할(ROLE) 매핑 신규 등록
	 */
    public void addRoleUserMapping(RoleMappingDTO roleMappingDTO);

    /**
	 * 사용자 역할(ROLE) 매핑 삭제
	 */
    public void removeRoleUserMapping(RoleMappingDTO roleMappingDTO);
    
}
