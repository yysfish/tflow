package tflow.com.yzs.flow.service;

import java.util.List;

import tflow.com.yzs.flow.entity.User;

public interface UserService {

	/**
	 * 根据用户ID获取用户信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User getUser(String userId) throws Exception;
	
	/**
	 * 获取所有用户
	 * @return
	 * @throws Exception
	 */
	public List<User> getAllUser() throws Exception;
	
}
