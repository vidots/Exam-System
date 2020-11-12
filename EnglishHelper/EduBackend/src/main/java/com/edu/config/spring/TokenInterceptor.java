package com.edu.config.spring;

import com.edu.base.CommonCode;
import com.edu.base.WxContext;
import com.edu.model.Auth;
import com.edu.util.RedisUtil;
import com.edu.util.RestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Value("${token.expirationMilliSeconds}")
    private long expirationMilliSeconds;

    private final RedisUtil redisUtil;
    private final WxContext wxContext;

    public TokenInterceptor(RedisUtil redisUtil, WxContext wxContext) {
        this.redisUtil = redisUtil;
        this.wxContext = wxContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        boolean token_wrong = StringUtils.isEmpty(token) || StringUtils.isBlank(token) || token.length() != 36;
        if (token_wrong) {
            RestUtil.response(response, CommonCode.UNAUTHORIZED);
            return false;
        }
        HashMap<String, Object> authMap = (HashMap<String, Object>) redisUtil.hget(token);
        if (null == authMap || authMap.keySet().size() == 0) {
            RestUtil.response(response, CommonCode.UNAUTHORIZED);
            return false;
        }
        Auth auth = new Auth();
        String open_id = authMap.get("open_id").toString();
        auth.setOpenId(authMap.get("open_id").toString());
        auth.setUserId(new Integer(authMap.get("user_id").toString()));
        auth.setRole(authMap.get("user_role").toString());
        logger.info("open_id = " + open_id + " url = " + request.getRequestURI());
        wxContext.setAuthInContext(auth);
        wxContext.setTokenInContext(token);
        redisUtil.setKeyExpire(token, expirationMilliSeconds);
        return true;
    }
}





















