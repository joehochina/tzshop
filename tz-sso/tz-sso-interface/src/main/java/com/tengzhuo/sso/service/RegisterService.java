package com.tengzhuo.sso.service;

import com.tengzhuo.pojo.TbUser;
import com.tengzhuo.utils.TZResult;

public interface RegisterService {
	TZResult checkData(String param,int key);
	TZResult register(TbUser user);
}
