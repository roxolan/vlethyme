package in.kmbs.vlethyme.service;

import java.util.List;

import in.kmbs.vlethyme.dao.User;
import in.kmbs.vlethyme.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void create(User user) {
		userDao.create(user);
	}
	
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
	
}
	