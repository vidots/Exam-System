package com.edu.service;

import com.edu.base.RestResponse;

public interface WechatService {
    RestResponse wxLogin(String code);
    void modifyName(String name, Integer userId);
}
