package in.kmbs.vlethyme.security;

import in.kmbs.vlethyme.converter.EntityToVOConverter;
import in.kmbs.vlethyme.model.User;
import in.kmbs.vlethyme.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class VlethymeUserService implements UserDetailsService {

	@Autowired
	UserService userService;

	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

		// Declare a null Spring UserLogin
		in.kmbs.vlethyme.security.User userDetails = null;

		try {
			// Search database for a user that matches the specified username
			// get the user view object
			User ldapUser = userService.getLdapUser(username);
			User loginUser = EntityToVOConverter.convert(userService.findUserByUsername(username));
			loginUser.setPassword(ldapUser.getPassword());
			userDetails = new in.kmbs.vlethyme.security.User(loginUser, true, true, true, true, getAuthorities(loginUser));
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Error in retrieving user", e);
		}
		// Return user to Spring for processing. the actual authentication is
		// done by spring
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
}
