package com.example.bookshop.service;

import com.example.bookshop.bean.ChatLink;
import com.example.bookshop.bean.Msg;

import java.util.List;

public interface MsgService {
    //查找历史消息
    public List<Msg> GetMsgService(String msgFrom, String msgTo);
    //插入新消息
    public int insertMsgService(Msg msg);

    //查找当前用户的所有会话列表
    public List<String> getChatListService(String user);

    //新建一个用户会话（包含判断是否已存在）
    public int insertChatLinkService(String fromUser, String toUser);
    
    //查找并删除当前用户与另一位用户的历史消息及会话列表
    public int deleteMsgAndLinkService(String msgFrom, String msgTo);
    
}
