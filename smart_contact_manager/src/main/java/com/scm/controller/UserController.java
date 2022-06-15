package com.scm.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entity.Contact;
import com.scm.entity.User;
import com.scm.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal)
	{
		String name = principal.getName();
		User user = userService.findUserByEmail(name);
		model.addAttribute("user",user);
	}

	@GetMapping("/index")
	public String dashboard(Model model)
	{
		return "normal/user_dashboard";
	}
	
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model)
	{
		model.addAttribute("title","add contacts");
		model.addAttribute("contact",new Contact());
		return "normal/add_contact_form";
	}
	
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute("contact")Contact contact,Model model,Principal principal)
	{
		String name = principal.getName();
		User user = this.userService.findUserByEmail(name);
		contact.setUser(user);
		user.getContacts().add(contact);
		this.userService.saveUser(user);
		return "normal/add_contact_form";
	}
}
