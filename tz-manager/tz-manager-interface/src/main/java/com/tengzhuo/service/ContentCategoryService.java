package com.tengzhuo.service;

import java.util.List;

import com.tengzhuo.pojo.EasyUITreeNode;
import com.tengzhuo.utils.TZResult;

public interface ContentCategoryService {
	List<EasyUITreeNode> getContentCatList(Long parentId);
	TZResult addContentCategory(long parentId,String name);
}
