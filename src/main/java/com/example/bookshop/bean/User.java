package com.example.bookshop.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户实体类
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel("用户实体类")
public class User {
    @ApiModelProperty("用户ID")
    private int userId;
    @ApiModelProperty("用户账户名")
    private String userName;
    @ApiModelProperty("用户密码")
    private String userPassword;
    @ApiModelProperty("用户邮箱")
    private String userEmail;
    @ApiModelProperty("用户手机号")
    private String userPhone;
    @ApiModelProperty("用户权限 1-管理员 2-普通用户")
    private int userRole;
    @ApiModelProperty("地址")
    private String userAddress;
    @ApiModelProperty("钱包--可提现金额")
    private String userWallet;

}
