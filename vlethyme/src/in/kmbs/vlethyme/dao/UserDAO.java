package in.kmbs.vlethyme.dao;

import in.kmbs.vlethyme.entity.User;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<User> findUsersByNameLike(String name, int limit) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.disjunction().add(Restrictions.like("firstName", "%"+name+"%")).add(Restrictions.like("lastName", "%"+name+"%")));
		if (limit > 0) {
			criteria.setMaxResults(limit);
		}
		@SuppressWarnings("unchecked")
		List<User> users = criteria.list();
		return users;
	}
	
	public User findUserByUsername(String username) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		User user = (User) criteria.uniqueResult();
		return user;
	}
	
	public User findUserById(Integer userId) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("id", userId));
		User user = (User) criteria.uniqueResult();
		return user;
	}
	
	public List<User> getUsers() {
		Criteria criteria = getSession().createCriteria(User.class);
		@SuppressWarnings("unchecked")
		List<User> users = criteria.list();
		return users;
	}
	
	public void createUser(User user) {
		getSession().save(user);
	}
 }
