package tflow.com.yzs.flow.dao;

import java.util.List;

import tflow.com.yzs.flow.entity.User;

public interface UserDAO {

	public User getUser(String userId) throws Exception;
	
	public List<User> getAllUser() throws Exception;
}
