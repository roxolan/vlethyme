package in.kmbs.vlethyme.service;

import in.kmbs.vlethyme.dao.UserDAO;
import in.kmbs.vlethyme.entity.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	public List<User> findUsersByNameLike(String name, int limit) {
		return userDAO.findUsersByNameLike(name, limit);
	}
}
	