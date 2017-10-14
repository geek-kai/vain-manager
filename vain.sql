/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.7.15 : Database - vain
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vain` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `vain`;

/*Table structure for table `t_menu` */

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
) ENGINE=InnoDB AUTO_INCREMENT=120101 DEFAULT CHARSET=utf8 COMMENT='保存菜单数据';

/*Data for the table `t_menu` */

insert  into `t_menu`(`id`,`name`,`parentId`,`menuKey`,`type`,`url`,`icon`,`description`,`deleted`,`createTime`,`modifyTime`) values (110000,'系统管理',0,'system',1,NULL,'am-icon-cog','系统管理',0,'2017-09-23 15:55:16','2017-09-23 15:55:17'),(110100,'系统配置',110000,'system.config',2,'../../page/system/config/config.html',NULL,NULL,0,'2017-10-14 21:31:30',NULL),(120000,'账号管理',0,'account',1,NULL,'am-icon-user','账号管理',0,'2017-10-14 20:36:34',NULL),(120100,'用户管理',120000,'account.user',2,'../../page/account/user/user.html',NULL,NULL,0,'2017-09-25 10:35:07','2017-09-25 10:35:09'),(120101,'用户添加',120100,'account.user.add',3,NULL,NULL,NULL,0,'2017-10-12 21:18:01','2017-10-12 21:18:04'),(120200,'角色管理',120000,'account.role',2,'../../page/account/role/role.html',NULL,NULL,0,'2017-09-25 14:05:51','2017-09-25 14:05:51'),(120300,'菜单管理',120000,'account.menu',2,'../../page/account/menu/menu.html',NULL,NULL,0,'2017-10-11 19:34:25',NULL),(130000,'动态管理',0,'dynamic',1,NULL,'am-icon-comments-o','动态管理',0,'2017-09-25 15:09:34','2017-09-25 15:09:35'),(130100,'发布动态',130000,'dynamic.send',2,NULL,NULL,NULL,0,'2017-09-25 15:35:35','2017-09-25 15:35:35');

/*Table structure for table `t_role` */

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

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`name`,`roleKey`,`description`,`deleted`,`createTime`,`modifyTime`) values (1,'超级管理员','SUPER_ADMINISTRATOR','超级管理员',0,'2017-09-23 16:17:24','2017-09-23 16:17:25'),(2,'管理员','ADMINISTRATOR','管理员',0,'2017-09-23 16:17:56','2017-09-23 16:17:55'),(3,'普通用户','USER','用户',0,'2017-09-23 16:19:01','2017-09-23 16:19:02');

/*Table structure for table `t_role_menu` */

DROP TABLE IF EXISTS `t_role_menu`;

CREATE TABLE `t_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL,
  `menuId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='保存角色和菜单的关联关系数据';

/*Data for the table `t_role_menu` */

insert  into `t_role_menu`(`id`,`roleId`,`menuId`,`createTime`,`modifyTime`) values (1,1,110000,'2017-10-14 20:47:47',NULL),(2,1,120000,'2017-10-14 20:47:47',NULL),(3,1,120100,'2017-10-14 20:47:47',NULL),(4,1,120101,'2017-10-14 20:47:47',NULL),(5,1,120200,'2017-10-14 20:47:47',NULL),(6,1,120300,'2017-10-14 20:47:47',NULL),(7,1,130000,'2017-10-14 20:47:47',NULL),(8,1,130100,'2017-10-14 20:47:47',NULL),(9,1,110100,'2017-10-14 21:45:43',NULL);

/*Table structure for table `t_system_config` */

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

/*Data for the table `t_system_config` */

insert  into `t_system_config`(`id`,`name`,`code`,`value`,`valueType`,`type`,`description`,`visible`,`createTime`,`modifyTime`) values (1,'上传文件访问根url','SYS_FILE_UPLOAD_ROOT_URL','http://192.168.121.1/vain',1,1,'上传文件访问根url','','2017-10-14 17:57:57','2017-10-14 17:57:57'),(2,'上传文件ftp的主机ip','SYS_FILE_UPLOAD_HOST_IP','192.168.121.1',1,1,'上传文件ftp的主机ip','','2017-10-14 17:57:57','2017-10-14 17:57:57'),(3,'上传文件ftp的主机端口','SYS_FILE_UPLOAD_HOST_PORT','22',2,1,'上传文件ftp的主机端口（默认22）','','2017-10-14 17:57:57','2017-10-14 17:57:57'),(4,'上传文件ftp的主机账号','SYS_FILE_UPLOAD_HOST_USER','root',1,1,'上传文件ftp的主机账号','','2017-10-14 17:57:57','2017-10-14 17:57:57'),(5,'上传文件ftp的主机密码','SYS_FILE_UPLOAD_HOST_PASSWD','vian123456',1,1,'上传文件ftp的主机密码','','2017-10-14 17:57:57','2017-10-14 17:57:57'),(6,'上传文件存放目录','SYS_FILE_UPLOAD_ROOT_PATH','/usr/share/nginx/html/vain',1,1,'上传文件存放目录','','2017-10-14 17:57:57','2017-10-14 17:57:57');

/*Table structure for table `t_user` */

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`type`,`userName`,`passwd`,`salt`,`nickname`,`head`,`sex`,`birthday`,`state`,`attr1`,`attr2`,`attr3`,`attr4`,`attr5`,`phone`,`email`,`deleted`,`createTime`,`modifyTime`) values (1,1,'admin','a0bca5d6fede811f6238a0eb475e2931','f74518bf-9b68-4f42-a680-eb3abdeaa1d6','管理员',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-09-17 16:42:25','2017-10-13 20:36:08'),(2,2,'小明','d6f175817754c5c0dae2520d0b7effe3','6acf8206-0869-4e50-af61-835ad15c05f3',NULL,NULL,0,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-09-30 21:41:46','2017-10-14 17:12:23'),(3,2,'张三丰','28573cf398dcdfb9085fdc4f5dcb382d','b96fedde-0c1b-424e-bf50-c3a2480d267a',NULL,NULL,0,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-10-14 13:31:05','2017-10-14 16:10:02'),(4,3,'用户A','0102cafa8d3a463e347a379529280472','997b3972-3fa2-4bcd-90e1-a109ac959611',NULL,NULL,0,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-10-14 14:01:46','2017-10-14 15:19:23');

/*Table structure for table `t_user_menu` */

DROP TABLE IF EXISTS `t_user_menu`;

CREATE TABLE `t_user_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `menuId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='保存用户与菜单的管理关系数据';

/*Data for the table `t_user_menu` */

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存用户与角色的关联关系数据';

/*Data for the table `t_user_role` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
