package cn.hust.wanson.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.hust.wanson.dao.CategoryDao;
import cn.hust.wanson.domain.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDaoImpl implements CategoryDao {
	private QueryRunner qr = new TxQueryRunner();
	
 
	@Override
	public List<Category> toCategoryList(List<Map<String,Object>> mapList){
		
		List<Category> categoryList = new ArrayList<Category>();//创建一个空集合
		for(Map<String,Object> map : mapList) {//循环遍历每个Map
			Category c = toCategory(map);//把一个Map转换成一个Category
			categoryList.add(c);//添加到集合中
		}
		return categoryList;//返回集合
	}
	
	
	@Override
	public List<Category> findAll() throws SQLException{
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String, Object>> maplist = qr.query(sql,new MapListHandler());
		
		List<Category> parents = toCategoryList(maplist);
		
	  for (Category parent : parents) {
		List<Category> childList = findByParent(parent.getCid());
		parent.setChildren(childList);	
	       }
	  return parents;
		
	}
	
	@Override
	public List<Category> findByParent(String pid) throws SQLException{
		String sql = "select * from t_category where pid=? order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), pid);
		return toCategoryList(mapList);
	}

	@Override
	public Category toCategory(Map<String, Object> map) {
		
		Category category=CommonUtils.toBean(map, Category.class);
		String pid = (String)map.get("pid");// 如果是一级分类，那么pid是null
		if(pid != null) {//如果父分类ID不为空，
			/*
			 * 使用一个父分类对象来拦截pid
			 * 再把父分类设置给category
			 */
			Category parent = new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
	}
	
	/**
	 * 添加分类
	 * @param category
	 * @throws SQLException 
	 */
	@Override
	public void add(Category category) throws SQLException {
		String sql = "insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";
		/*
		 * 因为一级分类，没有parent，而二级分类有！
		 * 我们这个方法，要兼容两次分类，所以需要判断
		 */
		String pid = null;//一级分类
		if(category.getParent() != null) {
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCid(), category.getCname(), pid, category.getDesc()};
		qr.update(sql, params);
	}

}
