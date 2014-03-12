package in.kmbs.vlethyme.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

public class VlethymeLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

	@Override
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations arg0, String arg1) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		authList.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return "secure";
			}
		});
		return authList;

	}

}
