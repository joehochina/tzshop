package com.tengzhuo.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tengzhuo.jedis.JedisClient;
import com.tengzhuo.mapper.TbItemDescMapper;
import com.tengzhuo.mapper.TbItemMapper;
import com.tengzhuo.pojo.EasyUIDataGridResult;
import com.tengzhuo.pojo.TbContent;
import com.tengzhuo.pojo.TbItem;
import com.tengzhuo.pojo.TbItemDesc;
import com.tengzhuo.pojo.TbItemExample;
import com.tengzhuo.pojo.TbItemExample.Criteria;
import com.tengzhuo.service.ItemService;
import com.tengzhuo.utils.IDUtils;
import com.tengzhuo.utils.JsonUtils;
import com.tengzhuo.utils.TZResult;
/**
 * 商品管理 service
 * <p>Title: ItemServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月17日</p>
 * <p>version:1.0</p>
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemDescMapper descMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private JedisClient jedisClient;
	
	//Resource注解是根据ID来注入的，不是名称
	@Resource
	private Destination topicDestination;
	
	@Value("${ITEM_REDIS_PRE}")
	private String ITEM_REDIS_PRE;

	@Value("${ITEM_CACHE_EXPIRE}")
	private Integer ITEM_CACHE_EXPIRE;
	
    @Override
	public TbItem getItemById(long id) {
		//先查询缓存，
		try {
			String json = jedisClient.get(ITEM_REDIS_PRE+":"+id+":BASE");
			if(StringUtils.isNotBlank(json)){
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	//根据主键查询
    	//TbItem item = tbItemMapper.selectByPrimaryKey(id);
		TbItemExample itemExample = new TbItemExample();
		Criteria createCriteria = itemExample.createCriteria();
		//设置查询条件
		createCriteria.andIdEqualTo(id);
		//查询并返回结果集
		List<TbItem> selectByExample = tbItemMapper.selectByExample(itemExample);
    	if (selectByExample!=null&&selectByExample.size()>0) {
    		//并把结果添加到缓存
    		try {
    			TbItem tbItem = selectByExample.get(0);
    			
    			jedisClient.set(ITEM_REDIS_PRE+":"+id+":BASE",JsonUtils.objectToJson(tbItem));
    			//设置过期时间
    			jedisClient.expire(ITEM_REDIS_PRE+":"+id+":BASE", ITEM_CACHE_EXPIRE);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
			return selectByExample.get(0);
		}
    	return null;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> tbItems = tbItemMapper.selectByExample(example); 
		//创建返回值对象
		EasyUIDataGridResult easyUIDataGridResult=new EasyUIDataGridResult();
		easyUIDataGridResult.setRows(tbItems);
		//取得分页总数
	    PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
		easyUIDataGridResult.setTotal(pageInfo.getTotal());
		return easyUIDataGridResult;
	}

	@Override
	public TZResult addItem(TbItem item, String desc) {
		// 先生成商品ID
		final long genItemId = IDUtils.genItemId();
		//补全item的数据,其他数据从页面带过来
		item.setId(genItemId);		
		item.setStatus((byte) 1); //'商品状态，1-正常，2-下架，3-删除'
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//向商品表插入数据
		tbItemMapper.insert(item);
		
		//创建商品描述表pojo
		TbItemDesc tbItemDesc=new TbItemDesc();
		//补全属性
		tbItemDesc.setItemId(genItemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		//向商品描述表插入数据
		descMapper.insert(tbItemDesc);
		//发送商品添加消息，此处需要注意事务提交是否完成，可以在消息接收方在接收消息前Thread.sleep(1000)
		//或者在Controller层处理消息发送
		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// 将商品ID通过消息发送出去
				TextMessage textMessage = session.createTextMessage(genItemId+"");
				return textMessage;
			}
		});
		
		//返回成功
		return TZResult.ok();
	}

	/**
	 * 根据商品ID 查找商品描述
	 */
	@Override
	public TbItemDesc getItemDescById(long itemId) {
		//先查询缓存，
		try {
			String json = jedisClient.get(ITEM_REDIS_PRE+":"+itemId+":DESC");
			if(StringUtils.isNotBlank(json)){
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return itemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemDesc itemDesc = descMapper.selectByPrimaryKey(itemId);
		
		//并把结果添加到缓存
		try {
			jedisClient.set(ITEM_REDIS_PRE+":"+itemId+":DESC",JsonUtils.objectToJson(itemDesc));
			//设置过期时间
			jedisClient.expire(ITEM_REDIS_PRE+":"+itemId+":DESC", ITEM_CACHE_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

}
