package com.wounom.cloudpan.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Litind
 * @version 1.0
 * @data 2023/6/7 22:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code;//状态码
    private String msg;//返回信息
    private Object data;

    public static Result success(){
        return new Result(200,"操作成功",null);
    }
    public static Result success(Object data){
        return new Result(200,"操作成功",data);
    }

    public static Result error(String msg){
        return new Result(500,msg,null);
    }
    public static Result error(Integer code,String msg){
        return new Result(code, msg,null);
    }
    public static Result error(){//敏感信息
        return new Result(500,"系统错误，请联系管理员",null);
    }
}
