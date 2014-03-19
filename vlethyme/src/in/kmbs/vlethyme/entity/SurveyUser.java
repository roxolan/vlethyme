package in.kmbs.vlethyme.entity;

// Generated Mar 18, 2014 3:05:04 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SurveyUser generated by hbm2java
 */
@Entity
@Table(name = "survey_user", catalog = "vlethyme")
public class SurveyUser implements java.io.Serializable {

	private int id;
	private User user;
	private Survey survey;
	private GroupUser groupUser;
	private Integer surveyId;
	private Integer groupId;
	private Integer userId;

	public SurveyUser() {
	}

	public SurveyUser(int id, User user, Survey survey, GroupUser groupUser) {
		this.id = id;
		this.user = user;
		this.survey = survey;
		this.groupUser = groupUser;
	}

	public SurveyUser(int id, User user, Survey survey, GroupUser groupUser, Integer surveyId, Integer groupId, Integer userId) {
		this.id = id;
		this.user = user;
		this.survey = survey;
		this.groupUser = groupUser;
		this.surveyId = surveyId;
		this.groupId = groupId;
		this.userId = userId;
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
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "survey_id", nullable = false)
	public Survey getSurvey() {
		return this.survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_user_id", nullable = false)
	public GroupUser getGroupUser() {
		return this.groupUser;
	}

	public void setGroupUser(GroupUser groupUser) {
		this.groupUser = groupUser;
	}

	@Column(name = "surveyId")
	public Integer getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	@Column(name = "groupId")
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "userId")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}