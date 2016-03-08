package cn.hust.wanson.serviceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.hust.wanson.dao.CartItemDao;
import cn.hust.wanson.dao.impl.CartItemDaoImpl;
import cn.hust.wanson.domain.CartItem;
import cn.hust.wanson.service.CartItemService;

public class CartItemServiceImpl implements CartItemService {
	
	private CartItemDao cartItemDao = new CartItemDaoImpl();

	/**
	 * 通过uid查找用户的购物车条目
	 */
	@Override
	public List<CartItem> findByUser(String uid){
		List<CartItem> list = new ArrayList<CartItem>();
		try {
			list = cartItemDao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	
	
	/**
	 * 添加购物车条目
	 * @param cartItem
	 * @throws SQLException
	 */
	@Override
	public void add(CartItem cartItem) throws SQLException {
		
		CartItem _cartItem = cartItemDao.findByUidAndBid(cartItem.getUser().getUid()
				,cartItem.getBook().getBid());
		if (_cartItem==null) {
			cartItemDao.add(cartItem);
		}
		else {			
			int quantity=cartItem.getQuantity()+_cartItem.getQuantity();
			cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
		}
	}
	
	/**
	 * 批量删除购物车条目
	 * @param cartItemIds
	 * @throws SQLException
	 */
	@Override
	public void batchDelete(String cartItemIds) throws SQLException {
		cartItemDao.batchDelete(cartItemIds);
		
	}
	
	/**
	 * 更新购物车条目数量并返回
	 * @param cartItemId
	 * @param quantity
	 * @return
	 * @throws SQLException
	 */
	@Override
	public CartItem updateQuantity(String cartItemId,int quantity) throws SQLException {
		
		cartItemDao.updateQuantity(cartItemId, quantity);
		CartItem cartItem = cartItemDao.findCartItemById(cartItemId);
		return cartItem;
		
	}
	
	/**
	 *  加载勾选支付的购物车条目
	 * @param cartItemIds
	 * @return
	 */
	@Override
	public List<CartItem> loadCartItem(String cartItemIds) {
		try {
			return cartItemDao.loadCartItem(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
