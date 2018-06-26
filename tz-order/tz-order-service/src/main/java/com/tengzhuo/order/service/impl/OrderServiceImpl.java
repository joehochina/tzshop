package com.tengzhuo.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tengzhuo.jedis.JedisClient;
import com.tengzhuo.mapper.TbOrderItemMapper;
import com.tengzhuo.mapper.TbOrderMapper;
import com.tengzhuo.mapper.TbOrderShippingMapper;
import com.tengzhuo.order.pojo.OrderInfo;
import com.tengzhuo.order.service.OrderService;
import com.tengzhuo.pojo.TbOrderItem;
import com.tengzhuo.pojo.TbOrderShipping;
import com.tengzhuo.utils.TZResult;
/**
 * 订单处理中心
 * <p>Title: OrderServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年5月5日</p>
 * <p>version:1.0</p>
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private TbOrderMapper orderMapper;
	
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	
	@Value("${ORDER_ID_START}")
	private String ORDER_ID_START;
	
	@Value("${ORDER_DETAIL_ID_GEN_KEY}")
	private String ORDER_DETAIL_ID_GEN_KEY;
	
	@Override
	public TZResult createOrder(OrderInfo orderInfo) {
		// 生成订单号，使用redis的incr生成
		if(!jedisClient.exists(ORDER_ID_GEN_KEY)){
				jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_START);
		}
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		//补全orderInfo的属性
		orderInfo.setOrderId(orderId);
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		//插入订单表
		orderMapper.insert(orderInfo);
		
		//向订单明细表插入数据
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			//生成明细id
			String oId = jedisClient.incr(ORDER_DETAIL_ID_GEN_KEY).toString();
			//补全pojo的属性
			tbOrderItem.setId(oId);
			tbOrderItem.setOrderId(orderId);
			//向明细表插入数据
			orderItemMapper.insert(tbOrderItem);
		}
		
		//向订单物流表插入数据
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingMapper.insert(orderShipping);
		
		//返回TZResult 包含订单号
		return TZResult.ok(orderId);
	}

}
