package com.tengzhuo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tengzhuo.pojo.EasyUITreeNode;
import com.tengzhuo.service.ItemCatService;

/**
 *  商品管理 Controller 
 * <p>Title: ItemCatController.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月19日</p>
 * <p>version:1.0</p>
 */
@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody //返回json格式数据 注意@RequestParam(name="id",defaultValue="0")的意思
	public List<EasyUITreeNode> getCatList(@RequestParam(name="id",defaultValue="0") long parentId) {
		List<EasyUITreeNode> list = itemCatService.getCatList(parentId);
		return list;
	}
}
