package com.omid.osw.web.alarm.service;

import com.omid.osw.web.alarm.dto.AlarmDTO;

import java.util.List;

public interface AlarmService {

    /**
     * 알람 설정 목록을 조회한다.
     * @return
     */
    List<AlarmDTO> getAlarm(AlarmDTO alarmDTO);

    /**
     * 알람을 설정한다.
     * @param alarmDTO
     */
    int saveAlarm(AlarmDTO alarmDTO);

    /**
     * 알람 설정 유무 확인
     * @param alarmDTO
     * @return
     */
    int hasAlarm(AlarmDTO alarmDTO);

    /**
     * 체크 알람을 설정한다.
     * @param alarmDTO
     */
    int updateCheckDt(AlarmDTO alarmDTO);
}
