package in.kmbs.vlethyme.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

	@Id
	@Column(name="iduser")	
	private String iduser;
	
	@Column(name="email")
	private String email;

	public User () {
		
	}
	
	public User(String iduser, String email) {
		this.iduser = iduser;
		this.email = email;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [username=" + iduser + ", email=" + email + "]";
	}
	
	
	
	
	
}
