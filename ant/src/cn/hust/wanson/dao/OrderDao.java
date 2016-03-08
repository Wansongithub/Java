package cn.hust.wanson.dao;

import java.sql.SQLException;

import cn.hust.wanson.domain.Order;
import cn.hust.wanson.page.PageBean;

public interface OrderDao {

	PageBean<Order> findByUser(String uid, int pc) throws SQLException;

	void add(Order order) throws SQLException;

	Order load(String oid) throws SQLException;

	int findStatus(String oid) throws SQLException;

	void updateStatus(String oid, int status) throws SQLException;

}
