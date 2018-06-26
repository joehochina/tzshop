package com.tengzhuo.portal.controller;

import java.util.List;

import javax.jws.WebParam.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tengzhuo.pojo.TbContent;
import com.tengzhuo.service.ContentService;

/**
 * 首页展示Controller
 * <p>Title: IndexController.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月20日</p>
 * <p>version:1.0</p>
 */
@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;
	
	@Value("${CONTENT_LUNBO_ID}")
	private long CONTENT_LUNBO_ID;
	
	@RequestMapping("/index")
	public String showIndex(Model model){
		//查询内容列表 ,内容轮播图 ，写死在 配置文件 中 分类ID
		List<TbContent> ad1List = contentService.getContentByCid(CONTENT_LUNBO_ID);
		//将广告轮播LIST通过mode传回给页面
		model.addAttribute("ad1List",ad1List);
		return "index";
	}
}
