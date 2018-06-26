package com.tengzhuo.cart.service;

import java.util.List;

import com.tengzhuo.pojo.TbItem;
import com.tengzhuo.utils.TZResult;

public interface CartService {

	TZResult addCart(long userId, long itemId, int num);
	TZResult mergeCart(long userId, List<TbItem> itemList);
	List<TbItem> getCartList(long userId);
	TZResult updateCartNum(long userId, long itemId, int num);
	TZResult deleteCartItem(long userId, long itemId);
	TZResult clearCartItem(long userId);
}
