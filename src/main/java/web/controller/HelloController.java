package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HelloController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String helloPage() {
		return "hello";
	}


	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		model.addAttribute("listUsers", this.userService.listUsers());
		model.addAttribute("user", new User());
		return "admin";
	}

	@GetMapping(value = "/add")
	public String addUser(@ModelAttribute("user") User user) {
		this.userService.add(user);
		return "redirect:/admin";
	}

	@GetMapping("/remove")
	public String removeUser(@RequestParam("id") Long id) {
		this.userService.removeById(id);
		return "redirect:/admin";
	}

	@GetMapping(value = "/editUser")
	public String editUser(ModelMap model, @RequestParam("id") Long id) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		Set<Role> allRoles = new HashSet<>();
		allRoles.add(new Role(1L, "USER"));
		allRoles.add(new Role(2L, "ADMIN"));
		model.addAttribute("allRoles", allRoles);
		return "/editUser";
	}

	@GetMapping(value = "/edit")
	public String edit(@ModelAttribute("user") User user) {
		userService.edit(user);
		return "redirect:/admin";
	}

	@GetMapping(value = "/userpage")
	public String show(ModelMap modelMap) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByName(auth.getName());
		modelMap.addAttribute("user", user);
		return "/userpage";
	}
}
