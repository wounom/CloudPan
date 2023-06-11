package com.wounom.cloudpan.service.serviceImp;

import com.wounom.cloudpan.common.Result;
import com.wounom.cloudpan.entity.Email;
import com.wounom.cloudpan.entity.User;
import com.wounom.cloudpan.mapper.EmailMapper;
import com.wounom.cloudpan.mapper.UserMapper;
import com.wounom.cloudpan.service.UserService;
import com.wounom.cloudpan.utils.NameUtil;
import com.wounom.cloudpan.utils.PasswordUtil;
import com.wounom.cloudpan.utils.TokenUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Litind
 * @version 1.0
 * @data 2023/6/8 19:04
 */
@Service
public class UserServiceImp implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private EmailMapper emailMapper;

    /**
     * 注册账号
     * @param user
     * @return
     */

    @Override
    public Result<?> regist(User user) {
        //1. 查询User表中是否已经存在该邮箱账号
        User user1 = userMapper.findUser(user.getEmail());
        if (user1 != null){
            return Result.error("账号已存在");
        }
        //查询数据库中的验证码
        Email compare = emailMapper.getCode(user.getEmail());
        if (compare==null){
            return Result.error("账号错误");
        }
        LocalDateTime now = LocalDateTime.now();
        if (!compare.getCode().equals(user.getCode())){
            return Result.error("验证码错误");
        }
        if(now.isAfter(compare.getActiveTime())) {
            return Result.error("验证码超时");
        }
        //验证通过，user表中插入user数据(用户名，email，password，salt)
        //生成salt
        String username = NameUtil.getRandomChineseString();
        String salt = PasswordUtil.createSalt();
        String password = PasswordUtil.md5Pwd(user.getPassword(),salt);//加密用户密码
        user.setSalt(salt);
        user.setPassword(password);
        user.setUserName(username);
        userMapper.insertUser(user);
        //插入成功
        return Result.success("注册成功");
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public Result<?> login(User user) {
        User userCompare = userMapper.findUser(user.getEmail());
        if (userCompare==null){
            return Result.error("账户不存在");
        }
        if(userCompare.getStatus()==1){
            return Result.error("账号封禁中");
        }
        String pwCompare = PasswordUtil.md5Pwd(user.getPassword(),userCompare.getSalt());
        if (!pwCompare.equals(userCompare.getPassword())){
            //密码错误
            return Result.error("账号或密码错误");
        }
        //登录成功
        //创建token
        userCompare.setCode(null);
        userCompare.setPassword(null);
        userCompare.setSalt(null);
        String token = TokenUtil.CreateToken(userCompare);//使用用户信息生成token，同时将用户不敏感信息存入服务器缓存
        Map<String,Object> map = new HashMap<>();
        map.put("user",userCompare);
        map.put("token",token);
        return Result.success(map);
    }
}
