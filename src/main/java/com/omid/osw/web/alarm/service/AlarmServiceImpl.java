package com.omid.osw.web.alarm.service;

import com.omid.osw.web.alarm.dto.AlarmDTO;
import com.omid.osw.web.alarm.dto.AlarmDetailDTO;
import com.omid.osw.web.alarm.mapper.AlarmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmServiceImpl implements AlarmService{

    @Autowired
    private AlarmMapper alarmMapper;

    @Override
    public List<AlarmDTO> getAlarm(AlarmDTO alarmDTO) {
        List<AlarmDTO> alarmList = alarmMapper.getAlarm(alarmDTO);
        alarmList.forEach(alarm -> {
            List<AlarmDetailDTO> alarmDetail = alarmMapper.getAlarmDetail(alarm);
            alarm.setAlarmDetailDTOList(alarmDetail);
        });
        return alarmList;
    }

    @Override
    public int saveAlarm(AlarmDTO alarmDTO) {
        return alarmMapper.saveAlarm(alarmDTO);
    }

    @Override
    public int hasAlarm(AlarmDTO alarmDTO) {
        List<AlarmDTO> alarm = alarmMapper.getAlarm(alarmDTO);
        return alarm.size();
    }

    @Override
    public int updateCheckDt(AlarmDTO alarmDTO) {
        return alarmMapper.updateCheckDt(alarmDTO);
    }

}
