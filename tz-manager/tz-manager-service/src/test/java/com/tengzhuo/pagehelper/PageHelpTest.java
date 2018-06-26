package com.tengzhuo.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tengzhuo.mapper.TbItemMapper;
import com.tengzhuo.pojo.TbItem;
import com.tengzhuo.pojo.TbItemExample;

public class PageHelpTest {
	
	@Test
	public void testPageHelper() throws Exception{
		//初始化spring容器
	    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		//从容器中获得Mapper代理对象
	    TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		//执行语句之前设置分页信息使用PageHelper的startPage方法
	    PageHelper.startPage(1, 20);
		//执行查询
	    TbItemExample example = new TbItemExample();
		List<TbItem> tbItems = itemMapper.selectByExample(example);
		//取分页信息，PageInfo 1、总记录数 2、总页数 。 当前页码 
		PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.getPages());
		
		
	}

}
