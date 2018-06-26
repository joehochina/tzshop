package com.tengzhuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 导入商品数据到索引库
 * <p>Title: SearchItemController.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月24日</p>
 * <p>version:1.0</p>
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tengzhuo.search.service.SearchItemService;
import com.tengzhuo.utils.TZResult;
@Controller
public class SearchItemController {
	
	@Autowired
	private SearchItemService searchItemService;

	@RequestMapping("/index/item/import")
	@ResponseBody
	public TZResult importItemList(){
		TZResult importAllItems = searchItemService.importAllItems();
		return importAllItems;
	}

}
