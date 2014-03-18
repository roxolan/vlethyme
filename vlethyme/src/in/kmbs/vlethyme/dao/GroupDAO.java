package in.kmbs.vlethyme.dao;

import in.kmbs.vlethyme.entity.Group;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GroupDAO {
	@Autowired
	private SessionFactory sessionFactory;
	

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Group getGroupByIdByUserId(int groupId, int userId) {
		Criteria criteria = getSession().createCriteria(Group.class);
		criteria.add(Restrictions.eq("id", groupId));
		criteria.createAlias("user", "user");
		criteria.add(Restrictions.eq("user.id", userId));
		@SuppressWarnings("unchecked")
		List<Group> groups = criteria.list();
		
		return CollectionUtils.isNotEmpty(groups) ? groups.get(0) : null;
		
	}
	
	public List<Group> getGroupsByUserId(int userId) {
		Criteria criteria = getSession().createCriteria(Group.class);
		criteria.createAlias("user", "user");
		criteria.add(Restrictions.eq("user.id", userId));
		@SuppressWarnings("unchecked")
		List<Group> groups = criteria.list();
		return groups;
	}
	
	public void createGroup(Group group) {
		getSession().save(group);
	}
}
