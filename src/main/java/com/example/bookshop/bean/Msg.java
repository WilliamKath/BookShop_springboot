package com.example.bookshop.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("历史消息实体类")
public class Msg implements Comparable<Msg>{
    @ApiModelProperty("消息ID")
    private int msgId;
    @ApiModelProperty("发送者")
    private String msgFrom;
    @ApiModelProperty("接受者")
    private String msgTo;
    @ApiModelProperty("消息内容")
    private String msgText;
    @ApiModelProperty("发送时间")
    private String msgDate;   //图方便直接用了String类型存储

    @Override
    public int compareTo(Msg o) {
        return this.msgId - o.msgId;
    }
}
