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
 * Question generated by hbm2java
 */
@Entity
@Table(name = "question", catalog = "vlethyme")
public class Question implements java.io.Serializable {

	private int id;
	private Quiz quiz;
	private String text;

	public Question() {
	}

	public Question(int id, Quiz quiz, String text) {
		this.id = id;
		this.quiz = quiz;
		this.text = text;
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
	@JoinColumn(name = "quizId", nullable = false)
	public Quiz getQuiz() {
		return this.quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	@Column(name = "text", nullable = false, length = 65535)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
