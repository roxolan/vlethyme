package in.kmbs.vlethyme.controllers;

import in.kmbs.vlethyme.service.UserService;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/")
	public String showIndex() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return "redirect:/me";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/me")
	public String showMe() {
		return "me";
	}
	
	@RequestMapping(value = "/me/dashboard")
	public String showMeDashboard() {
		return "me-dashboard";
	}
	
	@RequestMapping(value = "/me/courses")
	public String showMeCourses() {
		return "me-courses";
	}
	
	@RequestMapping(value = "/me/groups")
	public String showMeGroups() {
		return "me-groups";
	}
	
	@RequestMapping(value = "/me/users")
	public String showMeUsers() {
		return "me-users";
	}
	
	@RequestMapping(value = "/me/content")
	public String showMeContent() {
		return "me-content";
	}
	
	@RequestMapping(value = "/me/discussions")
	public String showMeDiscussions() {
		return "me-discussions";
	}
	
	@RequestMapping(value = "/me/network")
	public String showMeNetwork() {
		return "me-network";
	}

	@RequestMapping(value = "/content")
	public String showContent() {
		return "content";
	}

	@RequestMapping(value = "/group/activity")
	public String showGroupActivity() {
		return "group-activity";
	}
	
	@RequestMapping(value = "/group/library")
	public String showGroupLibrary() {
		return "group-library";
	}
	
	@RequestMapping(value = "/group/discussions")
	public String showGroupDiscussions() {
		return "group-discussions";
	}
	
	@RequestMapping(value = "/group/members")
	public String showGroupMembers() {
		return "group-members";
	}
	
	@RequestMapping(value = "/group")
	public String showGroup(Model model) {
		return "group";
	}

	@RequestMapping(value = "/course")
	public String showCourse() {
		return "course";
	}

	@RequestMapping(value = "/program")
	public String showProgram() {
		return "program";
	}

	@RequestMapping(value = "/search")
	public String showSearch() {
		return "search";
	}

	@RequestMapping(value = "/discussion")
	public String showDiscussion() {
		return "discussion";
	}

	
	@RequestMapping(value = "/partials/contentlibrary")
	public String showContentLibraries() {
		return "partials/contentlibrary";
	}
	
	@RequestMapping(value = " /user/{userId}", method=RequestMethod.GET)
	public String getOrder(@PathVariable Integer userId){
		return "user";
	}

	@RequestMapping(value = "/calendar")
	public String showCalendar() {
		return "calendar";
	}

	@RequestMapping(value = "/getmessages", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getMessages(Principal principal) {
		return null;
	}
}