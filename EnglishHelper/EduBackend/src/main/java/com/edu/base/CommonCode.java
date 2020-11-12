package com.edu.base;

public enum CommonCode {
    /**
     * OK
     */
    OK(200, "成功"),
    /**
     * UNAUTHORIZED
     */
    UNAUTHORIZED(401, "用户未登录"),
    /**
     * TRYAGAIN
     */
    TRYAGIN(402, "主人，请稍后再试"),
    /**
     * InnerError
     */
    InnerError(500, "系统内部错误");


    int code;
    String message;

    CommonCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
