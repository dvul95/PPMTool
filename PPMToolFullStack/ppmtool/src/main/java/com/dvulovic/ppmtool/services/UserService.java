package com.dvulovic.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dvulovic.ppmtool.domain.User;
import com.dvulovic.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.dvulovic.ppmtool.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User user) {
		try {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setUsername(user.getUsername());
		user.setConfirmedPassword("");
		return userRepository.save(user);
		
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username: "+ user.getUsername() + " already exists.");
		}
	}
}
