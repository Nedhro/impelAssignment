package com.impelitAssignment.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

	@RequestMapping({ "/" })
	public String showProfile() {
		return "welcome";
	}

}
