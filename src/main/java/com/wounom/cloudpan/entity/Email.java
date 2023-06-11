package com.wounom.cloudpan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description  
 * @Author  zhf
 * @Date 2023-06-07 
 */

@ApiModel(value = "email")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email  implements Serializable {


	/**
	 * 验证码id
	 */
	@ApiModelProperty(value = "验证码id")
	private Long id;

	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	private String email;

	/**
	 * 验证码
	 */
	@ApiModelProperty(value = "验证码")
	private String code;

	/**
	 * 验证码过期时间
	 */
	@ApiModelProperty(value = "验证码过期时间")
	private LocalDateTime activeTime;

}
