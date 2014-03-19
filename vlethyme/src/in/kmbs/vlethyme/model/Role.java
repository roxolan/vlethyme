package in.kmbs.vlethyme.model;

import java.util.HashSet;
import java.util.Set;

public class Role implements java.io.Serializable {

	private Integer id;
	private String name;
	private Integer type;
	private Set<UserCourse> userCourses = new HashSet<UserCourse>(0);
	private Set<EventUser> eventUsers = new HashSet<EventUser>(0);
	private Set<GroupUser> groupUsers = new HashSet<GroupUser>(0);

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Set<UserCourse> getUserCourses() {
		return this.userCourses;
	}

	public void setUserCourses(Set<UserCourse> userCourses) {
		this.userCourses = userCourses;
	}

	public Set<EventUser> getEventUsers() {
		return this.eventUsers;
	}

	public void setEventUsers(Set<EventUser> eventUsers) {
		this.eventUsers = eventUsers;
	}

	public Set<GroupUser> getGroupUsers() {
		return this.groupUsers;
	}

	public void setGroupUsers(Set<GroupUser> groupUsers) {
		this.groupUsers = groupUsers;
	}

}
