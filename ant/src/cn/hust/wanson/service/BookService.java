package cn.hust.wanson.service;

import java.sql.SQLException;

import cn.hust.wanson.domain.Book;
import cn.hust.wanson.page.PageBean;

public interface BookService {

	PageBean<Book> findByCategory(String cid, int pc);

	PageBean<Book> findByBname(String bname, int pc);

	PageBean<Book> findByAuthor(String author, int pc);

	PageBean<Book> findByPress(String press, int pc);

	PageBean<Book> findByCombination(Book criteria, int pc);

	Book findByBid(String bid) throws SQLException;

}
