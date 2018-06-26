package com.tengzhuo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转 Controller
 * <p>Title: PageController.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月18日</p>
 * <p>version:1.0</p>
 */
@Controller
public class PageController {

	@RequestMapping("/")
	public String showIndex(){
		return "index";
	}
	
	/**
	 * 根据请求参数返回对应的JSP页面 
	 * @param page 请求URL参数 
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
		
		return page ;
		
	}
}
