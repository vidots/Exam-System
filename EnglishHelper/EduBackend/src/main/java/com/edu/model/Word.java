package com.edu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("word")
@Data
public class Word implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String word;
    private String symbol;
    private String translationOne;
    private String translationTwo;
}
