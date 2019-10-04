package com.hx.webim.service.impl;

import com.hx.webim.mapper.UserMapper;
import com.hx.webim.model.pojo.User;
import com.hx.webim.service.UserService;
import com.hx.webim.util.DateUtil;
import com.hx.webim.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public boolean saveUser(User user) {
        if (user==null|| StringUtils.isBlank(user.getUsername())
                ||StringUtils.isBlank(user.getPassword())
                ||StringUtils.isBlank(user.getEmail())){
            return false;
        }
        user.setCreate_date(DateUtil.getDate());
        user.setPassword(SecurityUtil.encrypt(user.getPassword()));
        userMapper.insertUser(user);
        return true;
    }
}
