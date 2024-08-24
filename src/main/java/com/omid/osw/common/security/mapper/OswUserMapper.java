package com.omid.osw.common.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.omid.osw.common.security.dto.OswUser;

@Mapper
public interface OswUserMapper {
    
    /**
	 * 사용자 조회
	 * @param id
	 * @return
	 */
	public OswUser getOswUserById(String id);

	/**
	 * 사용자 역할(ROLE) 조회
	 */
	public List<String> getAuthoritiesById(String userId);

	/**
	 * 사용자 로그아웃 시간 기록
	 * @param userId
	 */
    void saveLasLoginDt(String userId);
}
