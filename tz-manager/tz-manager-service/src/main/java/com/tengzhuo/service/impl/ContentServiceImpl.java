package com.tengzhuo.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ExtendedModelMap;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tengzhuo.jedis.JedisClient;
import com.tengzhuo.mapper.TbContentMapper;
import com.tengzhuo.pojo.EasyUIDataGridResult;
import com.tengzhuo.pojo.TbContent;
import com.tengzhuo.pojo.TbContentExample;
import com.tengzhuo.pojo.TbContentExample.Criteria;
import com.tengzhuo.service.ContentService;
import com.tengzhuo.utils.JsonUtils;
import com.tengzhuo.utils.TZResult;


@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	/**
	 * 增删改的时候都应该同步缓存
	 * 此时删除原来缓存数据
	 */
	@Override
	public TZResult addContent(TbContent content) {
		
		content.setCreated(new Date());
		content.setUpdated(new Date());	
		
		//插入到数据库
		contentMapper.insert(content);
		
		//缓存同步 
		try{
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		//返回结果
		return TZResult.ok();
	}

	@Override
	public EasyUIDataGridResult getContentList(int page, int rows,long categoryId) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example); 
		//创建返回值对象
		EasyUIDataGridResult easyUIDataGridResult=new EasyUIDataGridResult();
		easyUIDataGridResult.setRows(contents);
		//取得分页总数
	    PageInfo<TbContent> pageInfo = new PageInfo<>(contents);
		easyUIDataGridResult.setTotal(pageInfo.getTotal());
		return easyUIDataGridResult;
	}

	/**
	 * 根据内容分类id查询内容列表
	 */
	@Override
	public List<TbContent> getContentByCid(long categoryId) {
		//先查询缓存，
		try {
			String json = jedisClient.hget(CONTENT_LIST, categoryId+"");
			if(StringUtils.isNotBlank(json)){
				List<TbContent> contentList = JsonUtils.jsonToList(json, TbContent.class);
				return contentList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//如果缓存中有，就直接返回缓存，如果没有则查询数据库
		//执行查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> contentList = contentMapper.selectByExampleWithBLOBs(example); 
		//并把结果添加到缓存
		try {
			jedisClient.hset(CONTENT_LIST, categoryId+"", JsonUtils.objectToJson(contentList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contentList;
		
	}


}
