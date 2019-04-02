package com.atguigu.springcloud.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atguigu.springcloud.entities.Dept;
//@FeignClient(value="microservicecloud-dept")
@FeignClient(value="microservicecloud-dept",fallbackFactory=DeptClientServiceFallbackFactory.class)
public interface DeptClientService {
	@RequestMapping(value="/dept/add",method=RequestMethod.POST)
	public boolean addDept(Dept dept);
	@RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
	public Dept findById(long id);
	@RequestMapping(value="/dept/list",method=RequestMethod.GET)
	public List<Dept> findAll();
}
