package com.omid.osw.system.user.service;

import java.util.List;

import com.omid.osw.system.user.dto.UserDTO;
import com.omid.osw.system.user.dto.UserSerchDTO;

/**
 * 시스템 사용자 서비스
 */
public interface UserService {

    /**
	 * 사용자 목록 조회
	 */
    List<UserDTO> getUserList(UserSerchDTO userSerchDTO);

    /**
	 * 사용자 상세정보 조회
	 */
    UserDTO getUser(UserSerchDTO userSerchDTO);

}
