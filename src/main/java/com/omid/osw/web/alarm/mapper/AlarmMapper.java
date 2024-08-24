package com.omid.osw.web.alarm.mapper;

import com.omid.osw.web.alarm.dto.AlarmDTO;
import com.omid.osw.web.alarm.dto.AlarmDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlarmMapper {

    List<AlarmDTO> getAlarm(AlarmDTO alarmDTO);

    List<AlarmDetailDTO> getAlarmDetail(AlarmDTO alarmDTO);

    int saveAlarm(AlarmDTO alarmDTO);

    int updateCheckDt(AlarmDTO alarmDTO);
}
