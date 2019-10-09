package com.hx.webim.service;

import com.hx.webim.Exception.UserException;
import com.hx.webim.model.pojo.User;

public interface UserService {
    boolean register(User user);
    boolean active(User user);
    boolean existEmail(String email) ;
}
