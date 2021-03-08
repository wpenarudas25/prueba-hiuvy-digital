package com.wpenarudas.prueba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String index() {
		return "index";
	}

	@GetMapping("/userForm")
	public String userForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		return "usuarios/formUsuarios";
	}

	@PostMapping("/userForm")
	public String createUser(@Validated @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("userList", userService.getAllUsers());
		} else {
			try {// Aca tendras error porque este metodo no existe, pero lo crearemos en la
					// siguiente seccion.
				userService.createUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("userList", userService.getAllUsers());

			} catch (Exception e) {
				model.addAttribute("formError", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles", roleRepository.findAll());
			}
		}

		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		return "usuarios/formUsuarios";

	}

	@GetMapping("/list")
	public String userlist(Model model) {
		model.addAttribute("userList", userService.getAllUsers());
		return "usuarios/listUsuarios";
	}

}
