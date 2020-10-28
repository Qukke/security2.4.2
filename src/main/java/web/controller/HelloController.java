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
import java.util.List;

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
//		System.out.println(user.toString());
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
		List<Role> allRoles = new ArrayList<>();
		allRoles.add(new Role(1L, "USER"));
		allRoles.add(new Role(2L, "ADMIN"));
		model.addAttribute("allRoles", allRoles);
		return "/editUser";
	}
	/*
	@PostMapping("/editUser/{id}")
    public String editUser(ModelMap model, @PathVariable Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", userService.getAllRoles());
        return "editUser";
    }
    @PostMapping("/editUser")
    public String edit(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }
	 */

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

/*
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }
}


@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }
}

 */
