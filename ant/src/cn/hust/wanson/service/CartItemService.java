package cn.hust.wanson.service;

import java.sql.SQLException;
import java.util.List;

import cn.hust.wanson.domain.CartItem;

public interface CartItemService {

	List<CartItem> findByUser(String uid);

	void add(CartItem cartItem) throws SQLException;

	void batchDelete(String cartItemIds) throws SQLException;

	CartItem updateQuantity(String cartItemId, int quantity)
			throws SQLException;

	List<CartItem> loadCartItem(String cartItemIds);

}
