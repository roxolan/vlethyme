package in.kmbs.vlethyme.dao;

import in.kmbs.vlethyme.converter.VOToEntityConverter;
import in.kmbs.vlethyme.entity.Group;
import in.kmbs.vlethyme.entity.GroupUser;
import in.kmbs.vlethyme.entity.Role;
import in.kmbs.vlethyme.enums.GroupMemberRoleEnum;

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
public class GroupDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
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
	
	public Group getGroupById(int groupId) {
		Criteria criteria = getSession().createCriteria(Group.class);
		criteria.add(Restrictions.eq("id", groupId));
		@SuppressWarnings("unchecked")
		List<Group> groups = criteria.list();
		
		return CollectionUtils.isNotEmpty(groups) ? groups.get(0) : null;
		
	}
	
	public List<Group> getGroupsByUserId(int userId) {
		Query query = getSession().createQuery("SELECT gu.group from GroupUser AS gu where gu.user.id = ?");
		query.setParameter(0, userId);
		@SuppressWarnings("unchecked")
		List<Group> groups = query.list();
		return groups;
	}
	
	public void createGroup(Group group) {
		getSession().save(group);
		System.out.println(group.getId());
		GroupUser groupUser = new GroupUser();
		groupUser.setGroup(group);
		groupUser.setUser(group.getUser());
		Role role = new Role();
		role.setId(GroupMemberRoleEnum.ROLE_GROUP_MEMBER_MANAGER.getRoleId());
		groupUser.setRole(role);
		getSession().save(groupUser);
		
	}
	
	public Group updateGroupMembers(in.kmbs.vlethyme.model.Group group) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Group.class);
		criteria.add(Restrictions.eq("id", group.getId()));
		Group groupEntity = (Group) criteria.uniqueResult();
		
		//Find group members that needs to be deleted
		if (CollectionUtils.isNotEmpty(groupEntity.getGroupUsers())) {
			for (GroupUser groupUserEntity : groupEntity.getGroupUsers()) {
				boolean groupUserFound = false;
				if (CollectionUtils.isNotEmpty(group.getGroupUsers())) {
					for (in.kmbs.vlethyme.model.GroupUser groupUser : group.getGroupUsers()) {
						if (groupUserEntity.getId().equals(groupUser.getId())) {
							groupUserFound = true;
							break;
						}
					}
				}
				if (!groupUserFound) {
					session.delete(groupUserEntity);
				}
			}
		}
		
		if (CollectionUtils.isNotEmpty(group.getGroupUsers())) {
			for (in.kmbs.vlethyme.model.GroupUser groupUser : group.getGroupUsers()) {
				if (groupUser.getId() != null) { //User is already added. Update
					GroupUser groupUserEntity = (GroupUser) session.load(GroupUser.class, groupUser.getId());
					Role roleEntity = (Role) session.load(Role.class, groupUser.getRole().getId());
					groupUserEntity.setRole(roleEntity);
					session.update(groupUserEntity);
				} else { //User does not exist. Add the user
					GroupUser groupUserEntity = VOToEntityConverter.convert(groupUser);
					groupUserEntity.setGroup(VOToEntityConverter.convert(group));
					groupUserEntity.setRole(VOToEntityConverter.convert(groupUser.getRole()));
					groupUserEntity.setUser(VOToEntityConverter.convert(groupUser.getUser()));
					session.save(groupUserEntity);
				}
			}
		}
		return groupEntity;
	}
}
