package com.hx.webim.util;

import com.hx.webim.model.TokenModel;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static TokenModel getTokenByRequest(HttpServletRequest request){
        String token= request.getHeader("token");
        TokenModel tokenModel=TokenUtil.stringToModel(token);
        return tokenModel;
    }
}
