package com.example.bookshop.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("返回实体类")
public class ResMsg {
    //提示代码，成功时默认为0
    @ApiModelProperty("返回代码 0-成功(默认)")
    private String errcode = "0";
    //提示信息
    @ApiModelProperty("提示信息")
    private String errmsg;
    //返回结果集
    @ApiModelProperty("返回结果集")
    private Object result;
}
