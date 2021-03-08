package com.wpenarudas.prueba.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wpenarudas.prueba.dto.ChangePassword;
import com.wpenarudas.prueba.entity.User;
import com.wpenarudas.prueba.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	@Override
	public Iterable<User> getAllUsers() {
		return repository.findAll();
	}

	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = repository.findByUsername(user.getUsername());
		if (userFound.isPresent()) {
			throw new Exception("Username no disponible");
		}
		return true;
	}

	private boolean checkEmailAvailable(User user) throws Exception {
		Optional<User> userFound = repository.findByEmail(user.getEmail());
		if (userFound.isPresent()) {
			throw new Exception("Email no disponible");
		}
		return true;
	}

	private boolean checkPasswordValid(User user) throws Exception {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Password y Confirm Password no son iguales");
		}
		return true;
	}

	@Override
	public User createUser(User user) throws Exception {
		if (checkUsernameAvailable(user) && checkPasswordValid(user) && checkEmailAvailable(user)) {
			user = repository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new Exception("El usuario no existe."));

	}

	@Override
	public User updateUser(User user) throws Exception {
		User usuarioEncontrado = getUserById(user.getId());
		String Password = user.getPassword();
		user.setPassword(Password);
		String confirmPassword = usuarioEncontrado.getPassword();
		user.setConfirmPassword(confirmPassword);
		mapUsuario(user, usuarioEncontrado);
		return repository.save(usuarioEncontrado);
	}
	
	protected void mapUsuario(User from, User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setRoles(from.getRoles());
		to.setEmail(from.getEmail());
		to.setPassword(from.getPassword());
		to.setConfirmPassword(from.getConfirmPassword());
	}

	@Override
	public void deleteUser(Long id) throws Exception {
		User user = repository.findById(id)
				.orElseThrow(() -> new Exception("No se encontró el usuario a eliminar." + this.getClass().getName()));
		repository.delete(user);

	}

	@Override
	public User changePassword(ChangePassword form) throws Exception {
User user = getUserById(form.getId());
		
				
		if( user.getPassword().equals(form.getNewPassword())) {
			throw new Exception ("Nuevo debe ser diferente al password actual.");
		}
		
		if( !form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception ("Nuevo Password y Confirm Password no coinciden.");
		}
		
		
		return repository.save(user);
	}

}
