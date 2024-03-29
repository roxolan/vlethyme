package in.kmbs.vlethyme.model;

// Generated Mar 18, 2014 3:05:04 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Assignment generated by hbm2java
 */
@Entity
@Table(name = "assignment", catalog = "vlethyme")
public class Assignment implements java.io.Serializable {

	private Integer id;
	private Module module;
	private byte type;
	private String subject;
	private String text;
	private Set<Quiz> quizs = new HashSet<Quiz>(0);
	private Set<GradeAssignment> gradeAssignments = new HashSet<GradeAssignment>(0);

	public Assignment() {
	}

	public Assignment(Module module, byte type) {
		this.module = module;
		this.type = type;
	}

	public Assignment(Module module, byte type, String subject, String text, Set<Quiz> quizs, Set<GradeAssignment> gradeAssignments) {
		this.module = module;
		this.type = type;
		this.subject = subject;
		this.text = text;
		this.quizs = quizs;
		this.gradeAssignments = gradeAssignments;
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
	@JoinColumn(name = "moduleId", nullable = false)
	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	@Column(name = "type", nullable = false)
	public byte getType() {
		return this.type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	@Column(name = "subject", length = 65535)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "text", length = 65535)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assignment")
	public Set<Quiz> getQuizs() {
		return this.quizs;
	}

	public void setQuizs(Set<Quiz> quizs) {
		this.quizs = quizs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assignment")
	public Set<GradeAssignment> getGradeAssignments() {
		return this.gradeAssignments;
	}

	public void setGradeAssignments(Set<GradeAssignment> gradeAssignments) {
		this.gradeAssignments = gradeAssignments;
	}

}
