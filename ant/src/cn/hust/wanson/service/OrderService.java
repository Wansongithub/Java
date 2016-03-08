package cn.hust.wanson.service;

import cn.hust.wanson.domain.Order;
import cn.hust.wanson.page.PageBean;

public interface OrderService {

	PageBean<Order> myOrders(String uid, int pc);

	void createOrder(Order order);

	Order load(String oid);

	void updateStatus(String oid, int status);

	int findStatus(String oid);

}
