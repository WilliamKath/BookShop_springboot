package com.example.bookshop.dao;
import com.example.bookshop.bean.ChatLink;
import com.example.bookshop.bean.Msg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface MsgDao {
    //插入消息
    public int insertMsg(Msg msg);

    //通过发送者与接收者查找历史消息
    public List<Msg> getHistoryMsg(String msgFrom, String msgTo);

    //查找当前用户的所有会话列表
    public List<ChatLink> getChatLinkList(String user);

    //查找当前用户与其他用户的会话是否已存在
    public ChatLink IsChatLink(String fromUser, String toUser);

    //新建一个会话列表
    public int insertChatLink(ChatLink chatLink);
    
    //删除当前用户与另一位用户的所有聊天记录
    public int deleteMsgByList(List<Integer> ids);
    
    //删除当前用户与另一位用户的所有会话列表
    public int deleteLinkByList(List<Integer> ids);
    
    //
    public List<Integer> getLinkIdList(String fromUser, String toUser);
}
