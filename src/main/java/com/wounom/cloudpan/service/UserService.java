package com.wounom.cloudpan.service;

import com.wounom.cloudpan.common.Result;
import com.wounom.cloudpan.entity.User;

/**
 * @author Litind
 * @version 1.0
 * @data 2023/6/8 19:03
 */
public interface UserService {
    Result<?> regist(User user);

    Result<?> login(User user);
}
