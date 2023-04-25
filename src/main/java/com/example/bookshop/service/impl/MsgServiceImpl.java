package com.example.bookshop.service.impl;

import com.example.bookshop.bean.ChatLink;
import com.example.bookshop.bean.Msg;
import com.example.bookshop.dao.MsgDao;
import com.example.bookshop.service.MsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MsgServiceImpl implements MsgService {
    @Resource
    private MsgDao msgDao;

    @Override
    public List<Msg> GetMsgService(String msgFrom, String msgTo) {
        List<Msg> msg1 = msgDao.getHistoryMsg(msgFrom,msgTo);
        List<Msg> msg2 = msgDao.getHistoryMsg(msgTo,msgFrom);
        List<Msg> msgList = Stream.of(msg1, msg2).flatMap(Collection::stream).collect(Collectors.toList());
        Collections.sort(msgList);
        return msgList;
    }
    

    @Override
    public int deleteMsgAndLinkService(String msgFrom, String msgTo) {
        List<Msg> msgList = GetMsgService(msgFrom, msgTo);
        //对应的历史消息ID列表
        List<Integer> msgIdList = msgList.stream().map(m -> m.getMsgId()).collect(Collectors.toList()); 
        //对应的会话列表ID列表
        List<Integer> linkIdList = msgDao.getLinkIdList(msgFrom, msgTo);
        
        //执行对应的删除操作，先删除对话列表后删除聊天记录
        int flag = 0;   //删除会话列表及聊天记录失败
        if (msgDao.deleteLinkByList(linkIdList) != 0){
            flag =  msgDao.deleteMsgByList(msgIdList);  //返回值是删除的条数
            if (flag != 0){
                flag = 1;   //删除会话列表及聊天记录成功
            } else {
                flag = 2;   //聊天记录删除失败！
            }
        }
        return flag;
    }

    @Override
    public int insertMsgService(Msg msg) {
        return msgDao.insertMsg(msg);
    }

    @Override
    public List<String> getChatListService(String user) {
        List<ChatLink> chatLinkList = msgDao.getChatLinkList(user);
        List<String> list = new ArrayList<>();
        for(ChatLink c : chatLinkList){
            list.add(c.getFromUser());
            list.add(c.getToUser());
        }
        //去重
        list = list.stream().distinct().collect(Collectors.toList());
        //去除当前user
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String item = iterator.next();
            if (item.equals(user)){
                iterator.remove();
            }
        }
        return list;
    }

    @Override
    public int insertChatLinkService(String fromUser, String toUser) {
        int flag = 0;
        ChatLink chatLink = msgDao.IsChatLink(fromUser,toUser);
        if (chatLink == null){
            ChatLink newLink = new ChatLink();
            newLink.setFromUser(fromUser);
            newLink.setToUser(toUser);
            flag = msgDao.insertChatLink(newLink);
            if (flag != 1){
                //插入成功时flag=1，不成功时flag=0
                flag = 0;
            }
        } else {
            flag = 2;
        }
        return flag;
    }

    
}
