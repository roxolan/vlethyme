package in.kmbs.vlethyme.controllers;

import java.util.List;

import in.kmbs.vlethyme.dao.User;
import in.kmbs.vlethyme.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	private static Logger logger = Logger.getLogger(HomeController.class);
	
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value="/")
	public String showIndex() {
		logger.info("Показую домашню сторінку -- showing home page....");
		return "index";
	}
	
	@RequestMapping(value="/anon")
	public String showIndexAnonymous() {
		return "index-anonymous";
	}
	
	@RequestMapping(value="/loggedin")
	public String showIndexLoggedIn() {
		return "index-loggedin";
	}
	
	@RequestMapping(value="/me")
	public String showMe() {
		return "me";
	}
	
	@RequestMapping(value="/content")
	public String showContent() {
		return "content";
	}
	
	@RequestMapping(value="/group")
	public String showGroup(Model model) {
		
		List<User> users = userService.getAllUsers();
		
		model.addAttribute("users", users);
		
		return "group";
	}
	
	@RequestMapping(value="/course")
	public String showCourse() {
		return "course";
	}
	
	@RequestMapping(value="/program")
	public String showProgram() {
		return "program";
	}
}