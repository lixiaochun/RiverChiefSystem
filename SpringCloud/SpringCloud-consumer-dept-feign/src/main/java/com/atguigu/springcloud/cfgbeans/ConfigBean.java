package com.atguigu.springcloud.cfgbeans;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean //boot  --->spring applicationcontext.xml---@Configutation配置 configBean=applicationContext。xml
{
	@Bean
	@LoadBalanced	    //spring cloud ribbon是基于Netflix Ribbon实现的一套客户端     负载均衡的工具
	public RestTemplate getRestTemplate(){
		
		return new RestTemplate();
	}
	
}

//@Bean
//public UserServcie getUserService(){
//	return new UserServiceImpl();
//}
//applicationContext.xml==ConfigBean(@Configration)
//<bean id="userserivcie" class="com.atguigu.tmall.UserServiceImpl>