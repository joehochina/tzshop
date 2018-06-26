package com.tengzhuo.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tengzhuo.pojo.TbUser;
import com.tengzhuo.sso.service.TokenService;
import com.tengzhuo.utils.CookieUtils;
import com.tengzhuo.utils.TZResult;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 前处理，如果返回true，放行  false拦截 		
		//先从cookie之中取token
		String token = CookieUtils.getCookieValue(request, "tz-token");
		//如果没有token，未登录状态，直接放行
		if(StringUtils.isBlank(token)){
			return true;
		}
		//取到token，需要调用sso系统服务，根据token取用户信息
		TZResult tzResult = tokenService.getUserByToken(token);
		//没有取到用户信息，登录过期，直接放行
		if(tzResult.getStatus()!=200) return true;
		//取到用户信息，登录状态
		TbUser user = (TbUser) tzResult.getData();
		//把用户信息放到request中，值需要在controller中判断request中是否包含user信息，放行
		request.setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// handler 执行之后 在 返回modelAndView之前

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 返回modelAndView之后，可以在此处理异常

	}

}
