package tflow.com.yzs.flow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;

import tflow.com.yzs.flow.entity.User;
import tflow.com.yzs.flow.mongodao.MongoDAO;
import tflow.com.yzs.flow.service.MongoService;

@Service
public class MongoServiceImpl implements MongoService{

	@Autowired
	private MongoDAO mongoDAO;
	
	public void saveAll(List<User> users) throws Exception {
		mongoDAO.saveAll(users);
		
	}

	public void save(User user) throws Exception {
		mongoDAO.save(user);
		
	}

	public User getUser(String id) throws Exception {
		return mongoDAO.getUser(id);
	}

	public WriteResult updateUserById(User user) throws Exception {
		return mongoDAO.updateUserById(user);
	}

	public List<User> findUser(User user) throws Exception {
		return mongoDAO.findUser(user);
	}

	public List<User> findAll() throws Exception {
		return mongoDAO.findAll();
	}

	public WriteResult deleteUser(User user) throws Exception {
		return mongoDAO.deleteUser(user);
	}

}
