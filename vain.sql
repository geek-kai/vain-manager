/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : vain

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-11-03 18:10:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单id规则\r\n 11 00 00，高位代表目录，中位代表菜单，低位代表操作\r\n            ',
  `name` varchar(128) NOT NULL,
  `parentId` bigint(20) NOT NULL COMMENT '根目录的父id填0',
  `menuKey` varchar(128) NOT NULL,
  `type` int(11) NOT NULL COMMENT '1：目录\r\n            2：菜单\r\n            3：按钮\r\n            ',
  `url` varchar(256) DEFAULT NULL COMMENT '点击菜单进入的页面，相对路径',
  `icon` varchar(80) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL COMMENT '0：正常\r\n            1：已删除',
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_menuKey` (`menuKey`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=130101 DEFAULT CHARSET=utf8 COMMENT='保存菜单数据';

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('110000', '系统管理', '0', 'system', '1', null, 'am-icon-cog', '系统管理', '0', '2017-09-23 15:55:16', null);
INSERT INTO `t_menu` VALUES ('110100', '系统信息', '110000', 'system.info', '2', '/manager/page/system/info/info.html', null, null, '0', '2017-10-18 16:03:20', null);
INSERT INTO `t_menu` VALUES ('110101', '系统信息', '110100', 'system.info.getSystemInfo', '3', '/vain-manager/api/systemConfig/getSystemInfo', null, null, '0', '2017-10-24 16:24:44', null);
INSERT INTO `t_menu` VALUES ('110102', 'cpu信息', '110100', 'system.info.getSystemCpuInfo', '3', '/vain-manager/api/systemConfig/getSystemCpuInfo', null, null, '0', '2017-10-28 12:10:54', null);
INSERT INTO `t_menu` VALUES ('110200', '系统配置', '110000', 'system.config', '2', '/manager/page/system/config/config.html', null, null, '0', '2017-10-14 21:31:30', null);
INSERT INTO `t_menu` VALUES ('110201', '获取配置', '110200', 'system.config.getPagedList', '3', '/vain-manager/api/systemConfig/getPagedList', null, null, '0', '2017-10-28 12:12:13', null);
INSERT INTO `t_menu` VALUES ('110202', '修改配置', '110200', 'system.config.modify', '3', '/vain-manager/api/systemConfig/modify', null, null, '0', '2017-10-24 10:09:26', null);
INSERT INTO `t_menu` VALUES ('110300', '系统在线', '110000', 'system.online', '2', '/manager/page/system/online/online.html', null, null, '0', '2017-10-27 16:29:54', null);
INSERT INTO `t_menu` VALUES ('110301', '在线用户', '110300', 'system.online.onLineUser', '3', '/vain-manager/api/user/onLineUser', null, null, '0', '2017-10-28 12:14:39', null);
INSERT INTO `t_menu` VALUES ('110302', '用户下线', '110300', 'system.online.forcedOffLine', '3', '/vain-manager/api/user/forcedOffLine', null, null, '0', '2017-10-27 16:56:51', null);
INSERT INTO `t_menu` VALUES ('110400', '定时任务', '110000', 'system.scheduleJob', '2', '/manager/page/system/scheduleJob/scheduleJob.html', null, null, '0', '2017-11-01 10:08:20', null);
INSERT INTO `t_menu` VALUES ('110401', '任务获取', '110400', 'system.scheduleJob.getList', '3', '/vain-manager/api/scheduleJob/getList', null, null, '0', '2017-11-01 11:33:23', null);
INSERT INTO `t_menu` VALUES ('110402', '任务暂停', '110400', 'system.scheduleJob.pause', '3', '/vain-manager/api/scheduleJob/pauseJob', null, null, '0', '2017-11-01 11:31:49', null);
INSERT INTO `t_menu` VALUES ('110403', '任务恢复', '110400', 'system.scheduleJob.resume', '3', '/vain-manager/api/scheduleJob/resumeJob', null, null, '0', '2017-11-01 11:31:49', null);
INSERT INTO `t_menu` VALUES ('110404', '任务测试', '110400', 'system.scheduleJob.trigger', '3', '/vain-manager/api/scheduleJob/triggerJob', null, null, '0', '2017-11-01 11:31:49', null);
INSERT INTO `t_menu` VALUES ('110405', '任务修改', '110400', 'system.scheduleJob.modify', '3', '/vain-manager/api/scheduleJob/modify', null, null, '0', '2017-11-01 11:31:49', null);
INSERT INTO `t_menu` VALUES ('110406', '任务删除', '110400', 'system.scheduleJob.delete', '3', '/vain-manager/api/scheduleJob/delete', null, null, '0', '2017-11-01 11:31:49', null);
INSERT INTO `t_menu` VALUES ('120000', '账号管理', '0', 'account', '1', null, 'am-icon-user', '账号管理', '0', '2017-10-14 20:36:34', null);
INSERT INTO `t_menu` VALUES ('120100', '用户管理', '120000', 'account.user', '2', '/manager/page/account/user/user.html', null, null, '0', '2017-09-25 10:35:07', null);
INSERT INTO `t_menu` VALUES ('120101', '用户添加', '120100', 'account.user.add', '3', '/vain-manager/api/user/add', null, null, '0', '2017-10-12 21:18:01', null);
INSERT INTO `t_menu` VALUES ('120102', '用户删除', '120100', 'account.user.delete', '3', '/vain-manager/api/user/delete', null, null, '0', '2017-10-25 14:33:37', null);
INSERT INTO `t_menu` VALUES ('120103', '用户修改', '120100', 'account.user.modify', '3', '/vain-manager/api/user/modify', null, null, '0', '2017-10-25 14:33:37', null);
INSERT INTO `t_menu` VALUES ('120104', '用户授权', '120100', 'account.user.grant', '3', '/vain-manager/api/user/assignUserMenu', null, null, '0', '2017-10-25 14:33:37', null);
INSERT INTO `t_menu` VALUES ('120105', '用户角色', '120100', 'account.user.role', '3', '/vain-manager/api/role/grantUserRole', null, null, '0', '2017-10-25 17:49:39', null);
INSERT INTO `t_menu` VALUES ('120106', '密码重置', '120100', 'account.user.resetPwd', '3', '/vain-manager/api/user/resetPwd', null, null, '0', '2017-10-25 17:49:39', null);
INSERT INTO `t_menu` VALUES ('120107', '用户锁定', '120100', 'account.user.lock', '3', '/vain-manager/api/user/lock', null, null, '0', '2017-10-25 17:49:39', null);
INSERT INTO `t_menu` VALUES ('120108', '用户查询', '120100', 'account.user.getPagedList', '3', '/vain-manager/api/user/getPagedList', null, null, '0', '2017-10-25 18:00:54', null);
INSERT INTO `t_menu` VALUES ('120200', '角色管理', '120000', 'account.role', '2', '/manager/page/account/role/role.html', null, null, '0', '2017-09-25 14:05:51', null);
INSERT INTO `t_menu` VALUES ('120201', '角色添加', '120200', 'account.role.add', '3', '/vain-manager/api/role/add', null, null, '0', '2017-10-25 10:23:07', null);
INSERT INTO `t_menu` VALUES ('120202', '角色删除', '120200', 'account.role.delete', '3', '/vain-manager/api/role/delete', null, null, '0', '2017-10-25 10:23:43', null);
INSERT INTO `t_menu` VALUES ('120203', '角色修改', '120200', 'account.role.modify', '3', '/vain-manager/api/role/modify', null, null, '0', '2017-10-25 10:24:23', null);
INSERT INTO `t_menu` VALUES ('120204', '角色权限', '120200', 'account.role.grant', '3', '/vain-manager/api/role/assignRoleMenu', null, null, '0', '2017-10-25 10:31:15', null);
INSERT INTO `t_menu` VALUES ('120205', '查询角色', '120200', 'account.role.getPagedList', '3', '/vain-manager/api/role/getPagedList', null, null, '0', '2017-10-28 12:17:22', null);
INSERT INTO `t_menu` VALUES ('120300', '菜单管理', '120000', 'account.menu', '2', '/manager/page/account/menu/menu.html', null, null, '0', '2017-10-11 19:34:25', null);
INSERT INTO `t_menu` VALUES ('120301', '菜单查询', '120300', 'account.menu.getPagedList', '3', '/vain-manager/api/menu/getPagedList', null, null, '0', '2017-10-28 12:18:42', null);
INSERT INTO `t_menu` VALUES ('120302', '菜单修改', '120300', 'account.menu.modify', '3', '/vain-manager/api/menu/modify', null, null, '0', '2017-10-25 10:16:44', null);
INSERT INTO `t_menu` VALUES ('130000', '动态管理', '0', 'dynamic', '1', null, 'am-icon-comments-o', '动态管理', '0', '2017-09-25 15:09:34', null);
INSERT INTO `t_menu` VALUES ('130100', '发布动态', '130000', 'dynamic.send', '2', null, null, null, '0', '2017-09-25 15:35:35', null);

-- ----------------------------
-- Table structure for `t_operation_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_operation_log`;
CREATE TABLE `t_operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operationType` int(11) DEFAULT NULL,
  `operation` int(11) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL COMMENT '系统：0',
  `operationId` bigint(20) DEFAULT NULL,
  `operationTime` datetime NOT NULL,
  `info` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `AK_Key_1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_operation_log
-- ----------------------------
INSERT INTO `t_operation_log` VALUES ('1', '6', null, null, null, '2017-11-03 15:49:47', '登录');
INSERT INTO `t_operation_log` VALUES ('2', '6', null, null, null, '2017-11-03 17:56:05', null);
INSERT INTO `t_operation_log` VALUES ('3', '6', null, null, null, '2017-11-03 18:09:10', '{\"type\":\"0\",\"state\":\"0\",\"sex\":\"0\",\"userName\":\"admin\",\"deleted\":\"0\",\"passwd\":\"vain123\"}');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `roleKey` varchar(128) NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL COMMENT '0：正常\r\n            1：已删除',
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_roleKey` (`roleKey`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='保存角色数据';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', 'SUPER_ADMINISTRATOR', '超级管理员', '0', '2017-09-23 16:17:24', '2017-09-23 16:17:25');
INSERT INTO `t_role` VALUES ('2', '管理员', 'ADMINISTRATOR', '管理员', '0', '2017-09-23 16:17:56', '2017-10-19 18:00:23');
INSERT INTO `t_role` VALUES ('3', '普通用户', 'USER', '用户', '0', '2017-09-23 16:19:01', '2017-09-23 16:19:02');

-- ----------------------------
-- Table structure for `t_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL,
  `menuId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='保存角色和菜单的关联关系数据';

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('1', '1', '110000', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('2', '1', '110100', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('3', '1', '110101', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('4', '1', '110102', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('5', '1', '110200', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('6', '1', '110201', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('7', '1', '110202', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('8', '1', '110300', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('9', '1', '110301', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('10', '1', '110302', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('11', '1', '120000', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('12', '1', '120100', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('13', '1', '120101', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('14', '1', '120102', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('15', '1', '120103', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('16', '1', '120104', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('17', '1', '120105', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('18', '1', '120106', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('19', '1', '120107', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('20', '1', '120108', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('21', '1', '120200', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('22', '1', '120201', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('23', '1', '120202', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('24', '1', '120203', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('25', '1', '120204', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('26', '1', '120205', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('27', '1', '120300', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('28', '1', '120301', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('29', '1', '120302', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('30', '1', '130000', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('31', '1', '130100', '2017-10-28 12:20:36', null);
INSERT INTO `t_role_menu` VALUES ('32', '1', '110400', '2017-11-01 10:08:58', null);
INSERT INTO `t_role_menu` VALUES ('33', '1', '110401', '2017-11-01 14:39:49', null);
INSERT INTO `t_role_menu` VALUES ('34', '1', '110402', '2017-11-01 14:39:49', null);
INSERT INTO `t_role_menu` VALUES ('35', '1', '110403', '2017-11-01 14:39:49', null);
INSERT INTO `t_role_menu` VALUES ('36', '1', '110404', '2017-11-01 14:39:49', null);
INSERT INTO `t_role_menu` VALUES ('37', '1', '110405', '2017-11-01 14:39:49', null);
INSERT INTO `t_role_menu` VALUES ('38', '1', '110406', '2017-11-01 14:40:16', null);

-- ----------------------------
-- Table structure for `t_schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule_job`;
CREATE TABLE `t_schedule_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jobName` varchar(255) DEFAULT NULL,
  `jobGroup` varchar(255) DEFAULT NULL,
  `jobStatus` varchar(255) DEFAULT NULL,
  `cronExpression` varchar(40) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `jobClass` varchar(255) DEFAULT NULL,
  `isConcurrent` varchar(255) DEFAULT NULL COMMENT '1',
  `springName` varchar(255) DEFAULT NULL,
  `jobMethod` varchar(255) NOT NULL,
  `deleted` tinyint(4) NOT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `modifyTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_group` (`jobName`,`jobGroup`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_schedule_job
-- ----------------------------
INSERT INTO `t_schedule_job` VALUES ('1', '测试0000', '1', '0', '0 0/1 * * * ?', '数据库备份', null, '1', 'autoTask', 'DbRestore', '0', '2017-11-01 09:51:18', '2017-11-03 14:06:19');

-- ----------------------------
-- Table structure for `t_system_config`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_config`;
CREATE TABLE `t_system_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置表的起始值设置为100，1-100用于通用配置项',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '配置项名字',
  `code` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '配置项键',
  `value` varchar(2048) COLLATE utf8_bin NOT NULL COMMENT '配置项值',
  `valueType` tinyint(1) NOT NULL DEFAULT '1' COMMENT '配置项值类型\n1：String\n2：INT\n3：String[]\n',
  `type` tinyint(4) NOT NULL COMMENT '配置项类型\n1：系统基础配置',
  `description` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '配置项描述',
  `visible` bit(1) NOT NULL DEFAULT b'1' COMMENT '配置项在管理界面是否可见',
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统配置表';

-- ----------------------------
-- Records of t_system_config
-- ----------------------------
INSERT INTO `t_system_config` VALUES ('1', '上传文件访问根url', 'SYS_FILE_UPLOAD_ROOT_URL', 'http://192.168.121.1/vain', '1', '1', '上传文件访问根url', '', '2017-10-14 17:57:57', '2017-10-14 17:57:57');
INSERT INTO `t_system_config` VALUES ('2', '上传文件ftp的主机ip', 'SYS_FILE_UPLOAD_HOST_IP', '192.168.121.1', '1', '1', '上传文件ftp的主机ip', '', '2017-10-14 17:57:57', '2017-10-14 17:57:57');
INSERT INTO `t_system_config` VALUES ('3', '上传文件ftp的主机端口', 'SYS_FILE_UPLOAD_HOST_PORT', '22', '2', '1', '上传文件ftp的主机端口（默认22）', '', '2017-10-14 17:57:57', '2017-10-14 17:57:57');
INSERT INTO `t_system_config` VALUES ('4', '上传文件ftp的主机账号', 'SYS_FILE_UPLOAD_HOST_USER', 'root11', '1', '2', '上传文件ftp的主机账号o', '', '2017-10-14 17:57:57', '2017-10-27 17:22:03');
INSERT INTO `t_system_config` VALUES ('5', '上传文件ftp的主机密码', 'SYS_FILE_UPLOAD_HOST_PASSWD', 'vian123456', '1', '1', '上传文件ftp的主机密码', '', '2017-10-14 17:57:57', '2017-10-28 12:23:28');
INSERT INTO `t_system_config` VALUES ('6', '上传文件存放目录', 'SYS_FILE_UPLOAD_ROOT_PATH', '/usr/share/nginx/html/vain', '1', '1', '上传文件存放目录', '', '2017-10-14 17:57:57', '2017-10-14 17:57:57');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户类型',
  `userName` varchar(64) NOT NULL COMMENT '用户名',
  `passwd` varchar(256) NOT NULL COMMENT '密码加密算法：MD5(passwd+salt)',
  `salt` varchar(128) NOT NULL,
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `head` varchar(256) DEFAULT NULL COMMENT '头像',
  `sex` int(11) DEFAULT NULL COMMENT '1：男\r\n            2：女',
  `birthday` date DEFAULT NULL,
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态\n0：正常 1：锁定 ',
  `attr1` varchar(500) DEFAULT NULL COMMENT '扩展字段',
  `attr2` varchar(64) DEFAULT NULL COMMENT '扩展字段',
  `attr3` varchar(64) DEFAULT NULL COMMENT '扩展字段',
  `attr4` varchar(64) DEFAULT NULL COMMENT '扩展字段',
  `attr5` varchar(64) DEFAULT NULL COMMENT '扩展字段',
  `phone` varchar(18) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL COMMENT '0:正常\r\n            1：已删除',
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '1', 'admin', 'a0bca5d6fede811f6238a0eb475e2931', 'f74518bf-9b68-4f42-a680-eb3abdeaa1d6', '管理员', null, null, null, '0', null, null, null, null, null, null, null, '0', '2017-09-17 16:42:25', '2017-10-13 20:36:08');
INSERT INTO `t_user` VALUES ('2', '2', '小明', 'd6f175817754c5c0dae2520d0b7effe3', '6acf8206-0869-4e50-af61-835ad15c05f3', null, null, '0', null, '0', null, null, null, null, null, null, null, '0', '2017-09-30 21:41:46', '2017-10-19 18:34:30');
INSERT INTO `t_user` VALUES ('3', '2', '张三丰', '28573cf398dcdfb9085fdc4f5dcb382d', 'b96fedde-0c1b-424e-bf50-c3a2480d267a', null, null, '0', null, '0', null, null, null, null, null, null, null, '0', '2017-10-14 13:31:05', '2017-10-14 16:10:02');
INSERT INTO `t_user` VALUES ('4', '3', '用户A', '0102cafa8d3a463e347a379529280472', '997b3972-3fa2-4bcd-90e1-a109ac959611', null, null, '0', null, '0', null, null, null, null, null, null, null, '0', '2017-10-14 14:01:46', '2017-10-14 15:19:23');
INSERT INTO `t_user` VALUES ('5', '2', 'admin1', 'c469ac8dcba977627b56ee712efad1f8', '4fb2f8d8-d420-4666-bdd9-abb7c0a25e61', null, null, '0', null, '0', null, null, null, null, null, null, null, '0', '2017-10-26 15:18:56', '2017-11-01 13:39:09');
INSERT INTO `t_user` VALUES ('6', '2', 'admin2', '909f2105d2a6271da2299b13d356c7b3', 'f1843ae5-e9a2-49d8-8b92-c92dd6e7f65a', null, null, '0', null, '0', null, null, null, null, null, null, null, '0', '2017-10-26 15:21:06', '2017-10-27 11:34:55');
INSERT INTO `t_user` VALUES ('7', '3', 'admin5', '32b92f2864cada4f2e3056e2ecdac4ef', '6a1aab2f-6b53-47fc-90ce-ddd057c0da2a', null, null, '0', null, '0', null, null, null, null, null, null, null, '0', '2017-10-26 15:31:44', '2017-10-27 11:15:36');
INSERT INTO `t_user` VALUES ('8', '2', 'admin4', '91243404dd46595e84f2d59ecdc4d08e', 'b0070f46-ff38-4c43-83eb-0f0d94cba132', null, null, '0', null, '1', null, null, null, null, null, null, null, '0', '2017-10-26 15:36:15', '2017-10-28 12:24:43');
INSERT INTO `t_user` VALUES ('9', '2', 'admin3', '2f35b10abb0a67879f44046554662edc', '5f42b8af-a7cc-4601-8d7d-4a686d9767ce', null, null, '0', null, '0', null, null, null, null, null, null, null, '0', '2017-10-26 15:38:41', '2017-10-28 11:44:23');
INSERT INTO `t_user` VALUES ('10', '3', 'admin99', 'e98cb23aa4675434681c35c047d88199', 'c36ae8e8-8a0e-4dda-afb8-614e7cbec6de', null, null, '0', null, '0', null, null, null, null, null, null, null, '0', '2017-10-26 15:42:27', '2017-10-27 17:20:33');
INSERT INTO `t_user` VALUES ('11', '3', 'admin111', '3a8932e2ea7a531ad08f9247188724d2', '7ea5dfb5-c502-43cc-9902-dbb8afc873c2', null, null, '0', null, '0', null, null, null, null, null, null, null, '0', '2017-10-26 15:50:09', '2017-10-28 11:44:03');

-- ----------------------------
-- Table structure for `t_user_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_menu`;
CREATE TABLE `t_user_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `menuId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存用户与菜单的管理关系数据';

-- ----------------------------
-- Records of t_user_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存用户与角色的关联关系数据';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
