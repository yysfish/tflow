package tflow.com.yzs.flow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tflow.com.yzs.flow.common.BaseController;
import tflow.com.yzs.flow.common.ResponseModel;
import tflow.com.yzs.flow.entity.User;
import tflow.com.yzs.flow.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

	@Autowired
	private UserService userService;
	
	@RequestMapping("/getUser")
	public ResponseModel getUser(@RequestParam(required = true) String id) throws Exception {
		try {
			
			User user = userService.getUser(id);
			return getSuccessModel(user);
		} catch (Exception e) {
			logger.error("查询用户信息出现异常:", e);
			return getErrorModel(e);
		}
		
	}
}
