package com.edu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("book_chapter_paragraph")
public class Paragraph implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String content;
    private String parse;
    private Integer chapterId;
}
