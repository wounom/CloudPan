package com.wounom.cloudpan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description  
 * @Author  zhf
 * @Date 2023-06-08 
 */

@ApiModel(value = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User  implements Serializable {


	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Long uid;

	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称")
	private String userName;

	/**
	 * 用户email（用于登录）
	 */
	@ApiModelProperty(value = "用户email（用于登录）")
	private String email;

	/**
	 * 用户头像
	 */
	@ApiModelProperty(value = "用户头像")
	private String useImg;

	/**
	 * 用户账号状态（0为正常，1为封禁）
	 */
	@ApiModelProperty(value = "用户账号状态（0为正常，1为封禁）")
	private Integer status;

	/**
	 * 加密盐
	 */
	@ApiModelProperty(value = "加密盐")
	private String salt;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 验证码
	 */
	@ApiModelProperty(value = "验证码")
	private String code;
}
