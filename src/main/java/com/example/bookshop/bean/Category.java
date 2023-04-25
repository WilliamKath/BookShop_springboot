package com.example.bookshop.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("图书分类实体类")
public class Category implements Serializable {
    @ApiModelProperty("种类ID")
    private int categoryId;
    @ApiModelProperty("种类名 1-课本教材 2-文艺作品 3-文学小说 4-政治生活 5-生活教育 6-经济金融")
    private String categoryName;

}
