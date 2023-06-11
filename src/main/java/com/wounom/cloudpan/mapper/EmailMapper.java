package com.wounom.cloudpan.mapper;

import com.wounom.cloudpan.entity.Email;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Litind
 * @version 1.0
 * @data 2023/6/8 14:11
 */
@Mapper
@Repository //将mapper交由spring容器管理
public interface EmailMapper {

    /**
     * 查询数据库中该email已存在的验证码
     */
    Email findCode(String email);

    void insertCode(Email emailnew);

    void updateCode(Email emailnew);

    Email getCode(String email);
}
