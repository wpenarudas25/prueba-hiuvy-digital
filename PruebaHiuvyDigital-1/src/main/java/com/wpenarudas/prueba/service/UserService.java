package com.wpenarudas.prueba.service;

import com.wpenarudas.prueba.dto.ChangePassword;
import com.wpenarudas.prueba.entity.User;

public interface UserService {

	public Iterable<User> getAllUsers();
	
	public User createUser(User user) throws Exception;

	public User getUserById(Long id) throws Exception;
	
	public User updateUser(User user) throws Exception;
	
	public void deleteUser(Long id) throws Exception;
	
	public User changePassword(ChangePassword form) throws Exception;
}