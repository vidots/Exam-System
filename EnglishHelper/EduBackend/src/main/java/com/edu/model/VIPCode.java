package com.edu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("vip_code")
public class VIPCode {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String code;
    private Integer used;
    private Date createTime;
}













