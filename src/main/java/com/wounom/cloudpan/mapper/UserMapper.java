package com.wounom.cloudpan.mapper;

import com.wounom.cloudpan.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Litind
 * @version 1.0
 * @data 2023/6/8 19:05
 */
@Mapper
public interface UserMapper {
    void insertUser(User user);
    User findUser(String email);
}
