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

/*Table structure for table `t_sys_config` */

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

/*Data for the table `t_sys_config` */

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

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`type`,`userName`,`passwd`,`salt`,`nickname`,`head`,`sex`,`birthday`,`address`,`state`,`attr1`,`attr2`,`attr3`,`attr4`,`attr5`,`phone`,`email`,`deleted`,`createTime`,`modifyTime`) values (1,1,'admin','a0bca5d6fede811f6238a0eb475e2931','f74518bf-9b68-4f42-a680-eb3abdeaa1d6','管理员',NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'2017-09-17 16:42:25',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
