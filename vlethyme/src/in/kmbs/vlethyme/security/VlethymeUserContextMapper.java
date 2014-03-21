package in.kmbs.vlethyme.security;

import in.kmbs.vlethyme.converter.EntityToVOConverter;
import in.kmbs.vlethyme.model.User;
import in.kmbs.vlethyme.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.transaction.annotation.Transactional;

public class VlethymeUserContextMapper implements UserDetailsContextMapper {

	@Autowired
	UserService userService;

	@Transactional
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {

		// Declare a null Spring UserLogin
		in.kmbs.vlethyme.security.User userDetails = null;
		in.kmbs.vlethyme.entity.User userEntity = userService.findUserByUsername(username);
		User loginUser = EntityToVOConverter.convert(userEntity);
		loginUser.setRole(EntityToVOConverter.convert(userEntity.getRole()));
		userDetails = new in.kmbs.vlethyme.security.User(loginUser, true, true, true, true, getAuthorities(loginUser));
		return userDetails;
	}

	private Collection<GrantedAuthority> getAuthorities(User user) {
		// Create a list of grants for this user
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		if (user.getRole() != null) {
			authList.add(new SimpleGrantedAuthority(user.getRole().getName()));
		} else {
			authList.add(new SimpleGrantedAuthority("guest"));
		}
		return authList;
	}

	@Override
	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
	}

}