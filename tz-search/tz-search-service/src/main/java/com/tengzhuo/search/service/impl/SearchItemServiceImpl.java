package com.tengzhuo.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tengzhuo.pojo.SearchItem;
import com.tengzhuo.search.mapper.ItemMapper;
import com.tengzhuo.search.service.SearchItemService;
import com.tengzhuo.utils.TZResult;
/**
 * 索引维护Service
 * <p>Title: SearchItemServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月24日</p>
 * <p>version:1.0</p>
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrServer solrServer;

	@Override
	public TZResult importAllItems() {
		try {
			//查询商品列表
			List<SearchItem> itemList = itemMapper.getItemList();
			//遍历商品列表
			for(SearchItem item:itemList){
				//创建文档对象
				SolrInputDocument document = new SolrInputDocument(); 
				//向文档对象中添加域
				document.addField("id", item.getId());
				document.addField("item_title", item.getTitle());
				document.addField("item_sell_point", item.getSell_point());
				document.addField("item_price", item.getPrice());
				document.addField("item_image", item.getImage());
				document.addField("item_category_name",item.getCategory_name());
				
				//把文档对象写入索引库
				solrServer.add(document);		
			}		
			//提交
			solrServer.commit();
			//返回导入成功		
			return TZResult.ok();		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return TZResult.build(500, "数据导入索引库时发生异常");
		}
	}

}
