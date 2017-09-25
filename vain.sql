/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : vain

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-09-25 18:16:00
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
  `description` varchar(256) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL COMMENT '0：正常\r\n            1：已删除',
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_menuKey` (`menuKey`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=120101 DEFAULT CHARSET=utf8 COMMENT='保存菜单数据';

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('110000', '系统管理', '0', 'system', '1', null, '系统管理', '0', '2017-09-23 15:55:16', '2017-09-23 15:55:17');
INSERT INTO `t_menu` VALUES ('110100', '个人账号管理', '110000', 'system.user', '2', '../../page/main/main.html', null, '0', '2017-09-25 10:35:07', '2017-09-25 10:35:09');
INSERT INTO `t_menu` VALUES ('110200', '个人菜单', '110000', 'system.menu', '3', '/api/menu/getMyMenus', null, '0', '2017-09-25 14:05:51', '2017-09-25 14:05:51');
INSERT INTO `t_menu` VALUES ('120000', '动态管理', '0', 'dynamic', '1', null, null, '0', '2017-09-25 15:09:34', '2017-09-25 15:09:35');
INSERT INTO `t_menu` VALUES ('120100', '发布动态', '120000', 'dynamic.send', '2', null, null, '0', '2017-09-25 15:35:35', '2017-09-25 15:35:35');

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
INSERT INTO `t_role` VALUES ('2', '管理员', 'ADMINISTRATOR', '管理员', '0', '2017-09-23 16:17:56', '2017-09-23 16:17:55');
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='保存角色和菜单的关联关系数据';

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('1', '1', '110000', '2017-09-23 18:20:36', '2017-09-23 18:20:37');
INSERT INTO `t_role_menu` VALUES ('2', '1', '110100', '2017-09-25 12:03:13', '2017-09-25 12:03:14');
INSERT INTO `t_role_menu` VALUES ('3', '1', '110200', '2017-09-25 14:07:08', '2017-09-25 14:07:09');
INSERT INTO `t_role_menu` VALUES ('4', '1', '120000', '2017-09-25 15:10:05', '2017-09-25 15:10:05');
INSERT INTO `t_role_menu` VALUES ('5', '1', '120100', '2017-09-25 15:35:46', '2017-09-25 15:35:47');

-- ----------------------------
-- Table structure for `t_sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统配置表';

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------

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
  `address` varchar(256) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '1', 'admin', 'a0bca5d6fede811f6238a0eb475e2931', 'f74518bf-9b68-4f42-a680-eb3abdeaa1d6', '管理员', null, null, null, null, '0', null, null, null, null, null, null, null, '0', '2017-09-17 16:42:25', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='保存用户与菜单的管理关系数据';

-- ----------------------------
-- Records of t_user_menu
-- ----------------------------
INSERT INTO `t_user_menu` VALUES ('1', '1', '110000', '2017-09-25 11:39:10', '2017-09-25 11:39:11');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='保存用户与角色的关联关系数据';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', '1', '2017-09-23 18:20:03', '2017-09-23 18:20:04');
