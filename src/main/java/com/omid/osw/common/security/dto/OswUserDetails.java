package com.omid.osw.common.security.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class OswUserDetails implements UserDetails {

    private OswUser oswUser;

    public OswUserDetails(OswUser oswUser) {
        this.oswUser = oswUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return oswUser.getUserRole();
            }
        });

        return collection;
    }

    public OswUser getOswUser() {
        return oswUser;
    }

    @Override
    public String getPassword() {
        return oswUser.getUserPassword(); // 비밀번호 필드 반환
    }

    @Override
    public String getUsername() {
        return oswUser.getUserId(); // 사용자 ID 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 반환
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 반환
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부 반환
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부 반환
    }
}
