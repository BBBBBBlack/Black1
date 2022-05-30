/*
 Navicat Premium Data Transfer

 Source Server         : Granger01
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : market

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 21/05/2022 19:22:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mar_bill
-- ----------------------------
DROP TABLE IF EXISTS `mar_bill`;
CREATE TABLE `mar_bill`  (
  `user_id` bigint NOT NULL,
  `goods_id` bigint NOT NULL,
  `number` int NOT NULL COMMENT '所订商品件数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '下单时间',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '交易完成时间',
  `bill_status` int NOT NULL DEFAULT 0 COMMENT '0为未完成交易——1为已完成交易——-1为交易取消',
  `bill_id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`bill_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mar_bill
-- ----------------------------
INSERT INTO `mar_bill` VALUES (3, 6, 1, '2022-03-30 16:59:26', '2022-04-16 19:24:43', 1, 1);
INSERT INTO `mar_bill` VALUES (3, 9, 1, '2022-04-29 17:06:27', '2022-04-29 17:35:31', 1, 3);
INSERT INTO `mar_bill` VALUES (5, 1, 1, '2022-05-13 19:40:28', NULL, 0, 5);
INSERT INTO `mar_bill` VALUES (1, 11, 1, '2022-05-13 21:09:28', NULL, 0, 10);
INSERT INTO `mar_bill` VALUES (5, 3, 1, '2022-05-16 22:28:45', NULL, 0, 11);
INSERT INTO `mar_bill` VALUES (5, 2, 1, '2022-05-16 23:45:46', NULL, 0, 12);

-- ----------------------------
-- Table structure for mar_goods
-- ----------------------------
DROP TABLE IF EXISTS `mar_goods`;
CREATE TABLE `mar_goods`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键——商品id',
  `goods_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `introduction` blob NULL COMMENT '商品描述',
  `price` int NOT NULL COMMENT '商品价格',
  `number` int NOT NULL COMMENT '商品剩余件数',
  `goods_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `goods_status` int NOT NULL DEFAULT 0 COMMENT '商品状态——未审核为0，审核已通过为1，审核未通过为-1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mar_goods
-- ----------------------------
INSERT INTO `mar_goods` VALUES (1, 'ABC', 0xE8BF99E698AF414243, 20, 1, '/file/11.jpg', 1);
INSERT INTO `mar_goods` VALUES (2, 'BCD', 0xE8BF99E698AF424344, 10, 1, '/file/10.jpg', 1);
INSERT INTO `mar_goods` VALUES (3, 'CDE', 0xE8BF99E698AF434445, 20, 3, '/file/9.jpg', 1);
INSERT INTO `mar_goods` VALUES (6, 'DHN', 0xE8BF99E698AF44484E, 50, 0, '/file/d22f85e5599c4902a24190955514b88b.jpg', 1);
INSERT INTO `mar_goods` VALUES (7, 'LWK', 0xE8BF99E698AF4C574B, 10000, 1, '/file/0ecd556cce764aac939ff68f9ba10469.jpg', 1);
INSERT INTO `mar_goods` VALUES (8, 'LISA', 0xE8BF99E698AF4C495341, 1000000, 1, '/file/3ca63002c28c4ef484e11de0c0726c0c.jpg', 1);
INSERT INTO `mar_goods` VALUES (9, 'CITY', 0xE8BF99E698AF43495459, 1000000, 89, '/file/1453b08e904f45459216b9766590979a.jpg', 1);
INSERT INTO `mar_goods` VALUES (10, 'FDM', 0xE8BF99E698AFE4B880E4B8AA46444D, 999999, 1, '/file/4555a156c33c45b393f6f6808fa64aa4.jpg', 1);
INSERT INTO `mar_goods` VALUES (11, 'picc', 0xE8BF99E698AFE4B880E4B8AA70696363, 20, 5, '/file/f72760b00e404f2888a127102773ca7e.jpg', 1);
INSERT INTO `mar_goods` VALUES (12, 'DC', 0xE8BF99E698AF4443, 50, 1, '/file/0d5138dce23c4d8bbae9d6ee39d63fab.png', 0);
INSERT INTO `mar_goods` VALUES (13, 'A', 0xE8BF99E698AF41, 23, 1, '/file/a27c9e960b9f4a4488a4efb0174dc5da.png', 0);
INSERT INTO `mar_goods` VALUES (19, 'A', 0xE8BF99E698AF41, 50, 3, NULL, 0);

-- ----------------------------
-- Table structure for mar_submit
-- ----------------------------
DROP TABLE IF EXISTS `mar_submit`;
CREATE TABLE `mar_submit`  (
  `user_id` bigint NOT NULL,
  `goods_id` bigint NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '提交商品时间',
  PRIMARY KEY (`user_id`, `goods_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mar_submit
-- ----------------------------
INSERT INTO `mar_submit` VALUES (1, 1, '2022-05-13 20:18:58');
INSERT INTO `mar_submit` VALUES (1, 2, '2022-05-13 20:19:14');
INSERT INTO `mar_submit` VALUES (1, 3, '2022-05-13 20:19:31');
INSERT INTO `mar_submit` VALUES (1, 6, '2022-04-06 22:55:02');
INSERT INTO `mar_submit` VALUES (1, 7, '2022-04-06 23:02:45');
INSERT INTO `mar_submit` VALUES (1, 8, '2022-04-07 16:17:02');
INSERT INTO `mar_submit` VALUES (1, 9, '2022-04-07 16:17:40');
INSERT INTO `mar_submit` VALUES (1, 10, '2022-04-11 19:53:28');
INSERT INTO `mar_submit` VALUES (1, 11, '2022-04-29 16:51:43');
INSERT INTO `mar_submit` VALUES (5, 12, '2022-05-16 22:29:20');
INSERT INTO `mar_submit` VALUES (5, 13, '2022-05-16 22:37:44');
INSERT INTO `mar_submit` VALUES (5, 19, '2022-05-16 22:48:10');

-- ----------------------------
-- Table structure for mar_user
-- ----------------------------
DROP TABLE IF EXISTS `mar_user`;
CREATE TABLE `mar_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键——用户id',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'a_secret_user' COMMENT '用户名',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NULL' COMMENT '用户密码',
  `phone_number` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户电话',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `user_status` int NOT NULL DEFAULT 1 COMMENT '用户状态——正常状态为1，封禁状态为0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mar_user
-- ----------------------------
INSERT INTO `mar_user` VALUES (1, 'abc', 'LWK', '$2a$10$lbB1EJuKxK7zPxPcrmq/tuCzQHAwzLp/SQTHdpT6sVjZEr1YyDL8C', '17850912348', '/file/6e3420cd9abb49f68e85d2fc0ff2854f.jpg', 1);
INSERT INTO `mar_user` VALUES (2, 'def', 'bb', '$2a$10$jlF3RHjYfafA/MU3ZynaF.vjDY141YmgKjTISGQIcep1OrhO2ev7i', '17850426783', NULL, 0);
INSERT INTO `mar_user` VALUES (3, 'fuj', 'herm', '$2a$10$GawiVOgfpBRxV0pvbD0cheuQw5HJPiRcYuLUE5r3BRxb6KmDCxq4C', '13450963728', '/file/93dfb80cb60e4ab683b413c538db70c8.jpg', 1);
INSERT INTO `mar_user` VALUES (4, 'dads', NULL, '$2a$10$QbecsekZ1fwP4aEjjC5OmuXZJt7jIsBaQO30PP4J7fUXlHam.t07S', NULL, NULL, 1);
INSERT INTO `mar_user` VALUES (5, '123', 'h', '$2a$10$Lskjf9Z1expJllve72sNzuOifjeoo0jMykzLFDClbHcNqo5FOAALy', '111', NULL, 1);
INSERT INTO `mar_user` VALUES (6, '456', NULL, '$2a$10$OXJLM01m7R2wwtALTWfqtuttM1onPgqIJILkdwe0/fKb2.Rjqlsz2', NULL, NULL, 1);
INSERT INTO `mar_user` VALUES (7, '', NULL, '$2a$10$gs4BaSxdhRSYLdfXTJvWOuZtap6sVTVCCjNOC9xydkuJPKLcgO6YO', NULL, NULL, 1);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 'show', 'all:show');
INSERT INTO `menu` VALUES (2, 'buy', 'common:buy');
INSERT INTO `menu` VALUES (3, 'sell', 'common:sell');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'common');
INSERT INTO `role` VALUES (2, 'admin');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1);
INSERT INTO `role_menu` VALUES (1, 2);
INSERT INTO `role_menu` VALUES (1, 3);
INSERT INTO `role_menu` VALUES (2, 1);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (3, 1);
INSERT INTO `user_role` VALUES (4, 1);
INSERT INTO `user_role` VALUES (5, 1);
INSERT INTO `user_role` VALUES (6, 1);
INSERT INTO `user_role` VALUES (7, 1);

SET FOREIGN_KEY_CHECKS = 1;
