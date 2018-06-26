package com.tengzhuo.solrJ;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.manipulation.Sortable;

public class TestSolrJ {
	
	@Test
	public void addDocument() throws Exception{
		String baseURL="http://192.168.1.108:8080/solr/collection1";
		//创建 一个 SolrServer对象 ，创建 一个连接。参数 solr服务的 url
		SolrServer solrServer = new HttpSolrServer(baseURL);
		//创建 一个文档对象 SolrInputDocument
		SolrInputDocument document = new SolrInputDocument();		
		//向文档对象中添加域。文档中必须包含一个id域，所有的域的名称必须在schema.xml中定义
		document.addField("id", "doc01");
		document.addField("item_title", "item_title_01");
		document.addField("item_price", 1000);
		
		//把文档写入文档库
		solrServer.add(document);
		
		//提交 
		solrServer.commit();
	}
	
	@Test
	public void delDocument() throws Exception{
		String baseURL="http://192.168.1.108:8080/solr/collection1";
		//创建 一个 SolrServer对象 ，创建 一个连接。参数 solr服务的 url
		SolrServer solrServer = new HttpSolrServer(baseURL);
		solrServer.deleteByQuery("id:doc01");
		solrServer.commit();
	}

	@Test
	public void queryIndex() throws Exception{
		String baseURL="http://192.168.1.108:8080/solr/collection1";
		//创建 一个 SolrServer对象 ，创建 一个连接。参数 solr服务的 url
		SolrServer solrServer = new HttpSolrServer(baseURL);
				
		//创建一个SolrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		//设置查询条件
		solrQuery.set("q", "*:*");
		//执行查询，得到QueryResponse对象
		QueryResponse response = solrServer.query(solrQuery);
		//取文档列表，取查询结果的总记录数
		SolrDocumentList results = response.getResults();
		System.out.println("查询结果总记录数为："+results.getNumFound());
		//遍历文档列表，从文档中取域的内容
		for (SolrDocument document:results) {
			System.out.println(document.get("id"));
			System.out.println(document.get("item_title"));
			System.out.println(document.get("item_sell_point"));
			System.out.println(document.get("item_price"));
			System.out.println(document.get("item_image"));
			System.out.println(document.get("item_category_name"));
			
		}
	}
	
	@Test
	public void queryIndexFuza() throws Exception{
		String baseURL="http://192.168.1.108:8080/solr/collection1";
		//创建 一个 SolrServer对象 ，创建 一个连接。参数 solr服务的 url
		SolrServer solrServer = new HttpSolrServer(baseURL);
				
		//创建一个SolrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		//设置查询条件
		solrQuery.setQuery("手机");
		solrQuery.setStart(0);
		solrQuery.setRows(20);
		solrQuery.set("df", "item_title");
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em>");
		solrQuery.setHighlightSimplePost("</em>");
		//执行查询，得到QueryResponse对象
		QueryResponse response = solrServer.query(solrQuery);
		//取文档列表，取查询结果的总记录数
		SolrDocumentList results = response.getResults();
		System.out.println("查询结果总记录数为："+results.getNumFound());
		//遍历文档列表，从文档中取域的内容
		for (SolrDocument document:results) {
			System.out.println(document.get("id"));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(document.get("id")).get("item_title");
			String title="";
			if(list!=null && list.size()>0){
				 title = list.get(0);
			}else{
				title= (String) document.get("item_title");
			}
			System.out.println(title);
			System.out.println(document.get("item_sell_point"));
			System.out.println(document.get("item_price"));
			System.out.println(document.get("item_image"));
			System.out.println(document.get("item_category_name"));
			
		}
	}
}
