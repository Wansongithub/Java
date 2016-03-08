package cn.hust.wanson.dao;

import java.sql.SQLException;
import java.util.List;

import cn.hust.wanson.domain.Book;
import cn.hust.wanson.page.Expression;
import cn.hust.wanson.page.PageBean;

public interface BookDao {
	PageBean<Book> findByCriteria(List<Expression> exprList, int pc) throws SQLException;

	PageBean<Book> findByCategory(String cid, int pc) throws SQLException;

	PageBean<Book> findByBname(String bname, int pc) throws SQLException;

	PageBean<Book> findByAuthor(String author, int pc) throws SQLException;

	PageBean<Book> findByPress(String press, int pc) throws SQLException;

	PageBean<Book> findByCombination(Book criteria, int pc) throws SQLException;

	Book findByBid(String bid) throws SQLException;

}
