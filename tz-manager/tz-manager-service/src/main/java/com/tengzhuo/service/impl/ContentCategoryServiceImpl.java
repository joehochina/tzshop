package com.tengzhuo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tengzhuo.mapper.TbContentCategoryMapper;
import com.tengzhuo.pojo.EasyUITreeNode;
import com.tengzhuo.pojo.TbContentCategory;
import com.tengzhuo.pojo.TbContentCategoryExample;
import com.tengzhuo.pojo.TbContentCategoryExample.Criteria;
import com.tengzhuo.service.ContentCategoryService;
import com.tengzhuo.utils.TZResult;
/**
 * 内容分类管理Service
 * <p>Title: ContentCategoryServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月20日</p>
 * <p>version:1.0</p>
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
		
	@Override
	public List<EasyUITreeNode> getContentCatList(Long parentId) {
		//根据parentId查询子节点列表		
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andParentIdEqualTo(parentId);
		//返回查询结果
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//转换成EasyUITreeNode
		List<EasyUITreeNode> treeNodes=new ArrayList<>();
		for(TbContentCategory contentCategory:list){
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(contentCategory.getId());
			treeNode.setText(contentCategory.getName());
			treeNode.setState(contentCategory.getIsParent()?"closed":"open");
			//添加到列表
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}
	@Override
	public TZResult addContentCategory(long parentId, String name) {
		//创建一个tb_content_category对应的pojo
		TbContentCategory contentCategory = new TbContentCategory();
		//设置pojo的属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		//可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		//默认排序就是1
		contentCategory.setSortOrder(1);
		//新添加的节点一定是叶子节点,'该类目是否为父类目，1为true，0为false',
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());	
		//插入到数据库
		contentCategoryMapper.insert(contentCategory);
		//判断父节点isparent属性,如果不为true,改为true
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			//更新isparent 到数据库
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		//返回结果
		return TZResult.ok(contentCategory);
	}


}
