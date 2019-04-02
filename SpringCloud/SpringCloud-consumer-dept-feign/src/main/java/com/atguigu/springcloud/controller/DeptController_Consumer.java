package com.atguigu.springcloud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptClientService;

@RestController
public class DeptController_Consumer {
	
	@Autowired
	private DeptClientService service;
	@RequestMapping(value="/dept/add",method=RequestMethod.POST)
	public boolean add(@RequestBody Dept dept,HttpServletRequest request,HttpServletResponse response){
		return service.addDept(dept);
	}
	@RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
	public Dept get(@PathVariable("id") Long id,HttpServletRequest request,HttpServletResponse response){
		return service.findById(id);
	}
	@RequestMapping(value="/dept/list",method=RequestMethod.GET)
	public List<Dept> list(HttpServletRequest request,HttpServletResponse response){
		return service.findAll();
	}
	
}
