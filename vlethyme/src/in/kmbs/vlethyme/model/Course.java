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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Course generated by hbm2java
 */
@Entity
@Table(name = "course", catalog = "vlethyme")
public class Course implements java.io.Serializable {

	private Integer id;
	private String name;
	private String description;
	private Set<CourseResource> courseResources = new HashSet<CourseResource>(0);
	private Set<UserCourse> userCourses = new HashSet<UserCourse>(0);
	private Set<Forum> forums = new HashSet<Forum>(0);
	private Set<Module> modules = new HashSet<Module>(0);

	public Course() {
	}

	public Course(String name) {
		this.name = name;
	}

	public Course(String name, String description, Set<CourseResource> courseResources, Set<UserCourse> userCourses, Set<Forum> forums, Set<Module> modules) {
		this.name = name;
		this.description = description;
		this.courseResources = courseResources;
		this.userCourses = userCourses;
		this.forums = forums;
		this.modules = modules;
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

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	public Set<CourseResource> getCourseResources() {
		return this.courseResources;
	}

	public void setCourseResources(Set<CourseResource> courseResources) {
		this.courseResources = courseResources;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	public Set<UserCourse> getUserCourses() {
		return this.userCourses;
	}

	public void setUserCourses(Set<UserCourse> userCourses) {
		this.userCourses = userCourses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	public Set<Forum> getForums() {
		return this.forums;
	}

	public void setForums(Set<Forum> forums) {
		this.forums = forums;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	public Set<Module> getModules() {
		return this.modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}

}