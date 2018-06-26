package com.tengzhuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tengzhuo.pojo.EasyUIDataGridResult;
import com.tengzhuo.pojo.TbContent;
import com.tengzhuo.service.ContentService;
import com.tengzhuo.utils.TZResult;

/**
 * 内容管理
 * <p>Title: ContentController.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月22日</p>
 * <p>version:1.0</p>
 */
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Integer page, Integer rows,long categoryId) {
		//取得商品信息
		EasyUIDataGridResult result = contentService.getContentList(page, rows, categoryId);
		return result;

	}
	
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public TZResult addContent(TbContent content){
		TZResult result = contentService.addContent(content);
		return result;
	}

}
