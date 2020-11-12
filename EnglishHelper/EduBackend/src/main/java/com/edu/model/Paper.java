package com.edu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("p_paper")
public class Paper implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer seriesId;
    private String name;
}
