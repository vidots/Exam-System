package com.edu.controller.wx;

import com.edu.base.RestResponse;
import com.edu.base.Role;
import com.edu.base.WxContext;
import com.edu.service.VIPCodeService;
import com.edu.service.WechatService;
import com.edu.util.RedisUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/wx")
public class WXAuthController {

    private final WechatService wechatService;
    private final VIPCodeService vipCodeService;
    private final WxContext wxContext;
    private final RedisUtil redisUtil;

    public WXAuthController(WechatService wechatService, VIPCodeService vipCodeService, WxContext wxContext, RedisUtil redisUtil) {
        this.wechatService = wechatService;
        this.vipCodeService = vipCodeService;
        this.wxContext = wxContext;
        this.redisUtil = redisUtil;
    }

    @PostMapping("/login")
    public RestResponse wxLogin(String code) {
        return wechatService.wxLogin(code);
    }

    @PostMapping("/become-vip")
    public RestResponse becomeVIP(String vipCode) {
        vipCodeService.useVIPCode(vipCode, wxContext.getCurrentAuth().getUserId());
        String token = wxContext.getUserToken();
        HashMap<String, Object> authMap = (HashMap<String, Object>) redisUtil.hget(token);
        authMap.put("user_role", Role.VIP.getRole());
        redisUtil.hset(token, authMap);
        return RestResponse.ok();
    }

    @PostMapping("/modify-name")
    public RestResponse modifyName(String name) {
        wechatService.modifyName(name, wxContext.getCurrentAuth().getUserId());
        return RestResponse.ok();
    }

    @PostMapping("/user-role")
    public RestResponse userRole() {
        return RestResponse.ok(vipCodeService.userRole(wxContext.getCurrentAuth().getUserId()));
    }



}
