package com.omid.osw.web.envirScore.service;

import com.omid.osw.web.envirScore.dto.EnvirScoreDTO;

import java.util.List;

public interface EnvirScoreService {

    /**
     * 회원계정 활성화
     */
    public int addUser(EnvirScoreDTO envirScoreDTO);

    /**
     * 회원점수 비활성화
     */
    public int removeUser(String userId);

    /**
     * 점수 등록
     */
    public int saveEnvirScore(EnvirScoreDTO envirScoreDTO);

    /**
     * 나의 점수 조회
     * @return EnvirScoreDTO
     */
    public EnvirScoreDTO getUserScore(String userId);


    /**
     * 전체점수 조회
     * @return List<EnvirScoreDTO>
     */
    public EnvirScoreDTO getUserRank(String userId);

    /**
     * 나의 점수 수정
     */
    public int updateUserScore();

    /**
     * 전체 사용자 평균점수
     */
    public EnvirScoreDTO getAvgScoreRank();
}
