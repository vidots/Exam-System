package com.edu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("p_question")
public class Question implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer paperId;
    private String content;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
    private String answer;
    private Integer category;
    private String analysis;
}
