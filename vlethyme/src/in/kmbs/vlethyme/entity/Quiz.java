package in.kmbs.vlethyme.entity;

// Generated Mar 18, 2014 3:05:04 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Quiz generated by hbm2java
 */
@Entity
@Table(name = "quiz", catalog = "vlethyme")
public class Quiz implements java.io.Serializable {

	private int id;
	private Assignment assignment;
	private Set<Question> questions = new HashSet<Question>(0);

	public Quiz() {
	}

	public Quiz(int id, Assignment assignment) {
		this.id = id;
		this.assignment = assignment;
	}

	public Quiz(int id, Assignment assignment, Set<Question> questions) {
		this.id = id;
		this.assignment = assignment;
		this.questions = questions;
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
	@JoinColumn(name = "assignmentId", nullable = false)
	public Assignment getAssignment() {
		return this.assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz")
	public Set<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

}
