/**
 * 系统配置信息
 */
CREATE TABLE sys_config(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT COMMENT '主键Id',
	name VARCHAR(64) NOT NULL COMMENT '配置信息名称',
	`key`  VARCHAR(128) NOT NULL COMMENT '配置信息Key',
	`value` VARCHAR(1024) NOT NULL COMMENT '配置信息Value',
	remark VARCHAR(256) DEFAULT NULL COMMENT '备注',
	status TINYINT(1) NOT NULL COMMENT '状态,1:启用,2:停用,3:删除',
	creator VARCHAR(64) NOT NULL COMMENT '创建者',
	created_time DATETIME NOT NULL COMMENT '创建时间',
	modifier VARCHAR(64) NOT NULL COMMENT '修改者',
	modified_time DATETIME NOT NULL COMMENT '修改时间'
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '系统配置信息';
/** 添加索引、约束等*/
ALTER TABLE sys_config ADD INDEX sys_config_index_name(name);
ALTER TABLE sys_config ADD INDEX sys_config_index_key(`key`);
ALTER TABLE sys_config ADD UNIQUE(name);
ALTER TABLE sys_config ADD UNIQUE(`key`);
/** 初始化数据*/
INSERT INTO sys_config(name,`key`,`value`,remark,status,creator,created_time,modifier,modified_time)
VALUES
('系统是否正在升级','system.upgrading','false','',1,'jarvis',NOW(),'jarivs',NOW()),
('系统管理员列表','admins','jarvis','',1,'jarvis',NOW(),'jarivs',NOW()),
('系统管理员邮件列表','admin.mail.list','x@163.com','',1,'jarvis',NOW(),'jarivs',NOW())
;
