package com.omid.osw.web.envirScore.mapper;

import com.omid.osw.web.envirScore.dto.EnvirScoreDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EnvirScoreMapper {

    /**
     * 회원계정 활성화
     * @param envirScoreDTO
     */
    int addUser(EnvirScoreDTO envirScoreDTO);

    /**
     * 회원점수 비활성화
     */
    int removeUser(String suserId);

    /**
     * 점수 등록
     */
    int saveEnvirScore(EnvirScoreDTO envirScoreDTO);

    /**
     * 나의 점수 조회
     * @return EnvirScoreDTO
     */
    EnvirScoreDTO getUserScore(String userId);

    /**
     * 전체점수 조회
     * @return List<EnvirScoreDTO>
     */
    EnvirScoreDTO getUserRank(String userId);

    /**
     * 전체 사용자 평균점수
     */
    EnvirScoreDTO getAvgScoreRank();
}
