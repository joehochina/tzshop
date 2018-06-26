package com.tengzhuo.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tengzhuo.pojo.SearchResult;
import com.tengzhuo.search.service.SearchService;

/**
 * 商品搜索
 * <p>Title: SearchController.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月24日</p>
 * <p>version:1.0</p>
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@Value("${PAGE_ROWS}")
	private Integer PAGE_ROWS;

	
	@RequestMapping("/search")
	public String searchItemList(String keyword,@RequestParam(defaultValue="1") Integer page,Model model) throws Exception{
		//查询商品列表
		keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		SearchResult search = searchService.search(keyword, page, PAGE_ROWS);
		//String string = JsonUtils.objectToJson(search);
		//把结果传递给页面 
		model.addAttribute("query",keyword);
		model.addAttribute("totalPages",search.getTotalPages());
		model.addAttribute("page",page);
		model.addAttribute("recordCount",search.getRecordCount());
		model.addAttribute("itemList",search.getItemList());
		//返回逻辑视图
		return "search";
	}
}
