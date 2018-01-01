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
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vain` /*!40100 DEFAULT CHARACTER SET latin1 */;

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
) ENGINE=InnoDB AUTO_INCREMENT=130101 DEFAULT CHARSET=utf8 COMMENT='保存菜单数据';

/*Data for the table `t_menu` */

insert  into `t_menu`(`id`,`name`,`parentId`,`menuKey`,`type`,`url`,`icon`,`description`,`deleted`,`createTime`,`modifyTime`) values (110000,'系统管理',0,'system',1,NULL,'am-icon-cog','系统管理',0,'2017-09-23 15:55:16',NULL),(110100,'系统信息',110000,'system.info',2,'/manager/page/system/info/info.html',NULL,NULL,0,'2017-10-18 16:03:20',NULL),(110101,'系统信息',110100,'system.info.getSystemInfo',3,'/api/systemConfig/getSystemInfo',NULL,NULL,0,'2017-10-24 16:24:44',NULL),(110102,'cpu信息',110100,'system.info.getSystemCpuInfo',3,'/api/systemConfig/getSystemCpuInfo',NULL,NULL,0,'2017-10-28 12:10:54',NULL),(110200,'系统配置',110000,'system.config',2,'/manager/page/system/config/config.html',NULL,NULL,0,'2017-10-14 21:31:30',NULL),(110201,'获取配置',110200,'system.config.getPagedList',3,'/api/systemConfig/getPagedList',NULL,NULL,0,'2017-10-28 12:12:13',NULL),(110202,'修改配置',110200,'system.config.modify',3,'/api/systemConfig/modify',NULL,NULL,0,'2017-10-24 10:09:26',NULL),(110300,'系统在线',110000,'system.online',2,'/manager/page/system/online/online.html',NULL,NULL,0,'2017-10-27 16:29:54',NULL),(110301,'在线用户',110300,'system.online.onLineUser',3,'/api/user/onLineUser',NULL,NULL,0,'2017-10-28 12:14:39',NULL),(110302,'用户下线',110300,'system.online.forcedOffLine',3,'/api/user/forcedOffLine',NULL,NULL,0,'2017-10-27 16:56:51',NULL),(110400,'定时任务',110000,'system.scheduleJob',2,'/manager/page/system/scheduleJob/scheduleJob.html',NULL,NULL,0,'2017-11-01 10:08:20',NULL),(110401,'任务获取',110400,'system.scheduleJob.getList',3,'/api/scheduleJob/getList',NULL,NULL,0,'2017-11-01 11:33:23',NULL),(110402,'任务暂停',110400,'system.scheduleJob.pause',3,'/api/scheduleJob/pauseJob',NULL,NULL,0,'2017-11-01 11:31:49',NULL),(110403,'任务恢复',110400,'system.scheduleJob.resume',3,'/api/scheduleJob/resumeJob',NULL,NULL,0,'2017-11-01 11:31:49',NULL),(110404,'任务测试',110400,'system.scheduleJob.trigger',3,'/api/scheduleJob/triggerJob',NULL,NULL,0,'2017-11-01 11:31:49',NULL),(110405,'任务修改',110400,'system.scheduleJob.modify',3,'/api/scheduleJob/modify',NULL,NULL,0,'2017-11-01 11:31:49',NULL),(110406,'任务删除',110400,'system.scheduleJob.delete',3,'/api/scheduleJob/delete',NULL,NULL,0,'2017-11-01 11:31:49',NULL),(120000,'账号管理',0,'account',1,NULL,'am-icon-user','账号管理',0,'2017-10-14 20:36:34',NULL),(120100,'用户管理',120000,'account.user',2,'/manager/page/account/user/user.html',NULL,NULL,0,'2017-09-25 10:35:07',NULL),(120101,'用户添加',120100,'account.user.add',3,'/api/user/add',NULL,NULL,0,'2017-10-12 21:18:01',NULL),(120102,'用户删除',120100,'account.user.delete',3,'/api/user/delete',NULL,NULL,0,'2017-10-25 14:33:37',NULL),(120103,'用户修改',120100,'account.user.modify',3,'/api/user/modify',NULL,NULL,0,'2017-10-25 14:33:37',NULL),(120104,'用户授权',120100,'account.user.grant',3,'/api/user/assignUserMenu',NULL,NULL,0,'2017-10-25 14:33:37',NULL),(120105,'用户角色',120100,'account.user.role',3,'/api/role/grantUserRole',NULL,NULL,0,'2017-10-25 17:49:39',NULL),(120106,'密码重置',120100,'account.user.resetPwd',3,'/api/user/resetPwd',NULL,NULL,0,'2017-10-25 17:49:39',NULL),(120107,'用户锁定',120100,'account.user.lock',3,'/api/user/lock',NULL,NULL,0,'2017-10-25 17:49:39',NULL),(120108,'用户查询',120100,'account.user.getPagedList',3,'/api/user/getPagedList',NULL,NULL,0,'2017-10-25 18:00:54',NULL),(120200,'角色管理',120000,'account.role',2,'/manager/page/account/role/role.html',NULL,NULL,0,'2017-09-25 14:05:51',NULL),(120201,'角色添加',120200,'account.role.add',3,'/api/role/add',NULL,NULL,0,'2017-10-25 10:23:07',NULL),(120202,'角色删除',120200,'account.role.delete',3,'/api/role/delete',NULL,NULL,0,'2017-10-25 10:23:43',NULL),(120203,'角色修改',120200,'account.role.modify',3,'/api/role/modify',NULL,NULL,0,'2017-10-25 10:24:23',NULL),(120204,'角色权限',120200,'account.role.grant',3,'/api/role/assignRoleMenu',NULL,NULL,0,'2017-10-25 10:31:15',NULL),(120205,'查询角色',120200,'account.role.getPagedList',3,'/api/role/getList',NULL,NULL,0,'2017-10-28 12:17:22',NULL),(120300,'菜单管理',120000,'account.menu',2,'/manager/page/account/menu/menu.html',NULL,NULL,0,'2017-10-11 19:34:25',NULL),(120301,'菜单查询',120300,'account.menu.getPagedList',3,'/api/menu/getMenuList',NULL,NULL,0,'2017-10-28 12:18:42',NULL),(120302,'菜单修改',120300,'account.menu.modify',3,'/api/menu/modify',NULL,NULL,0,'2017-10-25 10:16:44',NULL),(130000,'动态管理',0,'dynamic',1,'','am-icon-comments-o','动态管理',0,'2017-09-25 15:09:34',NULL),(130100,'发布动态',130000,'dynamic.send',2,NULL,NULL,NULL,0,'2017-09-25 15:35:35',NULL);

/*Table structure for table `t_operation_log` */

DROP TABLE IF EXISTS `t_operation_log`;

CREATE TABLE `t_operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '操作用户id',
  `operationType` int(11) DEFAULT NULL COMMENT '日志操作类型',
  `operationData` varchar(256) DEFAULT NULL COMMENT '操作数据',
  `operationTime` datetime NOT NULL,
  `info` varchar(256) DEFAULT NULL COMMENT '操作信息',
  `methodName` varchar(40) DEFAULT NULL COMMENT '操作方法',
  `className` varchar(60) DEFAULT NULL COMMENT '操作类',
  `operationIP` varchar(20) DEFAULT NULL,
  `exceptionMessage` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `AK_Key_1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `t_operation_log` */

insert  into `t_operation_log`(`id`,`userId`,`operationType`,`operationData`,`operationTime`,`info`,`methodName`,`className`,`operationIP`,`exceptionMessage`) values (11,NULL,5,'{\"type\":\"2\",\"modifyTime\":\"2017-11-03 21:54:01.0\",\"state\":\"1\",\"id\":\"5\",\"userName\":\"admin1\",\"createTime\":\"2017-10-26 15:18:56.0\"}','2017-11-03 22:42:33',NULL,'lock','com.vain.manager.service.impl.UserServiceImpl',NULL,NULL),(12,NULL,5,'{\"type\":\"2\",\"modifyTime\":\"2017-11-03 21:54:01.0\",\"state\":\"1\",\"id\":\"5\",\"userName\":\"admin1\",\"createTime\":\"2017-10-26 15:18:56.0\"}','2017-11-03 22:42:34',NULL,'lock','com.vain.manager.service.impl.UserServiceImpl',NULL,NULL),(13,NULL,5,'{\"type\":\"3\",\"modifyTime\":\"2017-11-03 22:09:33.0\",\"state\":\"1\",\"id\":\"4\",\"userName\":\"用户A\",\"createTime\":\"2017-10-14 14:01:46.0\"}','2017-11-03 22:44:13',NULL,'lock','com.vain.manager.service.impl.UserServiceImpl',NULL,NULL),(14,NULL,5,'{\"type\":\"3\",\"modifyTime\":\"2017-11-03 22:09:33.0\",\"state\":\"1\",\"id\":\"4\",\"userName\":\"用户A\",\"createTime\":\"2017-10-14 14:01:46.0\"}','2017-11-03 22:44:13',NULL,'lock','com.vain.manager.service.impl.UserServiceImpl',NULL,NULL),(15,1,6,'{\"userName\":\"admin\"}','2018-01-01 17:17:50','登录','login','com.vain.manager.controller.UserController',NULL,NULL),(16,1,6,'{\"userName\":\"admin\"}','2018-01-01 17:20:49','登录','login','com.vain.manager.controller.UserController',NULL,NULL),(17,1,6,'{\"userName\":\"admin\"}','2018-01-01 17:22:45','登录','login','com.vain.manager.controller.UserController',NULL,NULL),(18,1,6,'{\"userName\":\"admin\"}','2018-01-01 17:26:10','登录','login','com.vain.manager.controller.UserController',NULL,NULL),(19,1,6,'{\"userName\":\"admin\"}','2018-01-01 17:27:23','登录','login','com.vain.manager.controller.UserController',NULL,NULL),(20,1,6,'{\"userName\":\"admin\"}','2018-01-01 17:29:38','登录','login','com.vain.manager.controller.UserController',NULL,NULL),(21,1,6,'{\"userName\":\"admin\"}','2018-01-01 17:31:51','登录','login','com.vain.manager.controller.UserController',NULL,NULL),(22,1,6,'{\"userName\":\"admin\"}','2018-01-01 17:41:44','登录','login','com.vain.manager.controller.UserController',NULL,NULL),(23,1,6,'{\"userName\":\"admin\"}','2018-01-01 17:41:58','登录','login','com.vain.manager.controller.UserController',NULL,NULL),(24,1,6,'{\"userName\":\"admin\"}','2018-01-01 17:50:21','登录','login','com.vain.manager.controller.UserController',NULL,NULL);

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

insert  into `t_role`(`id`,`name`,`roleKey`,`description`,`deleted`,`createTime`,`modifyTime`) values (1,'超级管理员','SUPER_ADMINISTRATOR','超级管理员',0,'2017-09-23 16:17:24','2017-09-23 16:17:25'),(2,'管理员','ADMINISTRATOR','管理员',0,'2017-09-23 16:17:56','2017-10-19 18:00:23'),(3,'普通用户','USER','用户',0,'2017-09-23 16:19:01','2017-09-23 16:19:02');

/*Table structure for table `t_role_menu` */

DROP TABLE IF EXISTS `t_role_menu`;

CREATE TABLE `t_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL,
  `menuId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='保存角色和菜单的关联关系数据';

/*Data for the table `t_role_menu` */

insert  into `t_role_menu`(`id`,`roleId`,`menuId`,`createTime`,`modifyTime`) values (1,1,110000,'2017-10-28 12:20:36',NULL),(2,1,110100,'2017-10-28 12:20:36',NULL),(3,1,110101,'2017-10-28 12:20:36',NULL),(4,1,110102,'2017-10-28 12:20:36',NULL),(5,1,110200,'2017-10-28 12:20:36',NULL),(6,1,110201,'2017-10-28 12:20:36',NULL),(7,1,110202,'2017-10-28 12:20:36',NULL),(8,1,110300,'2017-10-28 12:20:36',NULL),(9,1,110301,'2017-10-28 12:20:36',NULL),(10,1,110302,'2017-10-28 12:20:36',NULL),(11,1,110400,'2017-10-28 12:20:36',NULL),(12,1,110401,'2017-10-28 12:20:36',NULL),(13,1,110402,'2017-10-28 12:20:36',NULL),(14,1,110403,'2017-10-28 12:20:36',NULL),(15,1,110404,'2017-10-28 12:20:36',NULL),(16,1,110405,'2017-10-28 12:20:36',NULL),(17,1,110406,'2017-10-28 12:20:36',NULL),(18,1,120000,'2017-10-28 12:20:36',NULL),(19,1,120100,'2017-10-28 12:20:36',NULL),(20,1,120101,'2017-10-28 12:20:36',NULL),(21,1,120102,'2017-10-28 12:20:36',NULL),(22,1,120103,'2017-10-28 12:20:36',NULL),(23,1,120104,'2017-10-28 12:20:36',NULL),(24,1,120105,'2017-10-28 12:20:36',NULL),(25,1,120106,'2017-10-28 12:20:36',NULL),(26,1,120107,'2017-10-28 12:20:36',NULL),(27,1,120108,'2017-10-28 12:20:36',NULL),(28,1,120200,'2017-10-28 12:20:36',NULL),(29,1,120201,'2017-10-28 12:20:36',NULL),(30,1,120202,'2017-10-28 12:20:36',NULL),(31,1,120203,'2017-10-28 12:20:36',NULL),(32,1,120204,'2017-11-01 10:08:58',NULL),(33,1,120205,'2017-11-01 14:39:49',NULL),(34,1,120300,'2017-11-01 14:39:49',NULL),(35,1,120301,'2017-11-01 14:39:49',NULL),(36,1,120302,'2017-11-01 14:39:49',NULL),(37,1,130000,'2017-11-01 14:39:49',NULL),(38,1,130100,'2017-11-01 14:40:16',NULL);

/*Table structure for table `t_schedule_job` */

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

/*Data for the table `t_schedule_job` */

insert  into `t_schedule_job`(`id`,`jobName`,`jobGroup`,`jobStatus`,`cronExpression`,`description`,`jobClass`,`isConcurrent`,`springName`,`jobMethod`,`deleted`,`createTime`,`modifyTime`) values (1,'测试0000','1','0','0 0/1 * * * ?','数据库备份',NULL,'1','autoTask','DbRestore',1,'2017-11-01 09:51:18','2018-01-01 17:50:27');

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

insert  into `t_system_config`(`id`,`name`,`code`,`value`,`valueType`,`type`,`description`,`visible`,`createTime`,`modifyTime`) values (1,'上传文件访问根url','SYS_FILE_UPLOAD_ROOT_URL','http://192.168.121.1/vain',1,1,'上传文件访问根url','','2017-10-14 17:57:57','2017-10-14 17:57:57'),(2,'上传文件ftp的主机ip','SYS_FILE_UPLOAD_HOST_IP','192.168.121.1',1,1,'上传文件ftp的主机ip','','2017-10-14 17:57:57','2017-10-14 17:57:57'),(3,'上传文件ftp的主机端口','SYS_FILE_UPLOAD_HOST_PORT','22',2,1,'上传文件ftp的主机端口（默认22）','','2017-10-14 17:57:57','2017-10-14 17:57:57'),(4,'上传文件ftp的主机账号','SYS_FILE_UPLOAD_HOST_USER','root11',1,2,'上传文件ftp的主机账号o','','2017-10-14 17:57:57','2017-10-27 17:22:03'),(5,'上传文件ftp的主机密码','SYS_FILE_UPLOAD_HOST_PASSWD','vian123456',1,1,'上传文件ftp的主机密码','','2017-10-14 17:57:57','2017-10-28 12:23:28'),(6,'上传文件存放目录','SYS_FILE_UPLOAD_ROOT_PATH','/usr/share/nginx/html/vain',1,1,'上传文件存放目录','','2017-10-14 17:57:57','2017-10-14 17:57:57');

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`type`,`userName`,`passwd`,`salt`,`nickname`,`head`,`sex`,`birthday`,`state`,`attr1`,`attr2`,`attr3`,`attr4`,`attr5`,`phone`,`email`,`deleted`,`createTime`,`modifyTime`) values (1,1,'admin','a0bca5d6fede811f6238a0eb475e2931','f74518bf-9b68-4f42-a680-eb3abdeaa1d6','管理员',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-09-17 16:42:25','2017-10-13 20:36:08'),(2,2,'小明','d6f175817754c5c0dae2520d0b7effe3','6acf8206-0869-4e50-af61-835ad15c05f3',NULL,NULL,0,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-09-30 21:41:46','2017-11-03 22:04:55'),(3,2,'张三丰','28573cf398dcdfb9085fdc4f5dcb382d','b96fedde-0c1b-424e-bf50-c3a2480d267a',NULL,NULL,0,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-10-14 13:31:05','2017-11-03 22:41:22'),(4,3,'用户A','0102cafa8d3a463e347a379529280472','997b3972-3fa2-4bcd-90e1-a109ac959611',NULL,NULL,0,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-10-14 14:01:46','2017-11-03 22:44:13'),(5,2,'admin1','c469ac8dcba977627b56ee712efad1f8','4fb2f8d8-d420-4666-bdd9-abb7c0a25e61',NULL,NULL,0,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-10-26 15:18:56','2017-11-03 22:42:34'),(6,2,'admin2','909f2105d2a6271da2299b13d356c7b3','f1843ae5-e9a2-49d8-8b92-c92dd6e7f65a',NULL,NULL,0,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-10-26 15:21:06','2017-10-27 11:34:55'),(7,3,'admin5','32b92f2864cada4f2e3056e2ecdac4ef','6a1aab2f-6b53-47fc-90ce-ddd057c0da2a',NULL,NULL,0,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-10-26 15:31:44','2017-10-27 11:15:36'),(8,2,'admin4','91243404dd46595e84f2d59ecdc4d08e','b0070f46-ff38-4c43-83eb-0f0d94cba132',NULL,NULL,0,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-10-26 15:36:15','2017-10-28 12:24:43'),(9,2,'admin3','2f35b10abb0a67879f44046554662edc','5f42b8af-a7cc-4601-8d7d-4a686d9767ce',NULL,NULL,0,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-10-26 15:38:41','2017-10-28 11:44:23'),(10,3,'admin99','e98cb23aa4675434681c35c047d88199','c36ae8e8-8a0e-4dda-afb8-614e7cbec6de',NULL,NULL,0,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-10-26 15:42:27','2017-10-27 17:20:33'),(11,3,'admin111','3a8932e2ea7a531ad08f9247188724d2','7ea5dfb5-c502-43cc-9902-dbb8afc873c2',NULL,NULL,0,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-10-26 15:50:09','2017-10-28 11:44:03');

/*Table structure for table `t_user_menu` */

DROP TABLE IF EXISTS `t_user_menu`;

CREATE TABLE `t_user_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `menuId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存用户与菜单的管理关系数据';

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
