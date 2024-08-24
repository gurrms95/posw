package com.omid.osw.system.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.omid.osw.system.user.dto.UserDTO;
import com.omid.osw.system.user.dto.UserSerchDTO;
import com.omid.osw.system.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 시스템 사용자 서비스 구현체
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    
    /**
	 * 사용자 목록 조회
     */
    public List<UserDTO> getUserList(UserSerchDTO userSerchDTO) {
        return userMapper.getUserList(userSerchDTO);
    }

    /**
	 * 사용자 상세정보 조회
     */
    public UserDTO getUser(UserSerchDTO userSerchDTO) {
        return userMapper.getUser(userSerchDTO);
    }

}
