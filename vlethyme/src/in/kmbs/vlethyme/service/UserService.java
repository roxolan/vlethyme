package in.kmbs.vlethyme.service;

import in.kmbs.vlethyme.dao.UserDAO;
import in.kmbs.vlethyme.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private LdapTemplate ldapTemplate;

	private static class UserAttributesMapper implements AttributesMapper {

		public Object mapFromAttributes(Attributes attrs) throws NamingException {
			in.kmbs.vlethyme.model.User user = new in.kmbs.vlethyme.model.User();
			user.setPassword((String) attrs.get("userPassword").get());
			return user;
		}
	}

	public List<User> getUsers() {
		return userDAO.getUsers();
	}

	public List<User> findUsersByNameLike(String name, int limit) {
		return userDAO.findUsersByNameLike(name, limit);
	}
	
	public User findUserById(Integer userId) {
		return userDAO.findUserById(userId);
	}

	public void createUser(User user) {
		userDAO.createUser(user);

		Name dn = buildDn(user);
		ldapTemplate.bind(dn, null, buildAttributes(user));

	}

	public User findUserByUsername(String username) {
		return userDAO.findUserByUsername(username);
	}

	public in.kmbs.vlethyme.model.User getLdapUser(final String userName) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "inetOrgPerson")).and(new EqualsFilter("uid", userName));
		@SuppressWarnings("unchecked")
		List<in.kmbs.vlethyme.model.User> users = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), new UserAttributesMapper());
		if (!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}

	public boolean authenticate(String userName, String password) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "inetOrgPerson")).and(new EqualsFilter("uid", userName));
		return ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
	}

	private Name buildDn(final User user) {
		DistinguishedName dn = new DistinguishedName();
		dn.add("uid", user.getUsername());
		return dn;
	}

	private Attributes buildAttributes(final User user) {
		Attributes attrs = new BasicAttributes();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("posixAccount");
		ocattr.add("top");
		ocattr.add("inetOrgPerson");
		attrs.put(ocattr);
		attrs.put("cn", user.getFirstName());
		attrs.put("sn", user.getLastName());
		attrs.put("userPassword", "{SHA}" + this.encrypt(user.getPassword()));
		attrs.put("mail", user.getEmail());

		return attrs;
	}

	private String encrypt(final String plaintext) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
		try {
			md.update(plaintext.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
		byte raw[] = md.digest();
		String hash = new String(Base64.encode(raw));
		return hash;
	}

}
