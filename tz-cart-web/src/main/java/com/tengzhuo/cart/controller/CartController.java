package com.tengzhuo.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tengzhuo.cart.service.CartService;
import com.tengzhuo.pojo.TbItem;
import com.tengzhuo.pojo.TbUser;
import com.tengzhuo.service.ItemService;
import com.tengzhuo.utils.CookieUtils;
import com.tengzhuo.utils.JsonUtils;
import com.tengzhuo.utils.TZResult;

@Controller
public class CartController {

	@Autowired
	private ItemService itemService;
	
	@Value("${COOKIE_CART_EXPIRE}")
	private Integer COOKIE_CART_EXPIRE;
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId,@RequestParam(defaultValue="1")Integer num,
			HttpServletRequest request, HttpServletResponse response){
		//先判断用户是否登录
		TbUser user = (TbUser) request.getAttribute("user");
		//登录 将购物车写入redis
		if(user!=null){
			//保存到服务端
			cartService.addCart(user.getId(), itemId, num);
			//返回逻辑视图
			return "cartSuccess";
		}
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		boolean flag=false;
		//判断商品在商品列表中是否存在
		for (TbItem tbItem : cartList) {
			//比较，注意包装类型
			if(tbItem.getId()==itemId.longValue()){
				flag=true;
				//如果存在数量相加
				tbItem.setNum(tbItem.getNum()+num);
				break;
			}
		}	
		//如果不存在，
		if(!flag){
			//根据商品id查询商品信息，得到一个TbItem对象
			TbItem tbItem = itemService.getItemById(itemId);
			//设置商品数量
			tbItem.setNum(num);
			//取第一张图片
			String image = tbItem.getImage();
			if(StringUtils.isNotBlank(image)){
				tbItem.setImage(image.split(",")[0]);
			}
			//把商品添加到商品列表
			cartList.add(tbItem);			
		}
		//写入cookie
		CookieUtils.setCookie(request, response, "cart",
				JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回添加成功页面		
		return "cartSuccess";
	}
	/**
	 * 获取购物车列表
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartListFromCookie(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request, "cart", true);
		//判断json是否为空
		if(StringUtils.isBlank(json)){
			return new ArrayList<>();
		}
		//把 json转换成商品列表
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}
	
	/**
	 * 展示购物车列表页面
	 */
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request,HttpServletResponse response){
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		
		//判断用户是否为登录状态
		//如果是登录状态
		//先判断用户是否登录
		TbUser user = (TbUser) request.getAttribute("user");
		//登录 将购物车写入redis
		if(user!=null){
			//如果不为空，把cookie中的购物车商品和服务端的购物车商品合并
			cartService.mergeCart(user.getId(), cartList);
			//把cookie中的购物车删除
			CookieUtils.deleteCookie(request, response, "cart");
			//从服务端取购物车列表
			cartList = cartService.getCartList(user.getId());		
		}
	
		//把列表传递给页面
		request.setAttribute("cartList",cartList);
		//返回逻辑视图
		return "cart";
	}
	
	/**
	 * 更新购物车商品数量
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TZResult updateCartNum(@PathVariable Long itemId, @PathVariable Integer num
			, HttpServletRequest request ,HttpServletResponse response) {
		//先判断用户是否登录
		TbUser user = (TbUser) request.getAttribute("user");
		//登录 将购物车写入redis
		if(user!=null){
			//如果不为空，把cookie中的购物车商品和服务端的购物车商品合并
			cartService.updateCartNum(user.getId(), itemId, num);
			return TZResult.ok();
		}
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		//遍历商品列表，找到对应的商品
		for (TbItem tbItem : cartList) {
			if(tbItem.getId()==itemId.longValue()){
				//更新数量
				tbItem.setNum(num);
				break;
			}
		}
		//把购物车列表写回到cookie	
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回成功
		return TZResult.ok();		
	}
	
	/**
	 * 删除购物车商品
	 * 删除后跳转到购物车列表
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartList(@PathVariable Long itemId,HttpServletRequest request ,HttpServletResponse response) {
		//先判断用户是否登录
		TbUser user = (TbUser) request.getAttribute("user");
		//登录 将购物车写入redis
		if(user!=null){
			//如果不为空，把cookie中的购物车商品删除 
			cartService.deleteCartItem(user.getId(), itemId);
			//跳转到逻辑视图		
			return "redirect:/cart/cart.html";
		}
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		//遍历商品列表，找到对应的商品
		for (TbItem tbItem : cartList) {
			if(tbItem.getId()==itemId.longValue()){
				//删除商品
				cartList.remove(tbItem);
				break;
			}
		}
		//把购物车列表写回到cookie	
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//跳转到逻辑视图		
		return "redirect:/cart/cart.html";
	}

}
