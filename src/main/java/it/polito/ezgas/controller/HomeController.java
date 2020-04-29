package it.polito.ezgas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("/admin")
	public String admin() {
		return "index";
	}
	
	@RequestMapping("/")
	public String index() {
		return map();
	}
	
	@RequestMapping("/map")
	public String map() {
		return "map";
	}
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	@RequestMapping("/update")
	public String update() {
		return "update";
	}
	@RequestMapping("/signup")
	public String signup() {
		return "signup";
	}
}
