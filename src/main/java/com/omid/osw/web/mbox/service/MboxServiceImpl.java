package com.omid.osw.web.mbox.service;

import com.omid.osw.web.mbox.mapper.MboxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MboxServiceImpl implements MboxService{

    @Autowired
    private MboxMapper mboxMapper;

    @Override
    public void getTest() {
        mboxMapper.getTest();
    }
}
