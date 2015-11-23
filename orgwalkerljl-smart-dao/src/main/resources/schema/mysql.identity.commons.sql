/**
 * 用户信息
 */
DROP TABLE IF EXISTS id_user;
CREATE TABLE id_user(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT COMMENT '主键Id',
	user_id VARCHAR(64) NOT NULL COMMENT '用户Id',
	user_name VARCHAR(64) NOT NULL COMMENT '用户姓名',
	sex ENUM('m','f') NOT NULL COMMENT '性别,m:男,f:女',
	email VARCHAR(64) DEFAULT NULL COMMENT '电子邮件',
	mobile CHAR(11) DEFAULT NULL COMMENT '手机',
	telephone VARCHAR(11) DEFAULT NULL COMMENT '座机',
	birthday DATE DEFAULT NULL COMMENT '出生日期',
	id_card_number CHAR(18) DEFAULT NULL COMMENT '身份证号码',
	last_login_date DATETIME NOT NULL COMMENT '最近登录日期',
	remark VARCHAR(256) DEFAULT NULL COMMENT '备注',
	status TINYINT(1) NOT NULL COMMENT '状态,1:启用,2:停用,3:删除',
	create_date DATETIME NOT NULL COMMENT '创建日期',
	create_user_id VARCHAR(64) NOT NULL COMMENT '创建者Id',
	create_user_name VARCHAR(64) NOT NULL COMMENT '创建者姓名',
	last_modify_date DATETIME NOT NULL COMMENT '最近更新日期',
	last_modify_user_id VARCHAR(64) NOT NULL COMMENT '最近修改者Id',
	last_modify_user_name VARCHAR(64) NOT NULL COMMENT '最近修改者姓名'
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT '用户信息';
/** 添加索引、约束等*/
ALTER TABLE id_user ADD INDEX id_user_index_user_id(user_id);
ALTER TABLE id_user ADD INDEX id_user_index_user_name(user_name);
ALTER TABLE id_user ADD INDEX id_user_index_mobile(mobile);
ALTER TABLE id_user ADD INDEX id_user_index_email(email);
ALTER TABLE id_user ADD UNIQUE(user_id);
ALTER TABLE id_user ADD UNIQUE(email);
ALTER TABLE id_user ADD UNIQUE(mobile);
/** 初始化数据*/
INSERT INTO id_user(user_id,user_name,sex,email,mobile,telephone,birthday,id_card_number,last_login_date,
remark,status,create_date,create_user_id,create_user_name,last_modify_date,last_modify_user_id,last_modify_user_name)
VALUES
('jarvis','JARVIS','m','','','','1990-01-01','',NOW(),'测试人员信息',1,NOW(),'jarvis','JARVIS',NOW(),'jarivs','JARVIS')
;
