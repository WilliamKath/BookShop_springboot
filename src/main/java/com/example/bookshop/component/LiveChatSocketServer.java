package com.example.bookshop.component;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.bookshop.bean.Msg;
import com.example.bookshop.service.MsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/LiveChat/{username}/{toUsername}")    //username为当前登录的用户
@Component
public class LiveChatSocketServer {
    /**
     * 注入MsgService
     */
    public static MsgService msgService;

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("toUsername") String toUsername) {
        sessionMap.put(username, session);
        log.info("有新用户加入，username={}, 当前在线人数为：{}", username, sessionMap.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", key);
            // {"username", "zhang", "username": "admin"}
            array.add(jsonObject);
        }
//        {"users": [{"username": "zhang"},{ "username": "admin"}]}
        sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端

        Session toSession = sessionMap.get(username);

        if (!toUsername.equals(username)){  //拿到历史消息
            List<Msg> msgListToAndFrom = msgService.GetMsgService(toUsername, username);
            JSONObject msgList = new JSONObject();
            JSONArray msg = new JSONArray();
            msgList.set("HistoryMsg", msg);
            for (Msg m : msgListToAndFrom) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("from", m.getMsgFrom());  // from 是 zhang
                jsonObject.set("to", m.getMsgTo());  // from 是 zhang
                jsonObject.set("text", m.getMsgText());  // text 同上面的text
                jsonObject.set("time", m.getMsgDate());  // text 同上面的text
                msg.add(jsonObject);
            }
            //sendAllMessage(JSONUtil.toJsonStr(msgList));
            this.sendMessage(JSONUtil.toJsonStr(msgList), toSession);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        //关闭连接时更新用户在线列表
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (Object key : sessionMap.keySet()) {
            //剔除当前用户再次发送在线列表
            if ( key != username) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("username", key);
                // {"username", "zhang", "username": "admin"}
                array.add(jsonObject);
            }
        }
//        {"users": [{"username": "zhang"},{ "username": "admin"}]}

        //sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端

        for (Object key : sessionMap.keySet()){
            if ( !key.equals(username) ) {
                Session toSession = sessionMap.get(key);
                this.sendMessage(JSONUtil.toJsonStr(result), toSession);
            }
        }




        sessionMap.remove(username);
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) {
        log.info("服务端收到用户username={}的消息:{}", username, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
        String text = obj.getStr("text"); // 发送的消息文本  hello
        String time = obj.getStr("time"); // 发送的消息文本  hello
        // {"to": "admin", "text": "聊天文本"}
        Session toSession = sessionMap.get(toUsername); // 根据 to用户名来获取 session，再通过session发送消息文本
        //以下为插入数据库语句
        Msg msg = new Msg();
        msg.setMsgFrom(username);
        msg.setMsgTo(toUsername);
        msg.setMsgText(text);
        msg.setMsgDate(time);
        if (!username.equals(toUsername)) {
            int flag = msgService.insertMsgService(msg);
            if (flag != 1) {
                System.out.println("插入消息到数据库失败！");
            } else {
                System.out.println("插入消息到数据库成功！消息为：" + msg);
                //插入成功后新建会话列表
                int NewFlag = msgService.insertChatLinkService(username, toUsername);
                if (NewFlag != 1) {
                    if (NewFlag == 2) {
                        System.out.println("当前会话已存在列表中！");
                    } else {
                        System.out.println("会话列表插入失败！");
                    }
                } else {
                    System.out.println("会话列表插入成功！");
                }

                //查找会话列表，同时给客户端发送会话列表，前端同步更新
                if (toSession != null) {
                    List<String> chatLinkList = msgService.getChatListService(toUsername);  //查找的会话列表应当是卖家，就是接收消息的用户会话列表
                    //System.out.println("找到的会话列表"+chatLinkList);

                    JSONObject LinkList = new JSONObject();
                    JSONArray linkUser = new JSONArray();
                    LinkList.set("LinkUser",linkUser);
                    for (String link : chatLinkList){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.set("username", link);  // from 是 zhang
                        linkUser.add(jsonObject);
                    }
                    this.sendMessage(LinkList.toString(), toSession);
                    log.info("发送给用户username={}，消息：{}", toUsername, LinkList.toString());
                } else {
                    log.info("发送失败，未找到用户username={}的session", toUsername);
                }
            }
            //System.out.println("msg=" + msg);
        }
        //如果当前用户在线就直接发送消息
        if (toSession != null) {
            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
            // {"from": "zhang", "text": "hello"}
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("from", username);  // from 是 zhang
            jsonObject.set("text", text);  // text 同上面的text
            jsonObject.set("time", time);  // text 同上面的text
            this.sendMessage(jsonObject.toString(), toSession);
            log.info("发送给用户username={}，消息：{}", toUsername, jsonObject.toString());
        } else {
            log.info("发送失败，未找到用户username={}的session", toUsername);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
}