package com.msh.tcw.service;

import com.msh.tcw.model.Message;

import java.util.List;

/**
 * Created by weizhongjia on 2018/5/13.
 */
public interface MessageService {
    void insertMessage(Message message);
    List<Message> selectMessageByRoomIdAndTime(int roomId, long time);
}
