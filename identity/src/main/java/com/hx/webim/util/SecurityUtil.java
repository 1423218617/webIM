package com.hx.webim.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtil {

    private SecurityUtil() {
    }

    private static final BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


    /**
     *
     * @description 密码加密
     * @param password
     * @return
     */
    public static String encrypt(String password){
        return passwordEncoder.encode(password);
    }
    /**
     *
     * @description  判断密码是否正确  password1是明文密码，password2为加密过的密码
     * @param password1
     * @param password2
     * @return
     */
    public static boolean matches(String password1,String password2){
        return passwordEncoder.matches(password1,password2);
    }

}
