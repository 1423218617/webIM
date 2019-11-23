package com.hx.webim.util;

import com.hx.webim.model.TokenModel;
import com.hx.webim.model.pojo.User;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


import java.io.IOException;


public class TokenUtil {
    private TokenUtil(){}

    private final static Logger log=LoggerFactory.getLogger(TokenModel.class);

    public static String create(User user){
        TokenModel tokenModel=new TokenModel();
        tokenModel.setUid(user.getId());
        tokenModel.setToken(UUIDUtil.getUUID());
        BASE64Encoder encoder=new BASE64Encoder();
        String tokenString=encoder.encode(JsonUtils.objToString(tokenModel).getBytes());
        return tokenString;
    }

    public static final TokenModel stringToModel(String tokenString){
        BASE64Decoder decoder=new BASE64Decoder();
        TokenModel tokenModel=new TokenModel();
        try{
            String s=new String(decoder.decodeBuffer(tokenString));
            tokenModel=JsonUtils.stringToObj(s,TokenModel.class);
        }catch(IOException e){
            e.printStackTrace();
        }

        return tokenModel;
    }
    public static Integer getIdByToken(TokenModel tokenModel){
        return tokenModel.getUid();
    }
}
