package com.wounom.cloudpan.controller;

import com.wounom.cloudpan.common.Result;
import com.wounom.cloudpan.entity.User;
import com.wounom.cloudpan.service.UserService;
import com.wounom.cloudpan.utils.TokenUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Litind
 * @version 1.0
 * @data 2023/6/8 18:59
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    /**
     * 用户注册
     * 参数: email，password，code
     */
    @PostMapping("/regist")
    public Result<?> Regist(@RequestBody User user){
        return userService.regist(user);
    }

    /**
     * 用户登录
     * 参数: email，password
     */
    @PostMapping("/login")
    public Result<?> Login(@RequestBody User user){
        return userService.login(user);
    }

    /**
     * 用户登出
     * 参数：无
     */
    @PostMapping("/logout")
    public Result<?> Logout(HttpServletRequest request){
        String token = request.getHeader("token");
        if (TokenUtil.removeToken(token)){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * 忘记密码
     * 参数：email，code，password
     */
    @PostMapping("/resetpw")
    public Result<?> ResetPw(@RequestBody User user){
        return userService.resetPw(user);
    }
}
