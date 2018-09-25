package tflow.com.yzs.flow.entity;

import java.io.Serializable;

import tflow.com.yzs.flow.common.annotation.ExcelField;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3070962187915675773L;
	/**
	 * 用户ID
	 */
	@ExcelField(title="用户ID", sort=0)
	private String userId;
	/**
	 * 用户姓名
	 */
	@ExcelField(title="用户姓名", sort=1)
	private String name;
	/**
	 * 用户性别 0-男 1-女
	 */
	@ExcelField(title="性别", sort=2, jsonStr="{\"0\":\"男\",\"1\":\"女\"}")
	private String sex;
	/**
	 * 手机号
	 */
	@ExcelField(title="用户手机", sort=3)
	private String phone;
	/**
	 * 登陆名
	 */
	@ExcelField(title="用户登录名", sort=4)
	private String loginName;
	/**
	 * 登陆密码
	 */
	private String pwd;
	/**
	 * 用户类型
	 */
	private String userType;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
