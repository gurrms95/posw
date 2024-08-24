package com.omid.osw.web.taking.service;

import com.omid.osw.common.security.dto.OswUserDetails;
import com.omid.osw.common.utils.SecurityUtils;
import com.omid.osw.web.envirScore.dto.EnvirScoreDTO;
import com.omid.osw.web.envirScore.mapper.EnvirScoreMapper;
import com.omid.osw.web.taking.dto.TakingDTO;
import com.omid.osw.web.taking.dto.TakingDetailDTO;
import com.omid.osw.web.taking.mapper.TakingMapper;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class TakingServiceImpl implements TakingService {

    private final TakingMapper takingMapper;
    private final EnvirScoreMapper envirScoreMapper;


    @Transactional
    @Override
    public void insertTaking(List<TakingDTO> takingList) {
        OswUserDetails user = SecurityUtils.getAuthenticatedUser();
        String userId = user.getOswUser().getUserId();

        LocalDate currentDate = LocalDate.now();
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String inpDt = currentDate.format(formatter1);
        String inpDt2 = currentDateTime.format(formatter2); //동일한 시간에 등록하기 위한 시간 설정

        Integer maxGubun = takingMapper.getMaxGubunByDate(inpDt);

        int initialGubun = (maxGubun == 0) ? 1 : maxGubun + 1;

        takingList.forEach(takingDTO -> {
            takingDTO.setUserId(userId);
            takingDTO.setGubun(initialGubun);
            takingDTO.setInpDt(inpDt2);
            takingMapper.insertTaking(takingDTO);

            //복용상세 insert
            int latestTakingId = takingMapper.getLatestTakingId();
            takingDTO.setTakingId(latestTakingId);
            takingDTO.setInpDt(inpDt);
            this.insertTakingDetail(takingDTO);
        });
        
        //환경지킴이 점수 등록
//        String userId = "testId6";
        EnvirScoreDTO envirScoreDTO = EnvirScoreDTO.builder()
                .userId(userId)
                .takingAddScore(5)
                .build();
        envirScoreMapper.saveEnvirScore(envirScoreDTO);

    }

    @Override
    public Map<String, List<TakingDTO>> getGroupedTakingList(TakingDTO takingDTO) {
        OswUserDetails user = SecurityUtils.getAuthenticatedUser();
        String userId = user.getOswUser().getUserId();
        takingDTO.setUserId(userId);

        List<TakingDTO> takingList = takingMapper.getTakingScheduleList(takingDTO);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return takingList.stream()
                .sorted((t1, t2) -> LocalDate.parse(t2.getInpDt().substring(0, 10)).compareTo(LocalDate.parse(t1.getInpDt().substring(0, 10))))
                .collect(Collectors.groupingBy(TakingDTO::getInpDt, LinkedHashMap::new, Collectors.toList()));
    }

    /**
     * 복용 일정을 가져온다.
     * @param takingDto
     */
    @Transactional
    @Override
    public List<TakingDTO> getTakingDetailList(TakingDTO takingDto) {
        List<TakingDTO> takingDTOList = takingMapper.getTakingScheduleList(takingDto);

        for (TakingDTO takingDTO : takingDTOList) {
            TakingDetailDTO takingDetailDTO = new TakingDetailDTO();
            takingDetailDTO.setUserId(takingDTO.getUserId());
            takingDetailDTO.setGubun(takingDTO.getGubun());
            takingDetailDTO.setInpDt(takingDTO.getInpDt());
            takingDetailDTO.setTkId(takingDTO.getTakingId());

            List<TakingDetailDTO> takingDetailDTOList = takingMapper.getTakingDetailList(takingDetailDTO);

            takingDTO.setTakingDetailDTOList(takingDetailDTOList);
        }

        return takingDTOList;
    }
    /**
     * 최근 복용중/복용완료 가져오기
     * @param takingDTO
     */
    @Transactional
    @Override
    public List<TakingDTO> getLatestTakingState(TakingDTO takingDTO) {
        OswUserDetails user = SecurityUtils.getAuthenticatedUser();
        String userId = user.getOswUser().getUserId();
        takingDTO.setUserId(userId);

        return takingMapper.getLatestTakingState(takingDTO);
    }

    /**
     * 복용일 저장하기
     * @param takingDetailDTO
     * @return
     */
    @Override
    public int saveTakingDetail(TakingDetailDTO takingDetailDTO) {
        int result = takingMapper.saveTakingDetail(takingDetailDTO);
        int cnt = takingMapper.checkLastDetail(takingDetailDTO);

        if (cnt < 1) { //모두 복용했다면
            TakingDTO takingDTO = new TakingDTO();
            takingDTO.setUserId(takingDetailDTO.getUserId());
            takingDTO.setGubun(takingDetailDTO.getGubun());
            takingDTO.setTakingId(takingDetailDTO.getTkId());
            List<TakingDTO> tkList = takingMapper.getTakingScheduleList(takingDTO);
            tkList.forEach( tk -> {
                if( tk.getTakingId() == takingDetailDTO.getTkId() ) {
                    if ( tk.getTakingState() < 1 ) { //최초 1회만 점수 부여
                        //taking 테이블 takingState 1로 업데이트
                        takingMapper.saveTakingState(tk);
                        //환경지킴이 점수++
                        EnvirScoreDTO envirScoreDTO = new EnvirScoreDTO();
                        envirScoreDTO.setUserId(tk.getUserId());
                        envirScoreDTO.setTakingEndScore(5);
                        envirScoreMapper.saveEnvirScore(envirScoreDTO);
                    }
                }
            });
        }

        return result;
    }

    @Override
    public int insertTakingDetail(TakingDTO takingDTO) {
        int dailyDoseCnt = takingDTO.getDailyDoseCnt();
        int allTakingDt = takingDTO.getAllTakingDt();

        int rowCnt = dailyDoseCnt*allTakingDt;
        if (rowCnt > 0) {
            for (int i = 1; i <= rowCnt; i++) {

                TakingDetailDTO takingDetailDTO = new TakingDetailDTO();
                takingDetailDTO.setTkId(takingDTO.getTakingId());
                takingDetailDTO.setTkSub(String.valueOf(i));
                takingDetailDTO.setUserId(takingDTO.getUserId());
                takingDetailDTO.setGubun(takingDTO.getGubun());
                takingDetailDTO.setInpDt(takingDTO.getInpDt());

                takingMapper.insertTakingDetail(takingDetailDTO);
            }
        }
        return rowCnt;
    }
}
