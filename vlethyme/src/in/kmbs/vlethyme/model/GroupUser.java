package in.kmbs.vlethyme.model;


public class GroupUser implements java.io.Serializable {

	private Integer id;
	private Role role;
	private Group group;
	private User user;
	
	public GroupUser() {
	}

	public GroupUser(Group group, User user) {
		this.group = group;
		this.user = user;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
