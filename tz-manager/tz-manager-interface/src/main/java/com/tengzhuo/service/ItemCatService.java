package com.tengzhuo.service;

import java.util.List;

import com.tengzhuo.pojo.EasyUITreeNode;

public interface ItemCatService {
	public List<EasyUITreeNode> getCatList(long parentId);
	
}
