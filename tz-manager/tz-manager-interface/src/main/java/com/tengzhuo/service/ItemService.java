package com.tengzhuo.service;

import com.tengzhuo.pojo.EasyUIDataGridResult;
import com.tengzhuo.pojo.TbItem;
import com.tengzhuo.pojo.TbItemDesc;
import com.tengzhuo.utils.TZResult;

/**
 * 商品服务接口
 * <p>Title: ItemService.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月18日</p>
 * <p>version:1.0</p>
 */
public interface ItemService {
	TbItem getItemById(long id);
	EasyUIDataGridResult getItemList(int page,int rows);
	TZResult addItem(TbItem item,String desc);
	TbItemDesc getItemDescById(long itemId);

}
