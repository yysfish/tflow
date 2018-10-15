package tflow.com.yzs.flow.mongodao;

import java.util.List;

import com.mongodb.WriteResult;

import tflow.com.yzs.flow.entity.User;

public interface MongoDAO {
	
	public void saveAll(List<User> users) throws Exception;

	public void save(User user) throws Exception;
	
	public User getUser(String id) throws Exception;
	
	public WriteResult updateUserById(User user) throws Exception;
	
	public List<User> findUser(User user) throws Exception;
	
	public List<User> findAll() throws Exception;
	
	public WriteResult deleteUser(User user) throws Exception;
	
}
