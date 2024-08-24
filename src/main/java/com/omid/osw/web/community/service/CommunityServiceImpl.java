package com.omid.osw.web.community.service;

import com.omid.osw.web.community.dto.CommunityDto;
import com.omid.osw.web.community.mapper.CommunityMapper;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommunityServiceImpl implements CommunityService{

    private final CommunityMapper communityMapper;

    @Override
    public List<CommunityDto> getCommunityList() {
        return communityMapper.getCommunityList();
    }

    @Override
    public CommunityDto getCommunity(CommunityDto dto) {
        return null;
    }

    @Override
    public void setCommunity(CommunityDto dto) {
        communityMapper.setCommunity(dto);
    }

    @Override
    public CommunityDto getcomDetail(int bno) {
        return communityMapper.getcomDetail(bno);
    }
}
