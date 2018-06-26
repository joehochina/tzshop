package com.tengzhuo.item.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tengzhuo.item.pojo.Item;
import com.tengzhuo.pojo.TbItem;
import com.tengzhuo.pojo.TbItemDesc;
import com.tengzhuo.service.ItemService;

/**
 * 商品详情展示
 * <p>Title: ItemController.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月28日</p>
 * <p>version:1.0</p>
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemDetail(@PathVariable Long itemId,Model model){
		//调用服务取商品基本信息
		TbItem tbItem = itemService.getItemById(itemId);
		//取一张图片
		String image = tbItem.getImage();
		if (StringUtils.isNotBlank(image)) {
			tbItem.setImage(image.split(",")[0]);
		}
		//商品描述信息
		TbItemDesc itemDesc = itemService.getItemDescById(itemId);
		//将信息传递给页面
		Item item = new Item(tbItem);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		//返回逻辑视图
		return "item";
	}

}
