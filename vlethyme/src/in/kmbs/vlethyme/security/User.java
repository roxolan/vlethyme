package in.kmbs.vlethyme.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;


public class User extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -2718742746451434858L;

	private in.kmbs.vlethyme.model.User user;

	public in.kmbs.vlethyme.model.User getUser() {
		return user;
	}


	public User(in.kmbs.vlethyme.model.User user, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), "", enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}
}