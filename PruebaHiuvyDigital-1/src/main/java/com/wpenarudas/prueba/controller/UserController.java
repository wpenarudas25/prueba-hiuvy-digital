package com.wpenarudas.prueba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wpenarudas.prueba.entity.User;
import com.wpenarudas.prueba.repository.RoleRepository;
import com.wpenarudas.prueba.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/index")
	public String index(){
		return "index";
	}
	
	@GetMapping("/create")
	public String userForm(Model model){
		model.addAttribute("userForm", new User());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		return "usuarios/formUsuarios";
	}
	
	@GetMapping("/list")
	public String userlist(Model model){
		model.addAttribute("userList", userService.getAllUsers());
		return "usuarios/listUsuarios";
	}

}
