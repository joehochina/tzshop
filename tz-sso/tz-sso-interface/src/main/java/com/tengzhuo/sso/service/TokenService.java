package com.tengzhuo.sso.service;

import com.tengzhuo.pojo.TbUser;
import com.tengzhuo.utils.TZResult;

public interface TokenService {

	//根据token从redis缓存中查询用户信息
	//用TZRsult包装一下 TbUser
	TZResult getUserByToken(String token);
}
