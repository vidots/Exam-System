package com.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.base.BusinessException;
import com.edu.base.CommonCode;
import com.edu.base.RestResponse;
import com.edu.base.Role;
import com.edu.config.property.SystemConfig;
import com.edu.mapper.AuthMapper;
import com.edu.model.Auth;
import com.edu.service.WechatService;
import com.edu.util.RedisUtil;
import com.edu.util.WxUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class WechatServiceImpl implements WechatService {

    private final SystemConfig systemConfig;
    private final AuthMapper authMapper;
    private final RedisUtil redisUtil;

    public WechatServiceImpl(SystemConfig systemConfig, AuthMapper authMapper, RedisUtil redisUtil) {
        this.systemConfig = systemConfig;
        this.authMapper = authMapper;
        this.redisUtil = redisUtil;
    }

    @Override
    public RestResponse wxLogin(String code) {
        String openid = WxUtil.getOpenId(systemConfig.getWx().getAppid(), systemConfig.getWx().getSecret(), code);
        if (null == openid) {
            return RestResponse.fail(CommonCode.TRYAGIN.getCode(), CommonCode.TRYAGIN.getMessage());
        }
        QueryWrapper<Auth> query = new QueryWrapper<>();
        query.eq("open_id", openid);

        Auth auth = authMapper.selectOne(query);
        if (auth == null) {
            auth = new Auth();
            auth.setOpenId(openid);
            auth.setRole(Role.USER.getRole());
            authMapper.insert(auth);
        }
        String token = UUID.randomUUID().toString();
        HashMap<String, Object> authMap = new HashMap<>();
        authMap.put("open_id", auth.getOpenId());
        authMap.put("user_id", auth.getUserId());
        authMap.put("user_role", auth.getRole());
        redisUtil.hset(token, authMap);
        Map<String, String> userMap = new HashMap<>();
        userMap.put("token", token);
        userMap.put("role", auth.getRole());
        return RestResponse.ok(userMap);
    }

    @Override
    public void modifyName(String name, Integer userId) {
        Auth auth = authMapper.selectById(userId);
        if (auth == null) {
            throw new BusinessException("用户不存在");
        }
        auth.setNickName(name);
        authMapper.updateById(auth);
    }
}
















