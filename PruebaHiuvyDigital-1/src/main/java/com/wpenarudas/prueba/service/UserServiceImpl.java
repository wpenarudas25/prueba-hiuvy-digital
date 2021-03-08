package com.wpenarudas.prueba.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wpenarudas.prueba.entity.User;
import com.wpenarudas.prueba.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
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
			//String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
			//user.setPassword(encodedPassword);
			user = repository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
