package in.kmbs.vlethyme.converter;

import in.kmbs.vlethyme.model.Group;
import in.kmbs.vlethyme.model.GroupUser;
import in.kmbs.vlethyme.model.Role;
import in.kmbs.vlethyme.model.User;

public class EntityToVOConverter {
	
	public static Group convert(in.kmbs.vlethyme.entity.Group group) {
		Group groupModel = new Group();
		groupModel.setId(group.getId());
		groupModel.setName(group.getName());

		return groupModel;
	}
	
	public static User convert(in.kmbs.vlethyme.entity.User user) {
		User userModel = new User();
		userModel.setId(user.getId());
		userModel.setUsername(user.getUsername());
		userModel.setFirstName(user.getFirstName());
		userModel.setLastName(user.getLastName());
		userModel.setPassword(user.getPassword());
		userModel.setEmail(user.getEmail());

		return userModel;
	}
	
	public static Role convert(in.kmbs.vlethyme.entity.Role role) {
		Role roleModel = new Role();
		roleModel.setId(role.getId());
		roleModel.setName(role.getName());
		roleModel.setType(role.getType());
		return roleModel;
	}
	
	public static GroupUser convert(in.kmbs.vlethyme.entity.GroupUser groupUser) {
		GroupUser groupUserModel = new GroupUser();
		groupUserModel.setId(groupUser.getId());

		return groupUserModel;
	}
}
