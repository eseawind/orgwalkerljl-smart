/*
 * Copyright (c) 2010-2015 lijunlin All Rights Reserved.
 * The software source code all copyright belongs to the author, 
 * without permission shall not be any reproduction and transmission.
 */
package org.walkerljl.smart.mvc.security;

import java.io.Serializable;
import java.util.List;

/**
 * ButtonBar
 * @author lijunlin<walkerljl@qq.com>
 */
public interface ButtonBar extends Serializable {
	
	List<Button> getButtons(String userId, boolean isAdmin, String permissionCodePrefix);
}
