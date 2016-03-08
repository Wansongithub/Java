package cn.hust.wanson.dao;

import java.sql.SQLException;
import java.util.List;

import cn.hust.wanson.domain.CartItem;

public interface CartItemDao {

	List<CartItem> findByUser(String uid) throws SQLException;

	CartItem findByUidAndBid(String uid, String bid) throws SQLException;

	void updateQuantity(String cartItemId, int quantity) throws SQLException;

	void add(CartItem cartItem) throws SQLException;

	void batchDelete(String cartItemIds) throws SQLException;

	CartItem findCartItemById(String cartItemId) throws SQLException;

	List<CartItem> loadCartItem(String cartItemIds) throws SQLException;

}
