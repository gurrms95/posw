package com.omid.osw.system.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.omid.osw.system.user.dto.UserDTO;
import com.omid.osw.system.user.dto.UserSerchDTO;

@Mapper
public interface UserMapper {

    /**
	 * 사용자 목록 조회
	 */
    List<UserDTO> getUserList(UserSerchDTO userSerchDTO);

    /**
	 * 사용자 상세정보 조회
	 */
    UserDTO getUser(UserSerchDTO userSerchDTO);
    
}
