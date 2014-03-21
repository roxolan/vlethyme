package in.kmbs.vlethyme.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonGetter;

public class User implements java.io.Serializable {

	private Integer id;
	private Role role;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String username;
	private Set<MessgeRecepient> messgeRecepients = new HashSet<MessgeRecepient>(0);
	private Set<Forum> forums = new HashSet<Forum>(0);
	private Set<EventUser> eventUsers = new HashSet<EventUser>(0);
	private Set<UserCourse> userCourses = new HashSet<UserCourse>(0);
	private Set<GroupUser> groupUsers = new HashSet<GroupUser>(0);
	private Set<Group> groups = new HashSet<Group>(0);
	private Set<UserModule> userModules = new HashSet<UserModule>(0);
	private Set<SurveyUser> surveyUsers = new HashSet<SurveyUser>(0);
	private Set<SurveyAnswer> surveyAnswers = new HashSet<SurveyAnswer>(0);

	public User() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role roleId) {
		this.role = role;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<MessgeRecepient> getMessgeRecepients() {
		return this.messgeRecepients;
	}

	public void setMessgeRecepients(Set<MessgeRecepient> messgeRecepients) {
		this.messgeRecepients = messgeRecepients;
	}

	public Set<Forum> getForums() {
		return this.forums;
	}

	public void setForums(Set<Forum> forums) {
		this.forums = forums;
	}

	public Set<EventUser> getEventUsers() {
		return this.eventUsers;
	}

	public void setEventUsers(Set<EventUser> eventUsers) {
		this.eventUsers = eventUsers;
	}

	public Set<UserCourse> getUserCourses() {
		return this.userCourses;
	}

	public void setUserCourses(Set<UserCourse> userCourses) {
		this.userCourses = userCourses;
	}

	public Set<GroupUser> getGroupUsers() {
		return this.groupUsers;
	}

	public void setGroupUsers(Set<GroupUser> groupUsers) {
		this.groupUsers = groupUsers;
	}

	public Set<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Set<UserModule> getUserModules() {
		return this.userModules;
	}

	public void setUserModules(Set<UserModule> userModules) {
		this.userModules = userModules;
	}

	public Set<SurveyUser> getSurveyUsers() {
		return this.surveyUsers;
	}

	public void setSurveyUsers(Set<SurveyUser> surveyUsers) {
		this.surveyUsers = surveyUsers;
	}

	public Set<SurveyAnswer> getSurveyAnswers() {
		return this.surveyAnswers;
	}

	public void setSurveyAnswers(Set<SurveyAnswer> surveyAnswers) {
		this.surveyAnswers = surveyAnswers;
	}
	
	@JsonGetter
	public String getDisplayName() {
		return StringUtils.join(new String[]{"", firstName,lastName}, " ");
		
	}
}
