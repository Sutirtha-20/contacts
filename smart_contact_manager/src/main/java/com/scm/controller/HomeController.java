package com.scm.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entity.User;
import com.scm.helper.Message;
import com.scm.service.UserService;

@Controller
public class HomeController { 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title","Home- smart contact");
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title","About- smart contact");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("title","sign-up page");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user")User user,BindingResult result,@RequestParam(value="agreement",defaultValue = "false")boolean agreement,Model model,HttpSession session)
	{
		try {
			System.out.println(agreement);
			if(!agreement)
			{
				System.out.println("didnt agree terms and conditions");
				throw new Exception("Something went wromg terms and conditions not agreed");
			}
			if(result.hasErrors())
			{
				model.addAttribute("user",user);
			}
			user.setRole("ROLE_USER");
			user.setEnable(true);
			user.setImageURL("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
//			System.out.println(user);
			userService.saveUser(user);
			model.addAttribute("user",new User());
			session.setAttribute("message", new Message("successfully registered","alert-success"));
			System.out.println("user saved");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Something went wrong","alert-danger"));
			
		}
		return "signup";
		
	}
	
	@GetMapping("/signin")
	public String customLogin(Model model)
	{
		model.addAttribute("title","login page");
		return "login";
	}
}
