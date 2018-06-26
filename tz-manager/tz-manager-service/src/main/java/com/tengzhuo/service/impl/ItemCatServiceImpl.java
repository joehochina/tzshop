package com.tengzhuo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tengzhuo.mapper.TbItemCatMapper;
import com.tengzhuo.pojo.EasyUITreeNode;
import com.tengzhuo.pojo.TbItemCat;
import com.tengzhuo.pojo.TbItemCatExample;
import com.tengzhuo.pojo.TbItemCatExample.Criteria;
import com.tengzhuo.service.ItemCatService;

/**
 * 商品分类管理  item-add.jsp
 * <p>Title: ItemCatServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月19日</p>
 * <p>version:1.0</p>
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getCatList(long parentId) {
		//根据parentId查询商品子节点
		TbItemCatExample example=new TbItemCatExample();
		Criteria createCriteria = example.createCriteria();
		//设置查询条件
		createCriteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//创建返回结果的list
		List<EasyUITreeNode> result = new ArrayList<>();
		for(TbItemCat tbItemCat:list){
			EasyUITreeNode treeNode=new EasyUITreeNode();
			treeNode.setId(tbItemCat.getId());
			treeNode.setText(tbItemCat.getName());
			treeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			//添加到结果列表
			result.add(treeNode);			
		}
		return result;
	}

}
