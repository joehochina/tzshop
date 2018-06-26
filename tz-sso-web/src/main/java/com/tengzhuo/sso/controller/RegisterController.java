package com.tengzhuo.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tengzhuo.pojo.TbUser;
import com.tengzhuo.sso.service.RegisterService;
import com.tengzhuo.utils.TZResult;

/**
 * 注册功能Controller
 * <p>Title: RegisterController.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月30日</p>
 * <p>version:1.0</p>
 */
@Controller
public class RegisterController {

	@Autowired
	private RegisterService registService;
	@RequestMapping("/page/register")
	public String showRegister(){
		return "register";
	}
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TZResult checkData(@PathVariable String param,@PathVariable Integer type){
		TZResult result = registService.checkData(param, type);
		return result;
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public TZResult register(TbUser user){
		TZResult register = registService.register(user);
		return register;
	}
}
