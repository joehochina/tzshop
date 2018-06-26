package com.tengzhuo.search.service;

import com.tengzhuo.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String keyword,int page,int rows) throws Exception;

}
