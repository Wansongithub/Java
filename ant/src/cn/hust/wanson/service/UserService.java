package cn.hust.wanson.service;

import java.sql.SQLException;

import cn.hust.wanson.domain.User;
import cn.hust.wanson.exception.UserException;

public interface UserService {

	public boolean ajaxValidateLoginname(String loginname) throws SQLException;
	public boolean ajaxValidateEmail(String email) throws SQLException;
	public void regist(User user) throws SQLException;
	public void activatioin(String code)throws Exception;
	public void updatePassword(String uid, String newpass, String loginpass) throws UserException;
	public User login(User user);
	public boolean findUserIsExsit(String loginname) throws SQLException;
}
