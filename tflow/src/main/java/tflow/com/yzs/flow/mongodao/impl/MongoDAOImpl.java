package tflow.com.yzs.flow.mongodao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.WriteResult;

import tflow.com.yzs.flow.entity.User;
import tflow.com.yzs.flow.mongodao.MongoDAO;

@Component
public class MongoDAOImpl implements MongoDAO{

	@Autowired
	private MongoTemplate template;
	
	public void save(User user) throws Exception {
		template.save(user, "user");		
	}

	public User getUser(String id) throws Exception {		
		return template.findById(id, User.class);
	}

	public WriteResult updateUserById(User user) throws Exception {
		Query query = Query.query(Criteria.where("id").is(user.getUserId()));
		Update update = Update.update("phone_no", user.getPhone());
		update.set("login_pwd", user.getPwd());
		return template.updateMulti(query, update, User.class);
	}

	public List<User> findUser(User user) throws Exception {
		//Query query = Query.query(getCriteria(user));
		return template.find(getQuery(user), User.class);
	}

	public WriteResult deleteUser(User user) throws Exception {
		return template.remove(getQuery(user), User.class);
		
	}

	public List<User> findAll() throws Exception {
		return template.findAll(User.class);
	}
	
	private Query getQuery(User user) {
		return Query.query(getCriteria(user));
	}
	
	private Criteria getCriteria(User user) {
		Criteria criteria = new Criteria();
		if (user.getUserId() != null) {
			criteria.and("id").is(user.getUserId());
		}
		if (user.getName() != null) {
			criteria.and("user_name").regex(user.getName());
		}
		if (user.getLoginName() != null) {
			criteria.and("login_name").regex(user.getLoginName());
		}
		if (user.getPhone() != null) {
			criteria.and("phone_no").is(user.getPhone());
		}
		if (user.getPwd() != null) {
			criteria.and("login_pwd").is(user.getPwd());
		}
		if (user.getSex() != null) {
			criteria.and("sex").is(user.getSex());
		}
		return criteria;
	}

	public void saveAll(List<User> users) throws Exception {
		template.insert(users, "user");
		
	}

}
