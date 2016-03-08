package cn.hust.wanson.dao;

import java.sql.SQLException;

import cn.hust.wanson.domain.User;

public interface UserDao {
	
	public boolean ajaxValidateLoginname(String loginname) throws SQLException;
	public boolean ajaxValidateEmail(String email) throws SQLException;
	public void add(User user) throws SQLException;
	public User findByCode(String code) throws SQLException;
	public void updateStatus(String uid, boolean b)throws Exception;
	public User findByLoginnameAndLoginpass(String loginname, String loginpass) throws SQLException;
	User findLoginExsit(String loginname);
	boolean findByUidAndPassword(String uid, String password)
			throws SQLException;
	void updatePassword(String uid, String password) throws SQLException;
}
