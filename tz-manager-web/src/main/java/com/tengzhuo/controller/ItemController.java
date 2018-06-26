package com.tengzhuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tengzhuo.pojo.EasyUIDataGridResult;
import com.tengzhuo.pojo.TbItem;
import com.tengzhuo.service.ItemService;
import com.tengzhuo.utils.TZResult;

/**
 * 商品管理Controller
 * <p>
 * Title: ItemConroller.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.tengzhuo.com
 * </p>
 * <p>
 * author:joeho 2018年4月17日
 * </p>
 * <p>
 * version:1.0
 * </p>
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 根据ID查询商品
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}

	/**
	 * 商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		//取得商品信息
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;

	}
	
	/**
	 * 商品添加
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public TZResult addItem(TbItem item, String desc) {
		TZResult result = itemService.addItem(item, desc);
		return result;
	}
}
