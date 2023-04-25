package com.example.bookshop.vo;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.Category;
import com.example.bookshop.bean.Favorites;
import com.example.bookshop.bean.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("收藏列表展示视图")
public class FavoritesListVo extends Favorites {
    @ApiModelProperty("收藏列表ID")
    private int favoritesId;
    @ApiModelProperty("收藏者ID")
    private int userId;
    @ApiModelProperty("收藏的图书ID")
    private int bookId;
    @ApiModelProperty("通过bookId获取收藏的book信息")
    private Book book;
    //以下为废弃的属性，两项属性都可以直接在BookDtlVo中获取
//    @ApiModelProperty("通过获取收藏的book信息来获取对应的图书分类名")
//    private Category category;
//    @ApiModelProperty("通过获取收藏的book信息来获取对应的seller信息")
//    private User seller;
}
