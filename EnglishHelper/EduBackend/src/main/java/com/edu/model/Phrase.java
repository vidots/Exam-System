package com.edu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("phrase")
@Data
public class Phrase implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String phrase;
    private String phraseTranslation;
    private String sentence;
    private String sentenceTranslation;
}
