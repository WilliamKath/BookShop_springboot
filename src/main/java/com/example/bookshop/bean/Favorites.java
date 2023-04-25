package com.example.bookshop.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("收藏列表实体类")
public class Favorites {
    @ApiModelProperty("收藏列表ID")
    private int favoritesId;
    @ApiModelProperty("对应收藏者的用户ID")
    private int userId;
    @ApiModelProperty("收藏的图书ID")
    private int bookId;
}
