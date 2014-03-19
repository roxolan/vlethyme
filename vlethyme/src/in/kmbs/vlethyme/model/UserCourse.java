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
 * UserCourse generated by hbm2java
 */
@Entity
@Table(name = "user_course", catalog = "vlethyme")
public class UserCourse implements java.io.Serializable {

	private Integer id;
	private Role role;
	private Course course;
	private User user;
	private boolean allModules;
	private Set<GradeAssignment> gradeAssignments = new HashSet<GradeAssignment>(0);

	public UserCourse() {
	}

	public UserCourse(Role role, Course course, User user, boolean allModules) {
		this.role = role;
		this.course = course;
		this.user = user;
		this.allModules = allModules;
	}

	public UserCourse(Role role, Course course, User user, boolean allModules, Set<GradeAssignment> gradeAssignments) {
		this.role = role;
		this.course = course;
		this.user = user;
		this.allModules = allModules;
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
	@JoinColumn(name = "roleId", nullable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "courseId", nullable = false)
	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "allModules", nullable = false)
	public boolean isAllModules() {
		return this.allModules;
	}

	public void setAllModules(boolean allModules) {
		this.allModules = allModules;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userCourse")
	public Set<GradeAssignment> getGradeAssignments() {
		return this.gradeAssignments;
	}

	public void setGradeAssignments(Set<GradeAssignment> gradeAssignments) {
		this.gradeAssignments = gradeAssignments;
	}

}