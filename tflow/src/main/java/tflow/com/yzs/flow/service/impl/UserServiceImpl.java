package tflow.com.yzs.flow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tflow.com.yzs.flow.dao.UserDAO;
import tflow.com.yzs.flow.entity.User;
import tflow.com.yzs.flow.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;
	
	public User getUser(String userId) throws Exception {
		return userDAO.getUser(userId);
	}

	public List<User> getAllUser() throws Exception {
		return userDAO.getAllUser();
	}


}
