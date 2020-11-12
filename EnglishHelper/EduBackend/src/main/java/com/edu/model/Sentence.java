package com.edu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("sentence")
@Data
public class Sentence implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String sentence;
    private String source;
    private Integer category;
    private String translation;
}
