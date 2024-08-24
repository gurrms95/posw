package com.omid.osw.common.security.service;

import com.omid.osw.common.security.dto.OswUser;
import com.omid.osw.common.security.dto.OswUserDetails;
import com.omid.osw.common.security.mapper.OswUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OswUserDetailService implements UserDetailsService {

    private final OswUserMapper oswUserMapper;

    /**
	 * 아이디 및 이메일로 사용자 인증
	 *
	 * @param userId
	 * @return OswUser
	 */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        
        OswUser oswUser = oswUserMapper.getOswUserById(userId);

        if (oswUser == null) {
            throw new UsernameNotFoundException(userId);
        }

        List<String> roles = oswUserMapper.getAuthoritiesById(userId);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if (roles != null && !roles.isEmpty()) {
            oswUser.setUserRole(roles.get(0)); // 시큐리티 로그인을 위한 최상위 권한 1개 세팅

            for(String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role)); // 업무 로직에서 권한 여부 핸들링을 위한 authorities 세팅 (보유 권한 목록)
            }

            oswUser.setAuthorities(authorities);
        }

        return new OswUserDetails(oswUser);
    }
    
}
