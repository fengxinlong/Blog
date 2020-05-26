package com.feng.service;

import com.feng.dao.UserRepository;
import com.feng.po.User;
import com.feng.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public User checkUser(String username, String password) {
		User user=userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));

		return user;
	}
}
