package com.tengzhuo.solrJ;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {
	
	@Test
	public void testAddDocument() throws Exception{
		String zkHost="192.168.1.108:2181,192.168.1.108:2182,192.168.1.108:2183";
		//创建一个连接，cloudSolrServer创建
		CloudSolrServer solrServer = new CloudSolrServer(zkHost);
		//设置 zkHost:zookeeper的地址列表
		
		//设置一个defaultConection
		solrServer.setDefaultCollection("collection2");
		//设置一个文档对象
		SolrInputDocument inputDocument = new SolrInputDocument();
		//向文档中添加域
		inputDocument.setField("id", "solrcloud01");
		inputDocument.setField("item_title", "测试商品01");
		inputDocument.setField("item_price", 123);
		//把文件写入索引库
		solrServer.add(inputDocument);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void testQueryDocument() throws Exception{
		String zkHost="192.168.1.108:2181,192.168.1.108:2182,192.168.1.108:2183";
		//创建一个连接，cloudSolrServer创建
		CloudSolrServer solrServer = new CloudSolrServer(zkHost);
		//设置一个defaultConection
		solrServer.setDefaultCollection("collection2");
		
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList results = queryResponse.getResults();
		System.out.println("总记录数为："+results.getNumFound());
		for(SolrDocument document:results){
			System.out.println(document.get("id"));
			System.out.println(document.get("title"));
			System.out.println(document.get("item_title"));
			System.out.println(document.get("item_price"));
		}
		
	}
	


}
