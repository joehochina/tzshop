package com.tengzhuo.search.mapper;

import java.util.List;

import com.tengzhuo.pojo.SearchItem;

public interface ItemMapper {
	List<SearchItem> getItemList();
	SearchItem getItemById(long itemId);
}
