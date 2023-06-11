package com.wounom.cloudpan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wounom.cloudpan.mapper")
public class CloudPanApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudPanApplication.class,args);
	}

}
