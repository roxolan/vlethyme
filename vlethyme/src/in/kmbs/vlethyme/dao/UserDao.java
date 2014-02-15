package in.kmbs.vlethyme.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("userDao")
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Transactional
	public void create(User user) {
		session().save(user);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return this.sessionFactory.openSession().createQuery("from User").list();
	}
	
/*
 Search for Hibernate issue solution (no session found for current thread):
 http://stackoverflow.com/questions/9717906/org-hibernate-hibernateexception-get-is-not-valid-without-active-transaction
 --> ultimately used openSession() instead of getCurrentSession()
 ------- other links
 http://stackoverflow.com/questions/4699381/best-way-to-inject-hibernate-session-by-spring-3
 http://stackoverflow.com/questions/20716939/spring-hibernate-no-session-found-for-current-thread
 .... 
 *
 */

	@Transactional
	public User getFirstUser() {
		return (User)session().createQuery("from User as user");
	}
}
