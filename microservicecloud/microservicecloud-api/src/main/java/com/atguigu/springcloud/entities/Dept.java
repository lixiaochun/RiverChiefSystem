package com.atguigu.springcloud.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@SuppressWarnings("serial") //警告处理
@AllArgsConstructor	//全参构造函数	

@NoArgsConstructor	//无参构造函数

@Data				//get setter方法	

@Accessors(chain=true)  

public class Dept implements Serializable {
	private Long deptno;		//主键
	private String dname;		//	部门名称
	private String db_source;//来自那个数据集，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同的数据库
	
	public Dept(String dname){
		super();
		this.dname=dname;
	}
	
}
