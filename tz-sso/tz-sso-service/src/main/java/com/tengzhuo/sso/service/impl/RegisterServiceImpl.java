package com.tengzhuo.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.tengzhuo.mapper.TbUserMapper;
import com.tengzhuo.pojo.TbUser;
import com.tengzhuo.pojo.TbUserExample;
import com.tengzhuo.pojo.TbUserExample.Criteria;
import com.tengzhuo.sso.service.RegisterService;
import com.tengzhuo.utils.TZResult;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户注册服务 
 * <p>Title: RegisterServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年5月1日</p>
 * <p>version:1.0</p>
 */
@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Override
	public TZResult checkData(String param, int type) {
		
		//根据不同的type生成不同的查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//1：用户名 2：手机号 3：邮箱
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		} else if (type == 3) {
			criteria.andEmailEqualTo(param);
		} else {
			return TZResult.build(400, "数据类型错误");
		}
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		//判断结果中是否包含数据
		if (list != null && list.size()>0) {
			//如果有数据返回false
			return TZResult.ok(false);
		}
		//如果没有数据返回true
		return TZResult.ok(true);
	}

	@Override
	public TZResult register(TbUser user) {
		//数据有效性校验
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) 
				|| StringUtils.isBlank(user.getPhone())) {
			return TZResult.build(400, "用户数据不完整，注册失败");
		}
		//1：用户名 2：手机号 3：邮箱
		TZResult result = checkData(user.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return TZResult.build(400, "此用户名已经被占用");
		}
		result = checkData(user.getPhone(), 2);
		if (!(boolean)result.getData()) {
			return TZResult.build(400, "手机号已经被占用");
		}
		//补全pojo的属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//对密码进行md5加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		//把用户数据插入到数据库中
		userMapper.insert(user);
		//返回添加成功
		return TZResult.ok();
	}

}
