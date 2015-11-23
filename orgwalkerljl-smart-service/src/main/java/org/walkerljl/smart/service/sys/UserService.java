/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.service.sys;

import org.walkerljl.smart.domain.sys.User;
import org.walkerljl.smart.service.DefaultBaseService;

/**
 * UserService
 * @author lijunlin<walkerljl@qq.com>
 */
public interface UserService extends DefaultBaseService<User, Long> {

	/**
	 * 通过UserId获取用户信息
	 * @param userId
	 * @return
	 */
	User selectByUserId(String userId);
}
