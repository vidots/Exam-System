package com.edu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("article_type")
public class ArticleType implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String typeName;
    private String typeDesc;
    private Integer needVip;
}
