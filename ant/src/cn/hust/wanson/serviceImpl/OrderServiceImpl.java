package cn.hust.wanson.serviceImpl;

import java.sql.SQLException;

import cn.hust.wanson.dao.OrderDao;
import cn.hust.wanson.dao.impl.OrderDaoImpl;
import cn.hust.wanson.domain.Order;
import cn.hust.wanson.page.PageBean;
import cn.hust.wanson.service.OrderService;
import cn.itcast.jdbc.JdbcUtils;

public class OrderServiceImpl implements OrderService {
	
	private OrderDao orderDao = new OrderDaoImpl();
	
	/**
	 * 我的订单
	 * @param uid
	 * @param pc
	 * @return
	 */
	@Override
	public PageBean<Order> myOrders(String uid, int pc) {
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findByUser(uid, pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}

	@Override
	public void createOrder(Order order) {
		try {
			JdbcUtils.beginTransaction();
			orderDao.add(order);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * 加载订单项
	 * @param oid
	 * @return
	 */
	@Override
	public Order load(String oid) {
		
		try {
			JdbcUtils.beginTransaction();
			Order order = orderDao.load(oid);
			JdbcUtils.commitTransaction();
			return order;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
		
	}
	

	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 */
	@Override
	public void updateStatus(String oid, int status) {
		try {
			orderDao.updateStatus(oid, status);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 */
	@Override
	public int findStatus(String oid) {
		try {
			return orderDao.findStatus(oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
