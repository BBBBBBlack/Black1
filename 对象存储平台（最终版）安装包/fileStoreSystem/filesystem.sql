/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.24 : Database - filesystem
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`filesystem` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `filesystem`;

/*Table structure for table `bucket_msg` */

CREATE TABLE `bucket_msg` (
  `name` varchar(10) NOT NULL COMMENT '桶的名称',
  `id` int NOT NULL AUTO_INCREMENT COMMENT '桶的id',
  `creator_id` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者的id',
  `authority` tinyint NOT NULL COMMENT '桶的基本权限:1—>公共读，2—>公共读写，3—>私有',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3;

/*Data for the table `bucket_msg` */

insert  into `bucket_msg`(`name`,`id`,`creator_id`,`authority`,`is_delete`,`create_time`) values 
('myFile',5,'2867628793@qq.com',2,0,'2023-03-10 00:00:00'),
('123',6,'1078713507@qq.com',1,1,'2023-03-13 00:00:00'),
('new',7,'1078713507@qq.com',2,0,'2023-03-15 00:00:00'),
('qqqqq',8,'2867628793@qq.com',1,0,'2023-03-25 00:00:00'),
('lalalala',9,'2867628793@qq.com',2,0,'2023-03-25 20:49:13'),
('picture',10,'2867628793@qq.com',2,0,'2023-03-29 17:07:09'),
('hahahah',11,'2867628793@qq.com',2,0,'2023-03-29 21:39:20'),
('aaaaaaaaaa',12,'2867628793@qq.com',2,0,'2023-03-29 21:44:59'),
('aaadsadsa',13,'2867628793@qq.com',2,0,'2023-03-29 21:47:33'),
('aaawwwww',14,'2867628793@qq.com',2,0,'2023-03-29 21:52:03'),
('aaawww',15,'2867628793@qq.com',2,0,'2023-03-29 22:07:38'),
('aaawwwc',16,'2867628793@qq.com',2,0,'2023-03-29 22:48:44'),
('aaawwwo',17,'2867628793@qq.com',2,0,'2023-03-29 22:49:33'),
('qqwewqeq',18,'2867628793@qq.com',2,0,'2023-03-29 22:50:12'),
('test',19,'1078713507@qq.com',1,0,'2023-04-11 22:02:15'),
('100',20,'2867628793@qq.com',1,0,'2023-04-14 23:16:46'),
('101',21,'2867628793@qq.com',1,0,'2023-04-14 23:16:46'),
('102',22,'2867628793@qq.com',1,0,'2023-04-14 23:16:46'),
('103',23,'2867628793@qq.com',1,0,'2023-04-14 23:16:47'),
('104',24,'2867628793@qq.com',1,0,'2023-04-14 23:16:47'),
('test02',25,'1078713507@qq.com',3,0,'2023-04-15 13:56:10'),
('',26,'ozlinex@outlook.com',1,1,'2023-04-16 13:22:54'),
('ozlinetest',27,'ozlinex@outlook.com',0,1,'2023-04-16 13:24:54'),
('111',28,'ozlinex@outlook.com',3,1,'2023-04-16 13:25:32'),
('test',29,'ozlinex@outlook.com',2,1,'2023-04-16 14:22:15'),
('Bucket1',30,'ozlinex@outlook.com',1,0,'2023-04-17 07:17:21'),
('Bucket2',31,'ozlinex@outlook.com',1,0,'2023-04-17 07:17:38'),
('Bucket3',32,'ozlinex@outlook.com',2,0,'2023-04-17 07:58:12'),
('test',33,'ozlinex@outlook.com',1,1,'2023-04-17 08:42:10');

/*Table structure for table `group` */

CREATE TABLE `group` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `user_id` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `bucket_id` int NOT NULL COMMENT 'bucketId',
  `type` tinyint NOT NULL COMMENT '分组类别(1->读写,2->只读,3->禁止访问)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;

/*Data for the table `group` */

insert  into `group`(`id`,`user_id`,`bucket_id`,`type`) values 
(2,'102101227@fzu.edu.cn',5,1),
(3,'lajizhuce@163.com',6,2),
(4,'lajizhuce0124@163.com',6,1),
(5,'lajizhuce0124@163.com',7,1),
(6,'lajizhuce0124@163.com',25,3),
(7,'elicpx@gmail.com',27,1),
(8,'elicpx@gmail.com',27,1),
(9,'elicpx@gmail.com',30,1),
(10,'tmptest@qq.com',30,2);

/*Table structure for table `user` */

CREATE TABLE `user` (
  `email` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `user` */

insert  into `user`(`email`,`password`,`name`,`is_delete`) values 
('102101227@fzu.edu.cn','e10adc3949ba59abbe56e057f20f883e','王五',0),
('1078713507@qq.com','202cb962ac59075b964b07152d234b70','1078713507@qq.com',0),
('2867628793@qq.com','e10adc3949ba59abbe56e057f20f883e','老王',0),
('elicpx@gmail.com','eb72031d8c2b29b92de052a0563627c7','elicpx@gmail.com',0),
('lajizhuce0124@163.com','202cb962ac59075b964b07152d234b70','lajizhuce0124@163.com',0),
('ozlinex@outlook.com','eb72031d8c2b29b92de052a0563627c7','ozlinex@outlook.com',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
