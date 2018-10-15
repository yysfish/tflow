package tflow.com.yzs.flow.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import tflow.com.yzs.flow.common.BaseController;
import tflow.com.yzs.flow.common.ResponseModel;
import tflow.com.yzs.flow.entity.User;
import tflow.com.yzs.flow.service.UserService;
import tflow.com.yzs.flow.util.ExportExcel;

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
	
	@RequestMapping("/export")
	public ResponseModel export(HttpServletResponse response) throws Exception {
		try {
			User user = userService.getUser("1");
			user.setSex("0");
			List<User> list = new ArrayList<User>();
			list.add(user);
			new ExportExcel().
			createSheet("用户数据", "用户列表", User.class).addData(list).
			createSheet("userdata", User.class).addData(list).
			write(response, "test.xlsx");
		} catch (Exception e) {
			logger.error("导出用户数据异常:", e);
		}
		return null;
	}
	
	@RequestMapping("/save")
	public ResponseModel save(User user) throws Exception {
		logger.info(JSON.toJSONString(user));
		return getSuccessModel();
	}
}
