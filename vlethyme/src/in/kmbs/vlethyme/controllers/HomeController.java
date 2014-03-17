package in.kmbs.vlethyme.controllers;

import in.kmbs.vlethyme.service.UserService;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@RequestMapping(value = "/group")
	public String showGroup(Model model) {

		//List<User> users = userService.getAllUsers();

		model.addAttribute("users", "asd");

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

	@RequestMapping(value = "/user")
	public String showUser() {
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