package com.edu.base;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public RestResponse defaultHandler(HttpServletRequest request, BusinessException e) throws Exception {
        return RestResponse.fail(CommonCode.TRYAGIN.getCode(), e.getMessage());
    }
}












