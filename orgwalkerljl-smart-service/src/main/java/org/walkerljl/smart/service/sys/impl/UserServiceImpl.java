/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.walkerljl.commons.collection.ListUtils;
import org.walkerljl.smart.dao.DefaultBaseDao;
import org.walkerljl.smart.dao.sys.UserDao;
import org.walkerljl.smart.domain.sys.User;
import org.walkerljl.smart.enums.Status;
import org.walkerljl.smart.service.impl.DefaultBaseServiceImpl;
import org.walkerljl.smart.service.sys.UserService;

/**
 * UserServiceImpl
 * 
 * @author lijunlin
 */
@Service("userService")
public class UserServiceImpl extends DefaultBaseServiceImpl<User, Long> implements UserService {

	@Resource private UserDao userDao;
	
	@Override
	public DefaultBaseDao<User, Long> getDao() {
		return userDao;
	}

	@Override
	public User selectByUserId(String userId) {
		User condition = new User();
		condition.setUserId(userId);
		condition.setStatusType(Status.ENABLED);
		return ListUtils.first(selectList(condition));
	}
}