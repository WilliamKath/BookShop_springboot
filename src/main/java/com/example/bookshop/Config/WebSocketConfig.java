package com.example.bookshop.Config;

import com.example.bookshop.component.LiveChatSocketServer;
import com.example.bookshop.component.MyChatSocketServer;
import com.example.bookshop.component.WebSocketServer;
import com.example.bookshop.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    /**
     * 注入一个ServerEndpointExporter,该Bean会自动注册使用@ServerEndpoint注解申明的websocket endpoint
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    public void setMessageService(MsgService msgService){
        WebSocketServer.msgService = msgService;
    }

    @Autowired
    public void setMyChatService(MsgService msgService){
        MyChatSocketServer.msgService = msgService;
    }

    @Autowired
    public void setLiveChatService(MsgService msgService){
        LiveChatSocketServer.msgService = msgService;
    }

}
