package in.kmbs.vlethyme.converter;

import in.kmbs.vlethyme.entity.Group;
import in.kmbs.vlethyme.entity.GroupUser;
import in.kmbs.vlethyme.entity.Role;
import in.kmbs.vlethyme.entity.User;

public class VOToEntityConverter {
	
	public static Group convert(in.kmbs.vlethyme.model.Group group) {
		Group groupEntity = new Group();
		groupEntity.setId(group.getId());
		groupEntity.setName(group.getName());

		return groupEntity;
	}
	
	public static User convert(in.kmbs.vlethyme.model.User user) {
		User userEntity = new User();
		userEntity.setId(user.getId());
		userEntity.setUsername(user.getUsername());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setPassword(user.getPassword());
		userEntity.setEmail(user.getEmail());

		return userEntity;
	}
	
	public static GroupUser convert(in.kmbs.vlethyme.model.GroupUser groupUser) {
		GroupUser groupUserEntity = new GroupUser();
		groupUserEntity.setId(groupUser.getId());
		
		return groupUserEntity;
	}
	
	public static Role convert(in.kmbs.vlethyme.model.Role role) {
		Role roleEntity = new Role();
		roleEntity.setId(role.getId());
		roleEntity.setName(role.getName());
		roleEntity.setType(role.getType());
		return roleEntity;
	}
}
