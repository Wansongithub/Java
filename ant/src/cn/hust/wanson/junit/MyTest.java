package cn.hust.wanson.junit;

import java.util.List;

import org.junit.Test;

import cn.hust.wanson.domain.Category;
import cn.hust.wanson.service.CategoryService;
import cn.hust.wanson.serviceImpl.CategoryServiceImpl;

public class MyTest {
	
	@Test
	public void findAll() {
		
		CategoryService categoryService=new CategoryServiceImpl();
		List<Category> parents = categoryService.findAll();
		System.out.println(parents.toString());
	}

}
