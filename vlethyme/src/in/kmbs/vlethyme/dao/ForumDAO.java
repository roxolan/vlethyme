package in.kmbs.vlethyme.dao;

import in.kmbs.vlethyme.converter.VOToEntityConverter;
import in.kmbs.vlethyme.entity.Forum;
import in.kmbs.vlethyme.entity.ForumMember;
import in.kmbs.vlethyme.entity.Role;
import in.kmbs.vlethyme.enums.ForumMemberRoleEnum;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ForumDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Forum getForumByIdByUserId(int forumId, int userId) {
		Criteria criteria = getSession().createCriteria(Forum.class);
		criteria.add(Restrictions.eq("id", forumId));
		criteria.createAlias("user", "user");
		criteria.add(Restrictions.eq("user.id", userId));
		@SuppressWarnings("unchecked")
		List<Forum> forums = criteria.list();
		
		return CollectionUtils.isNotEmpty(forums) ? forums.get(0) : null;
		
	}
	
	public Forum getForumById(int forumId) {
		Criteria criteria = getSession().createCriteria(Forum.class);
		criteria.add(Restrictions.eq("id", forumId));
		@SuppressWarnings("unchecked")
		List<Forum> forums = criteria.list();
		
		return CollectionUtils.isNotEmpty(forums) ? forums.get(0) : null;
		
	}
	
	public List<Forum> getForumsByUserId(int userId) {
		Query query = getSession().createQuery("SELECT fm.forum from ForumMember AS fm where fm.user.id = :userId or fm.group.id in (SELECT gu.id FROM GroupUser gu WHERE gu.user.id = :userId)");
		query.setParameter("userId", userId);
		@SuppressWarnings("unchecked")
		List<Forum> forums = query.list();
		return forums;
	}
	
	public void createForum(Forum forum) {
		getSession().save(forum);
		System.out.println(forum.getId());
		ForumMember forumMember = new ForumMember();
		forumMember.setForum(forum);
		forumMember.setUser(forum.getUser());
		Role role = new Role();
		role.setId(ForumMemberRoleEnum.ROLE_FORUM_MEMBER_MANAGE.getRoleId());
		forumMember.setRole(role);
		getSession().save(forumMember);
		
	}
	
	public Forum updateForumMembers(in.kmbs.vlethyme.model.Forum forum) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Forum.class);
		criteria.add(Restrictions.eq("id", forum.getId()));
		Forum forumEntity = (Forum) criteria.uniqueResult();
		
		//Find forum members that needs to be deleted
		if (CollectionUtils.isNotEmpty(forumEntity.getForumMembers())) {
			for (ForumMember forumMemberEntity : forumEntity.getForumMembers()) {
				boolean forumMemberFound = false;
				if (CollectionUtils.isNotEmpty(forum.getForumMembers())) {
					for (in.kmbs.vlethyme.model.ForumMember forumMember : forum.getForumMembers()) {
						if (forumMemberEntity.getId().equals(forumMember.getId())) {
							forumMemberFound = true;
							break;
						}
					}
				}
				if (!forumMemberFound) {
					session.delete(forumMemberEntity);
				}
			}
		}
		
		if (CollectionUtils.isNotEmpty(forum.getForumMembers())) {
			for (in.kmbs.vlethyme.model.ForumMember forumMember : forum.getForumMembers()) {
				if (forumMember.getId() != null) { //User is already added. Update
					ForumMember forumMemberEntity = (ForumMember) session.load(ForumMember.class, forumMember.getId());
					Role roleEntity = (Role) session.load(Role.class, forumMember.getRole().getId());
					forumMemberEntity.setRole(roleEntity);
					session.update(forumMemberEntity);
				} else { //User does not exist. Add the user
					ForumMember forumMemberEntity = VOToEntityConverter.convert(forumMember);
					forumMemberEntity.setForum(VOToEntityConverter.convert(forum));
					forumMemberEntity.setRole(VOToEntityConverter.convert(forumMember.getRole()));
					forumMemberEntity.setUser(VOToEntityConverter.convert(forumMember.getUser()));
					session.save(forumMemberEntity);
				}
			}
		}
		return forumEntity;
	}
}
