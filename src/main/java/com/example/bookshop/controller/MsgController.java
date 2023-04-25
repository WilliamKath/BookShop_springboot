package com.example.bookshop.controller;

import com.example.bookshop.bean.ChatLink;
import com.example.bookshop.service.MsgService;
import com.example.bookshop.vo.ResMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"消息相关接口"})
@RestController
@CrossOrigin("http://localhost:8080")
public class MsgController {
    @Autowired
    private MsgService msgService;

    @ApiOperation(value = "查找当前用户的会话列表")
    @GetMapping("linklist")
    public ResMsg getLinkList(@RequestParam(value = "user") String user){
        ResMsg resMsg = new ResMsg();
        List<String> chatLinkList = msgService.getChatListService(user);
        if (chatLinkList != null || chatLinkList.size() != 0){
            resMsg.setResult(chatLinkList);
            System.out.println("查找到当前会话列表："+chatLinkList);
            resMsg.setErrcode("0");
            resMsg.setErrmsg("查找到会话列表！");
        } else {
            resMsg.setErrcode("1");
            resMsg.setErrmsg("未查找到会话列表！");
        }
        return resMsg;
    }
    
    @ApiOperation(value = "删除聊天记录及会话列表")
    @DeleteMapping("/delLinkAndHxMsg")
    public ResMsg delMsgAndLink(@RequestParam(value = "msgFrom")String msgFrom,@RequestParam(value = "msgTo")String msgTo){
        ResMsg resMsg = new ResMsg();
        System.out.println("获得请求的msgFrom="+msgFrom+"，获得请求的msgTo="+msgTo);
        int flag = msgService.deleteMsgAndLinkService(msgFrom,msgTo);
        if ( flag != 0){
            if ( flag == 1 ){
                resMsg.setErrcode("0");
                resMsg.setErrmsg("成功删除会话列表及聊天记录！");
            } else {
                resMsg.setErrcode("2");
                resMsg.setErrmsg("会话列表删除成功！聊天记录删除失败！");
            }
        } else {
            resMsg.setErrcode("1");
            resMsg.setErrmsg("删除失败！会话列表或聊天记录为空！");
        }
        return resMsg;
    }
}
