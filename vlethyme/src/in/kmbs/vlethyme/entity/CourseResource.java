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
 * CourseResource generated by hbm2java
 */
@Entity
@Table(name = "course_resource", catalog = "vlethyme")
public class CourseResource implements java.io.Serializable {

	private int id;
	private Resource resource;
	private Course course;

	public CourseResource() {
	}

	public CourseResource(int id, Resource resource, Course course) {
		this.id = id;
		this.resource = resource;
		this.course = course;
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
	@JoinColumn(name = "resourceId", nullable = false)
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "courseId", nullable = false)
	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
