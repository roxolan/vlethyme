package in.kmbs.vlethyme.entity;

// Generated Mar 18, 2014 3:05:04 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Scorm generated by hbm2java
 */
@Entity
@Table(name = "scorm", catalog = "vlethyme")
public class Scorm implements java.io.Serializable {

	private Integer id;

	public Scorm() {
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

}
