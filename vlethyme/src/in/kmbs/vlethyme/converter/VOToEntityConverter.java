package in.kmbs.vlethyme.converter;

import in.kmbs.vlethyme.entity.Forum;
import in.kmbs.vlethyme.entity.ForumMember;
import in.kmbs.vlethyme.entity.ForumPost;
import in.kmbs.vlethyme.entity.Group;
import in.kmbs.vlethyme.entity.GroupUser;
import in.kmbs.vlethyme.entity.Role;
import in.kmbs.vlethyme.entity.User;

public class VOToEntityConverter {

	public static Group convert(in.kmbs.vlethyme.model.Group group) {
		Group groupEntity = null;
		if (group != null) {
			groupEntity = new Group();
			groupEntity.setId(group.getId());
			groupEntity.setName(group.getName());
		}
		return groupEntity;
	}
	
	public static Forum convert(in.kmbs.vlethyme.model.Forum forum) {
		Forum forumEntity = null;
		if (forum != null) {
			forumEntity = new Forum();
			forumEntity.setId(forum.getId());
			forumEntity.setTitle(forum.getTitle());
			forumEntity.setContent(forum.getContent());
		}
		return forumEntity;
	}
	
	public static ForumPost convert(in.kmbs.vlethyme.model.ForumPost forumPost) {
		ForumPost forumPostEntity = null;
		if (forumPost != null) {
			forumPostEntity = new ForumPost();
			forumPostEntity.setId(forumPost.getId());
			forumPostEntity.setContent(forumPost.getContent());
		}
		return forumPostEntity;
	}
	
	public static ForumMember convert(in.kmbs.vlethyme.model.ForumMember forumMember) {
		ForumMember forumMemberEntity = null;
		if (forumMember != null) {
			forumMemberEntity = new ForumMember();
			forumMemberEntity.setId(forumMember.getId());
		}
		return forumMemberEntity;
	}


	public static User convert(in.kmbs.vlethyme.model.User user) {
		User userEntity = null;
		if (user != null) {
			userEntity = new User();
			userEntity.setId(user.getId());
			userEntity.setUsername(user.getUsername());
			userEntity.setFirstName(user.getFirstName());
			userEntity.setLastName(user.getLastName());
			userEntity.setPassword(user.getPassword());
			userEntity.setEmail(user.getEmail());
		}
		return userEntity;
	}

	public static GroupUser convert(in.kmbs.vlethyme.model.GroupUser groupUser) {
		GroupUser groupUserEntity = null;
		if (groupUser != null) {
			groupUserEntity = new GroupUser();
			groupUserEntity.setId(groupUser.getId());
		}
		return groupUserEntity;
	}

	public static Role convert(in.kmbs.vlethyme.model.Role role) {
		Role roleEntity = null;
		if (role != null) {
			roleEntity = new Role();
			roleEntity.setId(role.getId());
			roleEntity.setName(role.getName());
			roleEntity.setType(role.getType());
		}
		return roleEntity;
	}
}
