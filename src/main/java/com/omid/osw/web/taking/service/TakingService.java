package com.omid.osw.web.taking.service;

import com.omid.osw.web.taking.dto.TakingDTO;
import com.omid.osw.web.taking.dto.TakingDetailDTO;

import java.util.List;
import java.util.Map;

public interface TakingService {
    /**
     * 복용 상세 리스트 가져오기
     * @param takingDTO
     * @return
     */
    List<TakingDTO> getTakingDetailList(TakingDTO takingDTO);

    /**
     * 복용 상세 리스트 가져오기
     * @param takingDTO
     * @return
     */
    Map<String, List<TakingDTO>> getGroupedTakingList(TakingDTO takingDTO);

    /**
     * 복용 등록
     * @param takingList
     */
    void insertTaking(List<TakingDTO> takingList);

    /**
     * 최근 복용중/복용완료 가져오기
     * @param takingDTO
     */
    List<TakingDTO> getLatestTakingState(TakingDTO takingDTO);

    /**
     * 복용일 저장하기
     * @param takingDetailDTO
     * @return
     */
    int saveTakingDetail(TakingDetailDTO takingDetailDTO);

    /**
     * 복용 상세 등록
     * @param takingDTO
     * @return
     */
    int insertTakingDetail(TakingDTO takingDTO);
}
