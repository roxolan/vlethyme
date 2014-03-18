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
 * SurveyAnswer generated by hbm2java
 */
@Entity
@Table(name = "survey_answer", catalog = "vlethyme")
public class SurveyAnswer implements java.io.Serializable {

	private int id;
	private SurveyOption surveyOption;
	private SurveyQuestion surveyQuestion;
	private User user;

	public SurveyAnswer() {
	}

	public SurveyAnswer(int id, SurveyOption surveyOption, SurveyQuestion surveyQuestion, User user) {
		this.id = id;
		this.surveyOption = surveyOption;
		this.surveyQuestion = surveyQuestion;
		this.user = user;
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
	@JoinColumn(name = "optionId", nullable = false)
	public SurveyOption getSurveyOption() {
		return this.surveyOption;
	}

	public void setSurveyOption(SurveyOption surveyOption) {
		this.surveyOption = surveyOption;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "questionId", nullable = false)
	public SurveyQuestion getSurveyQuestion() {
		return this.surveyQuestion;
	}

	public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
