package in.kmbs.vlethyme.converter;

import in.kmbs.vlethyme.model.Forum;
import in.kmbs.vlethyme.model.ForumMember;
import in.kmbs.vlethyme.model.ForumPost;
import in.kmbs.vlethyme.model.Group;
import in.kmbs.vlethyme.model.GroupUser;
import in.kmbs.vlethyme.model.Role;
import in.kmbs.vlethyme.model.User;

public class EntityToVOConverter {

	public static Group convert(in.kmbs.vlethyme.entity.Group group) {
		Group groupModel = null;
		if (group != null) {
			groupModel = new Group();
			groupModel.setId(group.getId());
			groupModel.setName(group.getName());
		}

		return groupModel;
	}
	
	public static Forum convert(in.kmbs.vlethyme.entity.Forum forum) {
		Forum forumModel = null;
		if (forum != null) {
			forumModel = new Forum();
			forumModel.setId(forum.getId());
			forumModel.setTitle(forum.getTitle());
			forumModel.setContent(forum.getContent());
		}
		return forumModel;
	}
	
	public static ForumPost convert(in.kmbs.vlethyme.entity.ForumPost forumPost) {
		ForumPost forumPostModel = null;
		if (forumPost != null) {
			forumPostModel = new ForumPost();
			forumPostModel.setId(forumPost.getId());
			forumPostModel.setContent(forumPost.getContent());
		}
		return forumPostModel;
	}
	
	public static ForumMember convert(in.kmbs.vlethyme.entity.ForumMember forumMember) {
		ForumMember forumMemberModel = null;
		if (forumMember != null) {
			forumMemberModel = new ForumMember();
			forumMemberModel.setId(forumMember.getId());
		}
		return forumMemberModel;
	}

	public static User convert(in.kmbs.vlethyme.entity.User user) {
		User userModel = null;
		if (user != null) {
			userModel = new User();
			userModel.setId(user.getId());
			userModel.setUsername(user.getUsername());
			userModel.setFirstName(user.getFirstName());
			userModel.setLastName(user.getLastName());
			userModel.setPassword(user.getPassword());
			userModel.setEmail(user.getEmail());
		}
		return userModel;
	}

	public static Role convert(in.kmbs.vlethyme.entity.Role role) {
		Role roleModel = null;
		if (role != null) {
			roleModel = new Role();
			roleModel.setId(role.getId());
			roleModel.setName(role.getName());
			roleModel.setType(role.getType());
		}
		return roleModel;
	}

	public static GroupUser convert(in.kmbs.vlethyme.entity.GroupUser groupUser) {
		GroupUser groupUserModel = null;
		if (groupUser != null) {
			groupUserModel = new GroupUser();
			groupUserModel.setId(groupUser.getId());
		}
		return groupUserModel;
	}
}
