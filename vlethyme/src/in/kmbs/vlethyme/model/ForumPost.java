package in.kmbs.vlethyme.model;

// Generated Mar 25, 2014 5:04:32 PM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ForumPosts generated by hbm2java
 */
@Entity
@Table(name = "forum_posts")
public class ForumPost implements java.io.Serializable {

	private Integer id;
	private Forum forum;
	private User user;
	private Integer parentPostId;
	private Date createDate;
	private String content;

	public ForumPost() {
	}

	public ForumPost(Forum forum, User user, Date createDate, String content) {
		this.forum = forum;
		this.user = user;
		this.createDate = createDate;
		this.content = content;
	}

	public ForumPost(Forum forum, User user, Integer parentPostId, Date createDate, String content) {
		this.forum = forum;
		this.user = user;
		this.parentPostId = parentPostId;
		this.createDate = createDate;
		this.content = content;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forumId", nullable = false)
	public Forum getForum() {
		return this.forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "parentPostId")
	public Integer getParentPostId() {
		return this.parentPostId;
	}

	public void setParentPostId(Integer parentPostId) {
		this.parentPostId = parentPostId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "content", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}