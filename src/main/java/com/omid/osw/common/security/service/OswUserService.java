package com.omid.osw.common.security.service;

import com.omid.osw.common.security.dto.OswUser;

public interface OswUserService {
    OswUser getOswUserById(String userId);

    void saveLasLoginDt(String userId);
}
