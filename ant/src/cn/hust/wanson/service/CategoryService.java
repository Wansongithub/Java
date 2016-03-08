package cn.hust.wanson.service;

import java.util.List;

import cn.hust.wanson.domain.Category;

public interface CategoryService {
	
	List<Category> findAll();

	void add(Category category);

}
