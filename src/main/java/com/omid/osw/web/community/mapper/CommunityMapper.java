package com.omid.osw.web.community.mapper;

import com.omid.osw.web.community.dto.CommunityDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {
    /**
     * 커뮤니티 리스트 가져오기
     * @return
     */
    List<CommunityDto> getCommunityList();

    /**
     * 해당 커뮤니티 내용
     * @param dto
     * @return
     */
    CommunityDto getCommunity(CommunityDto dto);

    /**
     * 커뮤니티 등록
     * @param dto
     * @return
     */
    void setCommunity(CommunityDto dto);

    /**
     * 커뮤니티 상세
     * @param bno
     * @return
     */
    CommunityDto getcomDetail(int bno);
}
