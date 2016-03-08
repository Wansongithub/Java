package cn.hust.wanson.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import cn.hust.wanson.dao.CategoryDao;
import cn.hust.wanson.dao.impl.CategoryDaoImpl;
import cn.hust.wanson.domain.Category;
import cn.hust.wanson.service.CategoryService;


public class CategoryServiceImpl implements CategoryService {
	
	private CategoryDao categoryDao = new CategoryDaoImpl();
	
	/**
	 * 查找所有的分类
	 */
	@Override
	public List<Category> findAll() {
		try {
			return categoryDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加分类
	 * @param category
	 */
	@Override
	public void add(Category category) {
		try {
			categoryDao.add(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
