package com.atguigu.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("*.*.*.dao")
@EnableEurekaClient  
@EnableDiscoveryClient //服务发现
public class DeptProvider8002_App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(DeptProvider8002_App.class, args);
	}

}
