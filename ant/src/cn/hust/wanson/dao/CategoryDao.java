package cn.hust.wanson.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.hust.wanson.domain.Category;

public interface CategoryDao {

	
	Category toCategory(Map<String,Object> map);

	List<Category> toCategoryList(List<Map<String, Object>> mapList);

	List<Category> findAll() throws SQLException;

	List<Category> findByParent(String pid) throws SQLException;

	void add(Category category) throws SQLException;
}
