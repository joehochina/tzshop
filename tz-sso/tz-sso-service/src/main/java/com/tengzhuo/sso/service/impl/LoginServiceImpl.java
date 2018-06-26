package com.tengzhuo.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.tengzhuo.jedis.JedisClient;
import com.tengzhuo.mapper.TbUserMapper;
import com.tengzhuo.pojo.TbUser;
import com.tengzhuo.pojo.TbUserExample;
import com.tengzhuo.pojo.TbUserExample.Criteria;
import com.tengzhuo.sso.service.LoginService;
import com.tengzhuo.utils.JsonUtils;
import com.tengzhuo.utils.TZResult;
/**
 * 用户单点登录业务逻辑
 * <p>Title: LoginServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年5月2日</p>
 * <p>version:1.0</p>
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${USER_SESSION_PRE}")	
	private String USER_SESSION_PRE;
	
	@Value("${SESSION_EXPIRE}")	
	private Integer SESSION_EXPIRE;
	
	@Override
	public TZResult userLogin(String username, String password) {
		//参数是用户名和密码
		TbUserExample example= new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);		
		List<TbUser> list = userMapper.selectByExample(example);
		//判断用户名密码是否正确、错误，返回登录失败
		if(list==null&&list.size()==0){
			return TZResult.build(400, "用户名或密码错误");
		}
		TbUser tbUser = list.get(0);
		if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(tbUser.getPassword())){
			return TZResult.build(400, "用户名或密码错误");
		}
		//正确：生成Token
		String token = UUID.randomUUID().toString();
		//把用户信息写入token key:token value:用户信息
		//不要将用户密码传给表现层，这样不安全
		tbUser.setPassword(null);	
		//设置session信息
		jedisClient.set(USER_SESSION_PRE+":"+token, JsonUtils.objectToJson(tbUser));
		
		jedisClient.expire(USER_SESSION_PRE+":"+token, SESSION_EXPIRE);
		//把token返回给表现层写入cookie
		return TZResult.ok(token);
	}

}
