package com.atguigu.springcloud.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atguigu.springcloud.entities.Dept;

import feign.hystrix.FallbackFactory;
@Component //不要忘记添加，不要忘记添加
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {

	@Override
	public DeptClientService create(Throwable throwable) {
		// TODO Auto-generated method stub
		return new DeptClientService() {
			
			@Override
			public Dept findById(long id) {
				// TODO Auto-generated method stub
				return new Dept().setDeptno(id).setDname("该ID:"+id+"没有对应的信息，Consumer客户端"
						+ "提供得降级信息,此刻服务Provider已经关闭"
						).setDb_source("no this database in MySQL");
			}
			
			@Override
			public List<Dept> findAll() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean addDept(Dept dept) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

}
