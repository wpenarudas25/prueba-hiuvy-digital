package com.wpenarudas.prueba.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/index")
	public String index(){
		return "index";
	}
	
	@GetMapping("/create")
	public String userForm(){
		return "usuarios/formUsuarios";
	}
	
	@GetMapping("/list")
	public String userlist(){
		return "usuarios/listUsuarios";
	}

}
