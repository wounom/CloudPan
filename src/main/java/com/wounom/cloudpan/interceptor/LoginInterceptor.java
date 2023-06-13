package com.wounom.cloudpan.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wounom.cloudpan.common.Result;
import com.wounom.cloudpan.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * @Author Litind
 * @Date 2023/6/13 11:59
 * @Version 1.0
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

          /*if (request.getSession().getAttribute("user")!=null){
            return true;
        }
        Result result = new Result(400,"session无效");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(result);
        return false;*/
        if ("OPTIONS".equals(request.getMethod())) {//这里通过判断请求的方法，判断此次是否是预检请求，如果是，立即返回一个204状态吗，标示，允许跨域；预检后，正式请求，这个方法参数就是我们设置的post了
            response.setStatus(HttpStatus.NO_CONTENT.value()); //HttpStatus.SC_NO_CONTENT = 204
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");//当判定为预检请求后，设定允许请求的方法
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, Token"); //当判定为预检请求后，设定允许请求的头部类型
            response.addHeader("Access-Control-Max-Age", "1");
            return true;
        }
        //token验证
        String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)){
            response.setContentType("application/json;charset=UTF-8");
            String error = new ObjectMapper().writeValueAsString(Result.error("token为空"));
            response.getWriter().write(error);
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }
        if (TokenUtil.verfiry(token)){//验证token
            return true;
        }else {
            response.setContentType("application/json;charset=UTF-8");
            String error = new ObjectMapper().writeValueAsString(Result.error("登录超时或无效token"));
            response.getWriter().write(error);
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }
    }
}
