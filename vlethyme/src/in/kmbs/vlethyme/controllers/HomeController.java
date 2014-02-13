package in.kmbs.vlethyme.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	private static Logger logger = Logger.getLogger(HomeController.class);


	@RequestMapping(value="/")
	public String showIndex() {
		logger.info("Показую домашню сторінку -- showing home page....");
		return "index";
	}
	
	@RequestMapping(value="/anon")
	public String showIndexAnonymous() {
		return "index-anonymous";
	}
	
	@RequestMapping(value="/me")
	public String showMe() {
		return "me";
	}
}