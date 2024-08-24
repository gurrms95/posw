package com.omid.osw.web.taking.mapper;

import com.omid.osw.web.taking.dto.TakingDTO;
import com.omid.osw.web.taking.dto.TakingDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TakingMapper {


    /**
     * 복용 등록
     * @param takingDTO
     */
    int insertTaking(TakingDTO takingDTO);

    /**
     * 구분값 가져오기
     * @param inpDt
     */
    int getMaxGubunByDate(String inpDt);

    /**
     * 복용 리스트 가져오기
     * @return List<TakingDTO>
     */
    List<TakingDTO> getTakingScheduleList(TakingDTO takingDTO);

    /**
     * 최근 복용중/복용완료 가져오기
     * @param takingState
     */
    List<TakingDTO> getLatestTakingState(TakingDTO takingState);

    /**
     * 복용 상세 리스트 가져오기
     */
    List<TakingDetailDTO> getTakingDetailList(TakingDetailDTO takingDetailDTO);

    /**
     * 복용일 저장하기
     * @param takingDetailDTO
     * @return
     */
    int saveTakingDetail(TakingDetailDTO takingDetailDTO);

    /**
     * 복용 상세 등록
     * @param takingDetailDTO
     * @return
     */
    int insertTakingDetail(TakingDetailDTO takingDetailDTO);

    /**
     * 가장 최근 삽입된 taking_id 값을 가져온다.
     */
    int getLatestTakingId();

    /***
     * 남은 투약 횟수를 파악하여 복용 완료 여부를 확인한다.
     */
    int checkLastDetail(TakingDetailDTO takingDetailDTO);

    /***
     * 복용 상태를 완료로 저장한다
     */
    void saveTakingState(TakingDTO takingDTO);
}
