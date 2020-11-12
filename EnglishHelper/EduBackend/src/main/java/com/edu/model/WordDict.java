package com.edu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("word_dict")
@Data
public class WordDict {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String word;
    private String detail;
}
