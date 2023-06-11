package com.wounom.cloudpan.utils;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wounom.cloudpan.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import com.auth0.jwt.JWT;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author litind
 * @version 1.0
 * @date 2023/6/11 12:18
 */
@Slf4j
public class TokenUtil {
    private static Map<String, User> tokenMap = new HashMap<>();//用于存储用户信息
    private static final Long EXPIRE_TIME = 7*24*60*60*1000L;//过期时间为7天
    private static final String TOKEN_SECRET =
            "litind_$13wounom2_finally_creat@%testfor(12)";//加密密钥

    /**
     * 生成token
     */
    //生成 Token
    public static String CreateToken(User user){
        String token = null;
        try {
            Date expire = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("litind")  //发行人
                    .withClaim("userId",user.getUid()) //存放数据
                    .withExpiresAt(expire) //过期时间
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));//加密方式
        }catch (Exception e){
            e.printStackTrace();
        }
        saveUser(token,user);
        return token;
    }

    //缓存已经登录的账户
    static void saveUser(String token,User user){
        Collection<User> collect = tokenMap.values();
        if (collect.contains(user)==true){
            //当用户重新登录的时候，先将缓存中的token去掉，再存入新的token
            collect.remove(user);
        }
        tokenMap.put(token,user);
    }


    // TOKEN 验证
    public static Boolean verfiry(String token){
        if(getUser(token)==null){
            return false;
        }
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET))
                    .withIssuer("litind")
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            log.info("TOKEN 验证通过");
            log.info("userId:"+ decodedJWT.getClaim("userId"));
            log.info("过期时间："+ decodedJWT.getExpiresAt());
            //User user = JSON.parseObject(String.valueOf(decodedJWT.getClaim("user")), User.class);
            //System.out.println(user.getEmail());
        }catch (Exception e){
            // 抛出错误即为验证不通过
            log.error("TOKEN 验证不通过");
            return false;
        }
        return true;
    }

    // 通过token获取用户
    /**
     *
     * 推荐使用该方法获取用户
     * 可与前端登录、登出形成两轮验证
     * @param token
     * @return com.wounom.kaoyaniep.entity.User
     * @author litind
     **/
    public static User getUser(String token){
        User user = tokenMap.get(token);
        return user;
    }

    //登出时使用该方法删除服务器缓存中的token
    //防止已登出的用户使用登出前的token恶意操作
    public static boolean removeToken(String token) {
        try {
            tokenMap.remove(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }


    /**
     *
     * 修改用户信息后更新tokenmap
     * @param user,token
     * @return
     * @author litind
     **/
    public static void updateTokenmap(User user, HttpServletRequest request){
        String token = request.getHeader("token");
        tokenMap.put(token,user);
    }




}
