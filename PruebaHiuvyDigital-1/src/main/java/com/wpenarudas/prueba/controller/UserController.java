package com.wpenarudas.prueba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.wpenarudas.prueba.dto.ChangePassword;
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

	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model, @PathVariable(name = "id") Long id) throws Exception {
		User userToEdit = userService.getUserById(id);
		model.addAttribute("usuarios", userService.getAllUsers());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("userForm", userToEdit);

		model.addAttribute("passwordForm",new ChangePassword(id));

		return "usuarios/editarUsuario";
	}

	@PostMapping("/editUser")
	public String editarUsuario(@Validated @ModelAttribute("userForm") User user, BindingResult result,
			ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("userList", userService.getAllUsers());
			model.addAttribute("passwordForm", new ChangePassword(user.getId()));
			//model.addAttribute("nombre_usuario", SecurityContextHolder.getContext().getAuthentication().getName());
		} else {
			try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User());
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("usuarios", userService.getAllUsers());
				model.addAttribute("roles", roleRepository.findAll());
				model.addAttribute("passwordForm", new ChangePassword(user.getId()));
				//model.addAttribute("nombre_usuario", SecurityContextHolder.getContext().getAuthentication().getName());
			}
		}

		return "redirect:/list";

	}
	
	@GetMapping("/eliminarUsuario/{id}")
	public String deleteUser(Model model,  @PathVariable(name="id") Long id ) {
		try {
			userService.deleteUser(id);
		} catch (Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		return "redirect:/list";
	}
	
	@GetMapping("/editUserPassword/{id}")
	public String getEditUserPasswordForm(Model model, @PathVariable(name="id") Long id) throws Exception {
		User userEdit = userService.getUserById(id);		
		model.addAttribute("usuarios", userService.getUserById(id));
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute("userForm", userEdit);		
		model.addAttribute("passwordForm",new ChangePassword(id));
		
		return "usuarios/changePassword";
	}
	
	@PostMapping("/editUserPassword")
	public String editarUsuarioPassword(@Validated @ModelAttribute("userForm") User user, BindingResult result, ModelMap model)  {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);		
			model.addAttribute("passwordForm",new ChangePassword(user.getId()));
		}else {
			 try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User());
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("usuarios", userService.getAllUsers());		
				model.addAttribute("roles", roleRepository.findAll());
				model.addAttribute("passwordForm",new ChangePassword(user.getId()));
			}
		}
		
		return "redirect:/list";
				
	}	

}
