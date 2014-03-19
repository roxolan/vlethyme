package in.kmbs.vlethyme.service;

import in.kmbs.vlethyme.dao.GroupDAO;
import in.kmbs.vlethyme.entity.Group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
	
	@Autowired
	GroupDAO groupDAO;
	
	public List<Group> getGroupsByUserId(int userId) {
		return groupDAO.getGroupsByUserId(userId);
	}
	
	public Group getGroupByIdByUserId(int groupId, int userId) {
		return groupDAO.getGroupByIdByUserId(groupId, userId);
	}
	
	public void createGroup(Group group) {
		groupDAO.createGroup(group);
	}
	
	public Group updateGroupMembers(in.kmbs.vlethyme.model.Group group) {
		return groupDAO.updateGroupMembers(group);
	}
}
