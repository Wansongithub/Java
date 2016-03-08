package cn.hust.wanson.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.hust.wanson.dao.CartItemDao;
import cn.hust.wanson.domain.Book;
import cn.hust.wanson.domain.CartItem;
import cn.hust.wanson.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class CartItemDaoImpl implements CartItemDao {
	
	private QueryRunner qr = new TxQueryRunner();
	
	
	public CartItem toCartItem(Map<String, Object>map){
		
		if (map==null||map.size()==0) {
			return null;
		}
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		User user = CommonUtils.toBean(map, User.class);
		cartItem.setBook(book);
		cartItem.setUser(user);
		return cartItem;
	}
	
	
	public List<CartItem> toCartItemList(List<Map<String, Object>> maplist) {
		List<CartItem> cList = new ArrayList<CartItem>();
		for (Map<String, Object> map : maplist) {
			CartItem cartItem = toCartItem(map);
			cList.add(cartItem);
		}
		return cList;
			
	}
	
	@Override
	public List<CartItem> findByUser(String uid) throws SQLException{
		String sql = "select * from t_cartitem c, t_book b where c.bid=b.bid and uid=? order by c.orderBy";
		List<Map<String, Object>> maplist = qr.query(sql, new MapListHandler(), uid);
		return toCartItemList(maplist);
	}
	
	/**
	 * 通过bid和uid查询某个用户的某本图书订单是否存在
	 * @param uid
	 * @param bid
	 * @return
	 * @throws SQLException
	 */
	@Override
	public CartItem findByUidAndBid(String uid,String bid) throws SQLException{
		String sql ="select * from t_cartitem where uid=? and bid=?";
		Map<String, Object> map = qr.query(sql, new MapHandler(),uid,bid);
		CartItem cartItem = toCartItem(map);
		return cartItem;
	}
	
	
	/**
	 * 更新购物车item的数量
	 * @param cartItemId
	 * @param quantity
	 * @throws SQLException
	 */
	@Override
	public void updateQuantity(String cartItemId,int quantity) throws SQLException {
		String sql = "update t_cartitem set quantity=? where cartItemId=? ";
			qr.update(sql,quantity,cartItemId);		
	}
	
	/**
	 * 添加购物车条目
	 * @param cartItem
	 * @throws SQLException
	 */
	@Override
	public void add(CartItem cartItem) throws SQLException {
		String sql ="insert into t_cartitem(cartItemId,quantity,bid,uid) "
				+ "values(?,?,?,?)";
		qr.update(sql,cartItem.getCartItemId(),cartItem.getQuantity(),
				cartItem.getBook().getBid(),cartItem.getUser().getUid());
	}
	
	/**
	 * 生产where子句
	 * @param len
	 * @return
	 */
	public String toWhere(int len) {
		StringBuilder sBuilder=new StringBuilder();
		sBuilder.append("cartItemId in(");
		for (int i = 0; i < len; i++) {
			sBuilder.append("?");
			if (i<len-1) {
				sBuilder.append(",");
			}
		}
		sBuilder.append(")");
		return sBuilder.toString();
		
	}
	
	/**
	 * 批量删除
	 * @param cartItemIds
	 * @throws SQLException
	 */
	@Override
	public void batchDelete(String cartItemIds) throws SQLException {
		
		Object[] split = cartItemIds.split(",");
		String sql = "delete from t_cartitem where "+toWhere(split.length);
		qr.update(sql, split);
	}
	
	/**
	 * 查询购物车条目
	 * @param cartItemId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public CartItem findCartItemById(String cartItemId) throws SQLException {
		String sql = "select * from t_cartItem c, t_book b where c.bid=b.bid and c.cartItemId=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(), cartItemId);
		return toCartItem(map);
	}
	
	/**
	 * 加载勾选支付的购物车条目
	 * @param cartItemIds
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<CartItem> loadCartItem(String cartItemIds) throws SQLException{
		Object[] cartItemArray = cartItemIds.split(",");
		String sql ="select * from t_cartitem c,t_book b where c.bid=b.bid and "+toWhere(cartItemArray.length);
		List<Map<String, Object>> cartItemList = qr.query(sql, new MapListHandler(),cartItemArray);
		return toCartItemList(cartItemList);
	} 

}
