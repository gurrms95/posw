package com.omid.osw.common.security.service.impl;

import com.omid.osw.common.security.dto.OswUser;
import com.omid.osw.common.security.mapper.OswUserMapper;
import com.omid.osw.common.security.service.OswUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OswUserServiceImpl implements OswUserService {

    private final OswUserMapper oswUserMapper;

    @Override
    public OswUser getOswUserById(String userId) {
        return oswUserMapper.getOswUserById(userId);
    }

    @Override
    public void saveLasLoginDt(String userId) {
        oswUserMapper.saveLasLoginDt(userId);
    }
}
