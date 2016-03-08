package cn.hust.wanson.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hust.wanson.domain.Book;
import cn.hust.wanson.domain.CartItem;
import cn.hust.wanson.domain.User;
import cn.hust.wanson.service.CartItemService;
import cn.hust.wanson.serviceImpl.CartItemServiceImpl;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class CartItemServlet
 */
@WebServlet("/CartItemServlet")
public class CartItemServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
	private CartItemService cartItemService=new CartItemServiceImpl();
       
    public CartItemServlet() {
        super();
    }
    
  /**
   * 加载购物车
   * @param req
   * @param resp
   * @return
   */
   public String myCart(HttpServletRequest req, HttpServletResponse resp) {
    	
	   User user = (User) req.getSession().getAttribute("sessionUser");
	   List<CartItem> cartItemList = cartItemService.findByUser(user.getUid());
	   req.setAttribute("cartItemList", cartItemList);
	   return "f:/jsps/cart/list.jsp";
    }
   
   /**
    * 添加购物车条目
    * @param req
    * @param resp
    * @return
    * @throws SQLException 
    */
   public String add(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
	   
	 Map<String, String[]> map = req.getParameterMap();
 	 CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
	 Book book = CommonUtils.toBean(map, Book.class);
	 User user = (User) req.getSession().getAttribute("sessionUser");
	 cartItem.setBook(book);
	 cartItem.setUser(user);
	 
	 cartItem.setCartItemId(CommonUtils.uuid());
	 
	 cartItemService.add(cartItem);
	 
	return myCart(req, resp);
	 
}
    
   /**
    * 批量删除购物车条目
    * @param req
    * @param resp
    * @return
    */
   public String batchDelete(HttpServletRequest req, HttpServletResponse resp) {
	   
	   String cartItemIds = req.getParameter("cartItemIds");
	   try {
		cartItemService.batchDelete(cartItemIds);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
	   return myCart(req, resp);
	
}
   
   public void updateQuantity(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
	   
	   String cartItemId = req.getParameter("cartItemId");
	   int quantity =Integer.parseInt(req.getParameter("quantity"));
	   
	   CartItem cartItem = cartItemService.updateQuantity(cartItemId, quantity);
	   
	   StringBuilder sb =new StringBuilder();
	   sb.append("{");
	   sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
	   sb.append(",");
	   sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
	   sb.append("}");
	   
	   resp.getOutputStream().print(sb.toString());
	   System.out.println(sb.toString());
	   }
   
   
   /**
    * 加载勾选支付的购物车条目
    * @param req
    * @param resp
    * @return
    */
     public String loadCartItem(HttpServletRequest req, HttpServletResponse resp){
    	 
	   String cartItemIds = req.getParameter("cartItemIds");
	   Double total = Double.parseDouble(req.getParameter("total"));
	   List<CartItem> cartItemlist = cartItemService.loadCartItem(cartItemIds);
	   req.setAttribute("cartItemList", cartItemlist);
	   req.setAttribute("total", total);
	   req.setAttribute("cartItemIds", cartItemIds);
	   return "f:/jsps/cart/showitem.jsp";
    }
    
}
