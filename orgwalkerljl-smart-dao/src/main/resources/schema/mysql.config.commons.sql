/**
 * 系统配置信息
 */
DROP TABLE IF EXISTS sys_config;
CREATE TABLE sys_config(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT COMMENT '主键Id',
	config_name VARCHAR(64) NOT NULL COMMENT '配置信息名称',
	config_key  VARCHAR(128) NOT NULL COMMENT '配置信息Key',
	config_value VARCHAR(4096) NOT NULL COMMENT '配置信息Value',
	write_type TINYINT(1) NOT NULL COMMENT '写入类型,1:立即写入,0:不立即写入',
	remark VARCHAR(256) DEFAULT NULL COMMENT '备注',
	status TINYINT(1) NOT NULL COMMENT '状态,1:启用,2:停用,3:删除',
	create_date DATETIME NOT NULL COMMENT '创建日期',
	create_user_id VARCHAR(64) NOT NULL COMMENT '创建者Id',
	create_user_name VARCHAR(64) NOT NULL COMMENT '创建者姓名',
	last_modify_date DATETIME NOT NULL COMMENT '最近更新日期',
	last_modify_user_id VARCHAR(64) NOT NULL COMMENT '最近修改者Id',
	last_modify_user_name VARCHAR(64) NOT NULL COMMENT '最近修改者姓名'
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT '系统配置信息';
/** 添加索引、约束等*/
ALTER TABLE sys_config ADD INDEX sys_config_index_config_name(config_name);
ALTER TABLE sys_config ADD INDEX sys_config_index_config_key(config_key);
ALTER TABLE sys_config ADD UNIQUE(config_name);
ALTER TABLE sys_config ADD UNIQUE(config_key);
/** 初始化数据*/
INSERT INTO sys_config(config_name,config_key,config_value,write_type,
remark,status,create_date,create_user_id,create_user_name,last_modify_date,last_modify_user_id,last_modify_user_name)
VALUES
('系统是否正在升级','system.upgrading','false',1,'',1,NOW(),'jarvis','JARVIS',NOW(),'jarivs','JARVIS'),
('系统管理员列表','admins','jarvis',1,'',1,NOW(),'jarvis','JARVIS',NOW(),'jarivs','JARVIS'),
('系统管理员邮件列表','admin.mail.list','xxx',1,'',1,NOW(),'jarvis','JARVIS',NOW(),'jarivs','JARVIS')
;
