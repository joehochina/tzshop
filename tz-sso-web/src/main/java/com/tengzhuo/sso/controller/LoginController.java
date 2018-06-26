package com.tengzhuo.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tengzhuo.sso.service.LoginService;
import com.tengzhuo.utils.CookieUtils;
import com.tengzhuo.utils.TZResult;
/**
 * 登录处理页面
 * <p>Title: LoginController.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年5月2日</p>
 * <p>version:1.0</p>
 */
@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Value("${USER_SESSION_PRE}")
	private String USER_SESSION_PRE;
	
	@RequestMapping("/page/login")
	public String showLogin(String redirect,Model model){
		model.addAttribute("redirect", redirect);
		return "login";
	}


	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public TZResult login(String username,String password,HttpServletRequest request,HttpServletResponse response){
		TZResult result = loginService.userLogin(username, password);
		
		//先判断登录是否成功，如果登录成功
		if(result.getStatus()==200){
			//把token写入cookie
			String token = result.getData().toString();
			CookieUtils.setCookie(request, response, USER_SESSION_PRE, token);
		}
		return result;
	}
}
