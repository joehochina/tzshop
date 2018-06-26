package com.tengzhuo.sso.controller;


import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tengzhuo.sso.service.TokenService;
import com.tengzhuo.utils.JsonUtils;
import com.tengzhuo.utils.TZResult;

@Controller
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
/*	@RequestMapping(value="/user/token/{token}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE"application/json;charset=utf-8")
	@ResponseBody //callback 配合 e3mall.js jsonp 进行跨域请求
	public String getUserByToken(@PathVariable String token,String callback){
		TZResult result = tokenService.getUserByToken(token);
		//在响应结果之前，判断是否为jsonp请求
		if(StringUtils.isNotBlank(callback)){
			//把结果封装成一个json数据
			return callback+"("+JsonUtils.objectToJson(result)+");";
		}
		return JsonUtils.objectToJson(result);		
	}*/
	
	//SPRING 4.1之后的版本实现方法
	@RequestMapping(value="/user/token/{token}")
	@ResponseBody //callback 配合 e3mall.js jsonp 进行跨域请求
	public Object getUserByToken(@PathVariable String token,String callback){
		TZResult result = tokenService.getUserByToken(token);
		//在响应结果之前，判断是否为jsonp请求
		if(StringUtils.isNotBlank(callback)){
			//把结果封装成一个json数据
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;			
		}
		return result;		
	}
}
