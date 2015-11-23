/*
 * Copyright (c) 2010-present www.walkerljl.org All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.dao.sys.impl;

import org.walkerljl.smart.dao.impl.DefaultBaseDaoImpl;
import org.walkerljl.smart.dao.sys.UserDao;
import org.walkerljl.smart.domain.sys.User;

/**
 * UserDaoImpl
 * 
 * @author lijunlin
 */
public class UserDaoImpl extends DefaultBaseDaoImpl<User, Long> implements UserDao {

	public UserDaoImpl() {
		super.baseNameSpace = "org.walkerljl.smart.dao.sys.UserDao";
	}
}
