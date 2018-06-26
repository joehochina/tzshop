package com.tengzhuo.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.tengzhuo.pojo.SearchItem;
import com.tengzhuo.search.mapper.ItemMapper;
/**
 * 监听商品添加消息，接收到消息后，将对应的商品信息同步到索引库
 * 解决了索引库不需要每次重新生成的问题
 * <p>Title: ItemAddMessageListener.java</p>
 * <p>Description: </p>
 * <p>Company: www.tengzhuo.com</p> 
 * <p>author:joeho 2018年4月27日</p>
 * <p>version:1.0</p>
 */
public class ItemAddMessageListener implements MessageListener {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	@Override
	public void onMessage(Message message) {
		try {
			// 从消息中取得商品ID
			TextMessage textMessage=(TextMessage)message;
			String text = textMessage.getText();
			long itemId=Long.parseLong(text);
			// 等待消息提供方事务提交
			Thread.sleep(1000);
			// 根据商品 ID查询商品 信息 
			SearchItem item = itemMapper.getItemById(itemId);
			// 创建 一个 文档对象
			SolrInputDocument solrInputDocument = new SolrInputDocument();
	
			//向文档对象中添加域
			solrInputDocument.addField("id", item.getId());
			solrInputDocument.addField("item_title", item.getTitle());
			solrInputDocument.addField("item_sell_point", item.getSell_point());
			solrInputDocument.addField("item_price", item.getPrice());
			solrInputDocument.addField("item_image", item.getImage());
			solrInputDocument.addField("item_category_name",item.getCategory_name());
			
			// 把文档写入索引库
			solrServer.add(solrInputDocument);
			// 提交 
			solrServer.commit();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
