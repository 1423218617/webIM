package com.hx.webim.mapper;


import com.hx.webim.model.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    void insertUser(User user);
}
