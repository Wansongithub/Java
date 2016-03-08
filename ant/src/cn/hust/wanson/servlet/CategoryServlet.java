package cn.hust.wanson.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hust.wanson.domain.Category;
import cn.hust.wanson.service.CategoryService;
import cn.hust.wanson.serviceImpl.CategoryServiceImpl;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
	private CategoryService categoryService = new CategoryServiceImpl();
       
    public CategoryServlet() {
        super();
    }
    
	/**
	 * 查询所有分类
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 通过service得到所有的分类
		 * 2. 保存到request中，转发到left.jsp
		 */
		List<Category> parents = categoryService.findAll();
		req.setAttribute("parents", parents);
		return "f:/jsps/left.jsp";
	}

}
