package in.kmbs.vlethyme.service;

import in.kmbs.vlethyme.dao.ForumDAO;
import in.kmbs.vlethyme.entity.Forum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumService {
	
	@Autowired
	ForumDAO forumDAO;
	
	public List<Forum> getForumsByUserId(int userId) {
		return forumDAO.getForumsByUserId(userId);
	}
	
	public Forum getForumById(int forumId) {
		return forumDAO.getForumById(forumId);
	}
	
	public Forum getForumByIdByUserId(int forumId, int userId) {
		return forumDAO.getForumByIdByUserId(forumId, userId);
	}
	
	public void createForum(Forum forum) {
		forumDAO.createForum(forum);
	}
	
	public Forum updateForumMembers(in.kmbs.vlethyme.model.Forum forum) {
		return forumDAO.updateForumMembers(forum);
	}
}
