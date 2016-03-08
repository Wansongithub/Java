package cn.hust.wanson.serviceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import cn.hust.wanson.dao.UserDao;
import cn.hust.wanson.dao.impl.UserDaoImpl;
import cn.hust.wanson.domain.User;
import cn.hust.wanson.exception.UserException;
import cn.hust.wanson.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

public class UserServiceImpl implements UserService {
	
	public UserDao  userDao = new UserDaoImpl();

	@Override
	public boolean ajaxValidateLoginname(String loginname) throws SQLException {
		try {
			return userDao.ajaxValidateLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean ajaxValidateEmail(String email) throws SQLException {
		try {
			return userDao.ajaxValidateEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void regist(User user) throws SQLException {
		
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
		
		try {
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
		
		// 登录邮件服务器，得到session
		String host = prop.getProperty("host");//服务器主机名
		String name = prop.getProperty("username");//登录名
		String pass = prop.getProperty("password");//登录密码
		Session session = MailUtils.createSession(host, name, pass);
		
		// 创建Mail对象
		String from = prop.getProperty("from");
		String to = user.getEmail();
		String subject = prop.getProperty("subject");
		
		// MessageForm.format方法会把第一个参数中的{0},使用第二个参数来替换。
		String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
		Mail mail = new Mail(from, to, subject, content);
		
		//发送邮件
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally{
		System.out.println("邮件已经发送");
		}
		
	}

	@Override
	public void activatioin(String code) throws SQLException{
		/*
		 * 1. 通过激活码查询用户
		 * 2. 如果User为null，说明是无效激活码，抛出异常，给出异常信息（无效激活码）
		 * 3. 查看用户状态是否为true，如果为true，抛出异常，给出异常信息（请不要二次激活）
		 * 4. 修改用户状态为true
		 
		try {
			User user=null;
			try {
				user = userDao.findByCode(code);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(user == null) throw new cn.hust.wanson.exception.UserException("无效的激活码！");
			if(user.isStatus()) throw new cn.hust.wanson.exception.UserException("您已经激活过了，不要二次激活！");
			try {
				userDao.updateStatus(user.getUid(), true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//修改状态
		} catch(Exception e) {
			throw new RuntimeException(e);
		}*/
		
		User user = null;
			user = userDao.findByCode(code);
		if (user==null){
			try {
				throw new UserException("无效的激活码！");
			} catch (UserException e1) {
	            throw new RuntimeException(e1);
			}
		}
		if (user.isStatus()) {
			try {
				throw new UserException("您已经激活过了，不要二次激活！");
			} catch (UserException e) {
				throw new RuntimeException(e);
			}
			
		}
		
		try {
			userDao.updateStatus(user.getUid(), true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public boolean findUserIsExsit(String loginname) throws SQLException {
		User user = userDao.findLoginExsit(loginname);
		if (user==null) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	/**
	 * 登录功能
	 * @param user
	 * @return
	 */
	@Override
	public User login(User user) {
		try {
			return userDao.findByLoginnameAndLoginpass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改密码
	 * @param uid
	 * @param newPass
	 * @param oldPass
	 * @throws UserException 
	 */
	@Override
	public void updatePassword(String uid, String newPass, String oldPass) throws UserException {

		try {
			/*
			 * 1. 校验老密码
			 */
			boolean bool = userDao.findByUidAndPassword(uid, oldPass);
			if(!bool) {//如果老密码错误
				throw new UserException("老密码错误！");
			}
			
			/*
			 * 2. 修改密码
			 */
			userDao.updatePassword(uid, newPass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
