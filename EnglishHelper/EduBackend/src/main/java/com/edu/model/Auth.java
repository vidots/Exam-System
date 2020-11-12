package com.edu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user")
public class Auth implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer userId;
    private String openId;
    private String role;
    private String nickName;
}
