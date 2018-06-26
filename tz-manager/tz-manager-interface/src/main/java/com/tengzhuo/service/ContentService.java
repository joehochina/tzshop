package com.tengzhuo.service;

import java.util.List;

import com.tengzhuo.pojo.EasyUIDataGridResult;
import com.tengzhuo.pojo.TbContent;
import com.tengzhuo.utils.TZResult;

public interface ContentService {

	TZResult addContent(TbContent content);
	EasyUIDataGridResult getContentList(int page,int rows,long categoryId) ;
	List<TbContent> getContentByCid(long categoryId);
}
