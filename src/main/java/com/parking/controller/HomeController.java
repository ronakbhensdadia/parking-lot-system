package com.parking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
	return "<h2>Welcome to Parking Lot System</h2>"
		+ "<a href='/oauth2/authorization/google'>Login with Google</a>";
    }
    
    @GetMapping("/success")
    @ResponseBody
    public String success() {
        return "<h3>Login successful! 🎉</h3>";
    }
}