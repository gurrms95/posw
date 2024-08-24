package com.omid.osw.web.envirScore.service;

import com.omid.osw.web.envirScore.dto.EnvirScoreDTO;
import com.omid.osw.web.envirScore.mapper.EnvirScoreMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EnvirScoreServiceImpl implements EnvirScoreService {

    @Autowired
    private final EnvirScoreMapper envirScoreMapper;

    @Override
    public int addUser(EnvirScoreDTO envirScoreDTO) {
        return envirScoreMapper.addUser(envirScoreDTO);
    }

    @Override
    public int removeUser(String userId) {
        return envirScoreMapper.removeUser(userId);
    }

    @Override
    public int saveEnvirScore(EnvirScoreDTO envirScoreDTO) {
        return envirScoreMapper.saveEnvirScore(envirScoreDTO);
    }

    @Override
    public EnvirScoreDTO getUserScore(String userId) {
        return envirScoreMapper.getUserScore(userId);
    }

    @Override
    public EnvirScoreDTO getUserRank(String userId) {
        return envirScoreMapper.getUserRank(userId);
    }

    @Override
    public int updateUserScore() {
        return 0;
    }

    @Override
    public EnvirScoreDTO getAvgScoreRank() {
        return envirScoreMapper.getAvgScoreRank();
    }
}
