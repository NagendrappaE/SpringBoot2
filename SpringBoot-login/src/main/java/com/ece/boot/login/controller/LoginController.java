/**
 * 
 */
package com.ece.boot.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author nagendrappae
 *
 */
@Controller
public class LoginController {
	@GetMapping("/login")
	String login() {
		return "login";
	}
	
	@GetMapping("/dbtl")
	String welcome() {
		return "dbtl";
	}
	
}
