package com.atguigu.springcloud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;


@RestController
public class DeptController {

	@Autowired
	private DeptService service;
	

	//一旦调用服务方法失败并抛出了错误信息后，会自动调用@HystrixCommand标注好的fallbackMethod调用类中的指定方法。
	@RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
	@HystrixCommand(fallbackMethod="processHystrix_Get")
	public Dept get(@PathVariable("id") Long id){
		Dept dept=this.service.findById(id);
		if(null==dept){
			throw new RuntimeException("该ID:"+id+"没有对应的信息");
		}
		
		return dept;
	}
	public Dept processHystrix_Get(@PathVariable("id") Long id){
		return new Dept().setDeptno(id)
				.setDname("该ID:"+id+"没有对应的信息，null--@HystrixCommand")
				.setDb_source("no this database in MySQL");
	}
	@RequestMapping(value="/dept/list",method=RequestMethod.GET)
	public List<Dept> list(HttpServletRequest request,HttpServletResponse response){
		return service.findAll();
	}
	@RequestMapping(value="/dept/add",method=RequestMethod.POST)
	public boolean add(@RequestBody Dept dept,HttpServletRequest request,HttpServletResponse response){
		return service.addDept(dept);
	}
	
}
