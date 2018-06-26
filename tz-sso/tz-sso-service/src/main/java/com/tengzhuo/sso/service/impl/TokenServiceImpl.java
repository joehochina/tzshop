package com.tengzhuo.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;

import com.tengzhuo.jedis.JedisClient;
import com.tengzhuo.pojo.TbUser;
import com.tengzhuo.sso.service.TokenService;
import com.tengzhuo.utils.JsonUtils;
import com.tengzhuo.utils.TZResult;

/**
 * 从Redis缓存中获取用户登录信息
 * <p>Title: TokenServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年5月2日</p>
 * <p>version:1.0</p>
 */
@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${USER_SESSION_PRE}")
	private String USER_SESSION_PRE;
	
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public TZResult getUserByToken(String token) {
		//取用户信息
		String json = jedisClient.get(USER_SESSION_PRE+":"+token);
		
		//如果取不到用户信息，则返回用户已经过期
		if(StringUtils.isBlank(json)){
			return TZResult.build(201, "用户登录已经过期");
		}
		//取到用去信息，更新session的过期时间
		TbUser userData = JsonUtils.jsonToPojo(json, TbUser.class);
		//更新过期时间
		jedisClient.expire(USER_SESSION_PRE+":"+token, SESSION_EXPIRE);
		//返回用户信息
		
		return TZResult.ok(userData);
	}

}
