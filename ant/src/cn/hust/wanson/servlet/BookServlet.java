package cn.hust.wanson.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hust.wanson.domain.Book;
import cn.hust.wanson.page.PageBean;
import cn.hust.wanson.service.BookService;
import cn.hust.wanson.serviceImpl.BookServiceImpl;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

@WebServlet("/BookServlet")
public class BookServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
	private BookService bookService=new BookServiceImpl();
      
	 public BookServlet() {
	        super();
	       
	    }
	
      /**
       * 获取当前页码
       * @param req
       * @return
       */
      private int getPc(HttpServletRequest request) {
      	int pc = 1;
      	String param = request.getParameter("pc");
      	if(param != null && !param.trim().isEmpty()) {
      		try {
      			pc = Integer.parseInt(param);
      		} catch(RuntimeException e) {}
      	}
      	return pc;
      }
      
      /**
       * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
       * @param req
       * @return
       */
      /*
       * http://localhost:8080/ant/BookServlet?methed=findByCategory&cid=xxx&pc=3
       * /ant/BookServlet + methed=findByCategory&cid=xxx&pc=3
       */
      private String getUrl(HttpServletRequest request) {
      	String url = request.getRequestURI() + "?" + request.getQueryString();
      	/*
      	 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
      	 */
      	int index = url.lastIndexOf("&pc=");
      	if(index != -1) {
      		url = url.substring(0, index);
      	}
      	return url;
      }

      /**
       * 按分类查
       * @param req
       * @param resp
       * @return
       * @throws ServletException
       * @throws IOException
       */
      public String findByCategory(HttpServletRequest req, HttpServletResponse resp) {
      	/*
      	 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
      	 */
      	int pc = getPc(req);
      	/*
      	 * 2. 得到url：...
      	 */
      	String url = getUrl(req);
      	/*
      	 * 3. 获取查询条件，本方法就是cid，即分类的id
      	 */
      	String cid = req.getParameter("cid");
      	/*
      	 * 4. 使用pc和cid调用service#findByCategory得到PageBean
      	 */
      	PageBean<Book> pb = bookService.findByCategory(cid, pc);
      	/*
      	 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
      	 */
      	pb.setUrl(url);
      	req.setAttribute("pb", pb);
      	return "f:/jsps/book/list.jsp";
      }

      /**
       * 按出版社查询
       * @param req
       * @param resp
       * @return
       */
      public String findByPress(HttpServletRequest req, HttpServletResponse resp) {
        	/*
        	 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
        	 */
        	int pc = getPc(req);
        	/*
        	 * 2. 得到url：...
        	 */
        	String url = getUrl(req);
        	/*
        	 * 3. 获取查询条件，本方法就是cid，即分类的id
        	 */
        	String press = req.getParameter("press");
        	/*
        	 * 4. 使用pc和cid调用service#findByCategory得到PageBean
        	 */
        	PageBean<Book> pb = bookService.findByPress(press, pc);
        	/*
        	 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
        	 */
        	pb.setUrl(url);
        	req.setAttribute("pb", pb);
        	return "f:/jsps/book/list.jsp";
        }
      
      /**
       * 按作者查询
       * @param req
       * @param resp
       * @return
       */
      public String findByAuthor(HttpServletRequest req, HttpServletResponse resp) {
        	/*
        	 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
        	 */
        	int pc = getPc(req);
        	/*
        	 * 2. 得到url：...
        	 */
        	String url = getUrl(req);
        	/*
        	 * 3. 获取查询条件，本方法就是cid，即分类的id
        	 */
        	String author = req.getParameter("author");
        	/*
        	 * 4. 使用pc和cid调用service#findByCategory得到PageBean
        	 */
        	PageBean<Book> pb = bookService.findByAuthor(author, pc);
        	/*
        	 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
        	 */
        	pb.setUrl(url);
        	req.setAttribute("pb", pb);
        	return "f:/jsps/book/list.jsp";
        }
      
      /**
       * 按书名查询
       * @param req
       * @param resp
       * @return
       */
      public String findByBname(HttpServletRequest req, HttpServletResponse resp) {
      	/*
      	 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
      	 */
      	int pc = getPc(req);
      	/*
      	 * 2. 得到url：...
      	 */
      	String url = getUrl(req);
      	/*
      	 * 3. 获取查询条件，本方法就是cid，即分类的id
      	 */
      	String bname = req.getParameter("bname");
      	/*
      	 * 4. 使用pc和cid调用service#findByCategory得到PageBean
      	 */
      	PageBean<Book> pb = bookService.findByBname(bname, pc);
      	/*
      	 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
      	 */
      	pb.setUrl(url);
      	req.setAttribute("pb", pb);
      	return "f:/jsps/book/list.jsp";
      }
      
      /**
       * 按组合条件查询
       * @param req
       * @param resp
       * @return
       */
      public String findByCombination(HttpServletRequest req, HttpServletResponse resp) {
        	/*
        	 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
        	 */
        	int pc = getPc(req);
        	/*
        	 * 2. 得到url：...
        	 */
        	String url = getUrl(req);
        	/*
        	 * 3. 获取查询条件，本方法就是cid，即分类的id
        	 */
        	Book certiaria =CommonUtils.toBean(req.getParameterMap(), Book.class);
        	/*
        	 * 4. 使用pc和cid调用service#findByCategory得到PageBean
        	 */
        	PageBean<Book> pb = bookService.findByCombination(certiaria, pc);
        	/*
        	 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
        	 */
        	pb.setUrl(url);
        	req.setAttribute("pb", pb);
        	return "f:/jsps/book/list.jsp";
        }
      
      /**
       * 按bid加载图书
       * @param req
       * @param resp
       * @return
     * @throws SQLException 
       */
      public String load(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
    	  
    	  String bid = req.getParameter("bid");
    	  Book book = bookService.findByBid(bid);
    	  req.setAttribute("book", book);
    	  return "f:jsps/book/desc.jsp";
		
	}
}

