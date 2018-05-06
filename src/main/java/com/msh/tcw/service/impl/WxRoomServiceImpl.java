package com.msh.tcw.service.impl;

import com.msh.tcw.core.AbstractService;
import com.msh.tcw.dao.WxRoomMapper;
import com.msh.tcw.dao.WxUserMapper;
import com.msh.tcw.model.Room;
import com.msh.tcw.model.WxUser;
import com.msh.tcw.service.WxRoomService;
import com.msh.tcw.service.WxUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/01/03.
 */
@Service
@Transactional
public class WxRoomServiceImpl extends AbstractService<Room> implements WxRoomService {
    @Resource
    private WxRoomMapper wxRoomMapper;

}
