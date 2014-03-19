package in.kmbs.vlethyme.entity;

// Generated Mar 18, 2014 3:05:04 PM by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Group generated by hbm2java
 */
@Entity
@Table(name = "group", catalog = "vlethyme")
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

	public Group(int id, User user, String name, Date createdDate, List<EventUser> eventUsers, List<GroupUser> groupUsers) {
		this.id = id;
		this.user = user;
		this.name = name;
		this.createdDate = createdDate;
		this.eventUsers = eventUsers;
		this.groupUsers = groupUsers;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	public List<EventUser> getEventUsers() {
		return this.eventUsers;
	}

	public void setEventUsers(List<EventUser> eventUsers) {
		this.eventUsers = eventUsers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	public List<GroupUser> getGroupUsers() {
		return this.groupUsers;
	}

	public void setGroupUsers(List<GroupUser> groupUsers) {
		this.groupUsers = groupUsers;
	}

}