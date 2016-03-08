package cn.hust.wanson.serviceImpl;

import java.sql.SQLException;

import cn.hust.wanson.dao.BookDao;
import cn.hust.wanson.dao.impl.BookDaoImpl;
import cn.hust.wanson.domain.Book;
import cn.hust.wanson.page.PageBean;
import cn.hust.wanson.service.BookService;

public class BookServiceImpl implements BookService {
	
	private BookDao bookDao=new BookDaoImpl();
	
	/**
	 * 按分类查
	 * @param cid
	 * @param pc
	 * @return
	 */
	@Override
	public PageBean<Book> findByCategory(String cid, int pc) {
		try {
			return bookDao.findByCategory(cid, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按书名查
	 * @param bname
	 * @param pc
	 * @return
	 */
	@Override
	public PageBean<Book> findByBname(String bname, int pc) {
		try {
			return bookDao.findByBname(bname, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按作者查
	 * @param author
	 * @param pc
	 * @return
	 */
	@Override
	public PageBean<Book> findByAuthor(String author, int pc) {
		try {
			return bookDao.findByAuthor(author, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按出版社查
	 * @param press
	 * @param pc
	 * @return
	 */
	@Override
	public PageBean<Book> findByPress(String press, int pc) {
		try {
			return bookDao.findByPress(press, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 多条件组合查询
	 * @param criteria
	 * @param pc
	 * @return
	 */
	@Override
	public PageBean<Book> findByCombination(Book criteria, int pc) {
		try {
			return bookDao.findByCombination(criteria, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按bid查询图书
	 * @param bid
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public Book findByBid(String bid) throws SQLException {
		
		Book book = bookDao.findByBid(bid);
		return book;
	}

}
