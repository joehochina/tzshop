package com.tengzhuo.sso.service;

import com.tengzhuo.utils.TZResult;

public interface LoginService {

	TZResult userLogin(String username,String password);
	//参数是用户名和密码，返回值是
	
	//判断用户名密码是否正确、错误，返回登录失败
	//正确：生成Token
	//把用户信息写入token key:token value:用户信息
	
	//设置session信息
	//把token返回给表现层写入cookie
	
}
