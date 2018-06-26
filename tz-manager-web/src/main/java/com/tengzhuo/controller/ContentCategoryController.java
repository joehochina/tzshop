package com.tengzhuo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tengzhuo.pojo.EasyUITreeNode;
import com.tengzhuo.service.ContentCategoryService;
import com.tengzhuo.utils.TZResult;

/**
 * 内容分类管理 Controller
 * <p>Title: ContentCategoryController.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月20日</p>
 * <p>version:1.0</p>
 */
@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService categoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(name="id",defaultValue="0") Long parentId) {
		
		List<EasyUITreeNode> contentCatList = categoryService.getContentCatList(parentId);
		return contentCatList;
	}
		
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public TZResult createContentCategory(long parentId,String name){
		TZResult result = categoryService.addContentCategory(parentId, name);
		return result;
	}

}
