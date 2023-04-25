package com.example.bookshop.vo;

import com.example.bookshop.bean.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 图书详情实体类
 */
@Data
@ApiModel("图书详情视图")
public class BookDetailVo extends Book {
    @ApiModelProperty("图书ID 主键 自增")
    private int bookId;
    @ApiModelProperty("书名")
    private String bookName;
    @ApiModelProperty("ISBN号")
    private String bookIsbn;
    @ApiModelProperty("种类 1-课本教材 2-文艺作品 3-文学小说 4-政治生活 5-生活教育 6-经济金融")
    private int bookCategory;
    @ApiModelProperty("价钱")
    private String bookPrice;
    @ApiModelProperty("简介")
    private String bookOutline;
    @ApiModelProperty("审核状态 0-待审核 1-审核通过 2-审核不通过")
    private int bookRelease;
    @ApiModelProperty("种类 1-课本教材 2-文艺作品 3-文学小说 4-政治生活 5-生活教育 6-经济金融")
    private Category category;
    @ApiModelProperty("商品图片列表")
    private List<BookImg> bookImgList;
    @ApiModelProperty("卖家")
    private Seller seller;
}
