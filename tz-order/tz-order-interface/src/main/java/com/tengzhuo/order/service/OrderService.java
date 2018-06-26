package com.tengzhuo.order.service;

import com.tengzhuo.order.pojo.OrderInfo;
import com.tengzhuo.utils.TZResult;

public interface OrderService {

	TZResult createOrder(OrderInfo orderInfo);
}
