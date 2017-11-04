package com.team.expressway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
	
	@RequestMapping("/redirect")
	public String testController(Model model) {
		System.out.println("HERE");
		return "redirect:redirect_page";
	}

}
