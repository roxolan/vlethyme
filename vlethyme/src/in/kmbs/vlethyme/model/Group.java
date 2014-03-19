package in.kmbs.vlethyme.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group implements java.io.Serializable {

	private int id;
	private User user;
	private String name;
	private Date createdDate;
	private List<EventUser> eventUsers = new ArrayList<EventUser>(0);
	private List<GroupUser> groupUsers = new ArrayList<GroupUser>(0);

	public Group() {
	}

	public Group(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<EventUser> getEventUsers() {
		return this.eventUsers;
	}

	public void setEventUsers(List<EventUser> eventUsers) {
		this.eventUsers = eventUsers;
	}

	public List<GroupUser> getGroupUsers() {
		return this.groupUsers;
	}

	public void setGroupUsers(List<GroupUser> groupUsers) {
		this.groupUsers = groupUsers;
	}

}
