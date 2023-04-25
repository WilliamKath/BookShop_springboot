package com.example.bookshop.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("聊天会话实体类")
public class ChatLink {
    @ApiModelProperty("会话ID")
    private int linkId;
    @ApiModelProperty("会话ID")
    private String fromUser;
    @ApiModelProperty("会话ID")
    private String toUser;
}
