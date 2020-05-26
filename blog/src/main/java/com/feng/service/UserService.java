package com.feng.service;

import com.feng.dao.UserRepository;
import com.feng.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface UserService {

	User checkUser(String username,String password);
}
