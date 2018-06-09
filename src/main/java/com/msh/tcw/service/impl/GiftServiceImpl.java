package com.msh.tcw.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.msh.tcw.dao.GiftMapper;
import com.msh.tcw.domain.Gift;
import com.msh.tcw.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftServiceImpl implements GiftService{

    @Autowired
    private GiftMapper giftMapper;

    @Override
    public List<Gift> getAllGift() {
        return giftMapper.selectList(new EntityWrapper<>());
    }
}
