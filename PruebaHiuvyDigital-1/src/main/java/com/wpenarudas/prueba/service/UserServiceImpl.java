package com.wpenarudas.prueba.service;

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

	@Override
	public User createUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
