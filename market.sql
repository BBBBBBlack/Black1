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

 Date: 05/06/2022 19:33:03
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
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mar_bill
-- ----------------------------
INSERT INTO `mar_bill` VALUES (14, 8, 1, '2022-05-30 01:51:40', NULL, 0, 24);
INSERT INTO `mar_bill` VALUES (15, 61, 1, '2022-05-30 01:54:06', '2022-05-30 01:55:20', 1, 26);
INSERT INTO `mar_bill` VALUES (14, 1, 1, '2022-06-04 21:27:45', NULL, 0, 27);
INSERT INTO `mar_bill` VALUES (14, 62, 1, '2022-06-04 21:28:01', NULL, 0, 28);

-- ----------------------------
-- Table structure for mar_check
-- ----------------------------
DROP TABLE IF EXISTS `mar_check`;
CREATE TABLE `mar_check`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `goods_id` bigint NULL DEFAULT NULL,
  `check_time` datetime(0) NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mar_check
-- ----------------------------
INSERT INTO `mar_check` VALUES (44, 52, '2022-05-30 00:50:07', 12, 1);
INSERT INTO `mar_check` VALUES (45, 53, '2022-05-30 00:50:11', 12, 1);
INSERT INTO `mar_check` VALUES (46, 54, '2022-05-30 00:50:14', 12, -1);
INSERT INTO `mar_check` VALUES (47, 55, '2022-05-30 00:50:19', 13, -1);
INSERT INTO `mar_check` VALUES (48, 57, '2022-05-30 01:42:57', 13, -1);
INSERT INTO `mar_check` VALUES (49, 58, '2022-05-30 01:43:00', 13, 1);
INSERT INTO `mar_check` VALUES (50, 56, '2022-05-30 01:43:04', 13, -1);
INSERT INTO `mar_check` VALUES (51, 59, '2022-05-30 01:43:06', 12, 1);
INSERT INTO `mar_check` VALUES (52, 60, '2022-05-30 01:52:40', 14, -1);
INSERT INTO `mar_check` VALUES (53, 61, '2022-05-30 01:52:43', 14, 1);
INSERT INTO `mar_check` VALUES (54, 62, '2022-05-30 01:54:54', 15, 1);
INSERT INTO `mar_check` VALUES (55, 64, '2022-05-30 01:55:00', 15, 1);
INSERT INTO `mar_check` VALUES (56, 63, '2022-05-30 01:55:02', 15, -1);
INSERT INTO `mar_check` VALUES (57, 65, '2022-06-04 21:13:31', 14, -1);
INSERT INTO `mar_check` VALUES (58, 66, '2022-06-04 21:44:24', 14, 1);
INSERT INTO `mar_check` VALUES (59, 67, '2022-06-04 22:25:33', 14, -1);
INSERT INTO `mar_check` VALUES (60, 68, '2022-06-04 22:26:24', 14, -1);
INSERT INTO `mar_check` VALUES (61, 69, '2022-06-04 22:30:11', 14, -1);
INSERT INTO `mar_check` VALUES (62, 70, '2022-06-04 22:30:18', 14, -1);
INSERT INTO `mar_check` VALUES (63, 71, '2022-06-04 22:31:33', 14, -1);
INSERT INTO `mar_check` VALUES (64, 72, '2022-06-04 22:39:42', 14, -1);
INSERT INTO `mar_check` VALUES (65, 74, '2022-06-04 22:57:16', 15, -1);
INSERT INTO `mar_check` VALUES (66, 75, '2022-06-04 23:13:53', 15, -1);
INSERT INTO `mar_check` VALUES (67, 73, '2022-06-04 23:44:16', 14, 1);
INSERT INTO `mar_check` VALUES (68, 76, '2022-06-04 23:44:19', 15, 1);
INSERT INTO `mar_check` VALUES (69, 77, '2022-06-04 23:44:22', 15, 1);
INSERT INTO `mar_check` VALUES (70, 78, '2022-06-04 23:45:46', 15, 1);
INSERT INTO `mar_check` VALUES (71, 79, '2022-06-05 11:04:19', 15, 1);

-- ----------------------------
-- Table structure for mar_comment
-- ----------------------------
DROP TABLE IF EXISTS `mar_comment`;
CREATE TABLE `mar_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `goods_id` bigint NULL DEFAULT NULL COMMENT '所评论商品ID',
  `root_id` bigint NULL DEFAULT -1 COMMENT '根评论ID，若该评论为根评论，root_id=-1',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `to_comment_user_id` bigint NULL DEFAULT -1 COMMENT '所回复的用户的ID,若该评论为根评论，to_comment_user_id=-1',
  `create_by` bigint NULL DEFAULT NULL COMMENT '发评论人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建评论时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mar_comment
-- ----------------------------
INSERT INTO `mar_comment` VALUES (1, 1, -1, '真tm好', -1, 5, '2022-05-22 12:34:06');
INSERT INTO `mar_comment` VALUES (2, 1, -1, '咋的了', -1, 5, '2022-05-22 12:35:23');
INSERT INTO `mar_comment` VALUES (3, 1, -1, '什么玩意儿', -1, 5, '2022-05-22 12:40:01');
INSERT INTO `mar_comment` VALUES (4, 1, 1, '你是托吧', 5, 1, '2022-05-22 15:35:48');
INSERT INTO `mar_comment` VALUES (5, 1, 1, '你是卖家问我是不是你的托？', 1, 5, '2022-05-24 18:17:10');
INSERT INTO `mar_comment` VALUES (6, 8, -1, '这是人贩子？', NULL, 1, '2022-05-24 18:26:19');

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
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `mar_goods` VALUES (52, '123-1', 0x313233, 1, 1, '/file/0f814a1eed844eecb483f136bc464eef.png', 1);
INSERT INTO `mar_goods` VALUES (53, '123-2', 0x3132332D32, 1, 1, '/file/de16a3275bae46639060f8b985675f99.png', 1);
INSERT INTO `mar_goods` VALUES (54, '123-3', 0x3132332D33, 1, 1, '/file/f2b90a2ae1de44b2b3fa82814bc3925d.png', -1);
INSERT INTO `mar_goods` VALUES (55, '456-1', 0x3435362D31, 456, 1, '/file/4027d5359ad64fa985f5be5978802800.png', -1);
INSERT INTO `mar_goods` VALUES (56, '456-2', 0x32, 2, 1, '/file/7b39de2942d7433aa1fa22ff397e5276.png', -1);
INSERT INTO `mar_goods` VALUES (57, '456-3', 0x31, 1, 1, '/file/23c61165abca43fd8cd4e2144a0c741b.png', -1);
INSERT INTO `mar_goods` VALUES (58, '456-4', 0x31, 1, 1, '/file/310d554f522c4c1fa3f450c9026086a1.png', 1);
INSERT INTO `mar_goods` VALUES (59, '11', 0x31, 1, 1, '/file/92177e97817a45ce8479a37a07d56df5.png', 1);
INSERT INTO `mar_goods` VALUES (60, '11', 0x31, 1, 1, '/file/ca1ea20bb1664df08be9a031624e6ecd.png', -1);
INSERT INTO `mar_goods` VALUES (61, '12', 0x3132, 1, 0, '/file/7c2682671a424af1a05a005593f4b977.png', 1);
INSERT INTO `mar_goods` VALUES (62, '211', 0x32, 2, 1, '/file/f232fb6a7ea342f39d0c694c14d16b71.png', 1);
INSERT INTO `mar_goods` VALUES (63, '22', 0x32, 2, 1, '/file/1978413a8abf41a39222ada24148b547.png', -1);
INSERT INTO `mar_goods` VALUES (64, '23', 0x3232, 2, 1, '/file/3004c63515254cde928d84bc033aac33.png', 1);
INSERT INTO `mar_goods` VALUES (65, '1234', 0x31323334, 12, 1, '/file/b0d0394593954a07b93ba2580c41f3cb.png', -1);
INSERT INTO `mar_goods` VALUES (66, 'jinri', 0x6A, 1, 1, '/file/4beca5a2b90d4872b16e88bb23351929.png', 1);
INSERT INTO `mar_goods` VALUES (67, '111', 0x31, 1, 1, '/file/722f0331ac374d89b5db6d0b1bc6045a.png', -1);
INSERT INTO `mar_goods` VALUES (68, '1', 0x31, 1, 1, '/file/7199cadabe8c44338e18dc99ef44f380.png', -1);
INSERT INTO `mar_goods` VALUES (69, '1', 0x31, 1, 1, '/file/88b2a8cd228a4adaa7858a3c7ea0f93c.png', -1);
INSERT INTO `mar_goods` VALUES (70, '2', 0x31, 1, 1, '/file/3ded43aa713a4ee39d29124d0955ea0c.png', -1);
INSERT INTO `mar_goods` VALUES (71, '3', 0x31, 1, 1, '/file/e4afa79ee3754e17a8500fc07b1799a9.png', -1);
INSERT INTO `mar_goods` VALUES (72, '4', 0x31, 1, 1, '/file/f37fc8b14c264de5bcc0731664909fb3.png', -1);
INSERT INTO `mar_goods` VALUES (73, '5', 0x31, 1, 1, '/file/a30001bef7cb4173ac6f006e769d05c0.png', 1);
INSERT INTO `mar_goods` VALUES (74, '2', 0x32, 2, 1, '/file/89100516429649ffabbafb1d6c1b6438.png', -1);
INSERT INTO `mar_goods` VALUES (75, 'ceshi', 0x31, 1, 1, '/file/f2600f590dda44b88c6989f37a2a5fa3.png', -1);
INSERT INTO `mar_goods` VALUES (76, '21', 0x3232, 2, 1, '/file/e02f4b125099405891be0b14f3977120.png', 1);
INSERT INTO `mar_goods` VALUES (77, '22', 0x32, 2, 1, '/file/282bf550e5ec488e8d598935ffb15f98.png', 1);
INSERT INTO `mar_goods` VALUES (78, '今日上新', 0x31, 1, 1, '/file/8567318a66a64ad2bfb1ea18042c94f2.png', 1);
INSERT INTO `mar_goods` VALUES (79, '1', 0x31, 1, 1, '/file/9eb6b11e2fe24717a8f225d780867806.png', 1);

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
INSERT INTO `mar_submit` VALUES (12, 52, '2022-05-30 00:47:19');
INSERT INTO `mar_submit` VALUES (12, 53, '2022-05-30 00:47:31');
INSERT INTO `mar_submit` VALUES (12, 54, '2022-05-30 00:47:43');
INSERT INTO `mar_submit` VALUES (12, 59, '2022-05-30 01:24:02');
INSERT INTO `mar_submit` VALUES (13, 55, '2022-05-30 00:48:40');
INSERT INTO `mar_submit` VALUES (13, 56, '2022-05-30 00:48:51');
INSERT INTO `mar_submit` VALUES (13, 57, '2022-05-30 00:49:02');
INSERT INTO `mar_submit` VALUES (13, 58, '2022-05-30 00:49:11');
INSERT INTO `mar_submit` VALUES (14, 60, '2022-05-30 01:51:54');
INSERT INTO `mar_submit` VALUES (14, 61, '2022-05-30 01:52:03');
INSERT INTO `mar_submit` VALUES (14, 65, '2022-06-04 21:09:21');
INSERT INTO `mar_submit` VALUES (14, 66, '2022-06-04 21:42:44');
INSERT INTO `mar_submit` VALUES (14, 67, '2022-06-04 22:24:41');
INSERT INTO `mar_submit` VALUES (14, 68, '2022-06-04 22:26:02');
INSERT INTO `mar_submit` VALUES (14, 69, '2022-06-04 22:29:07');
INSERT INTO `mar_submit` VALUES (14, 70, '2022-06-04 22:29:18');
INSERT INTO `mar_submit` VALUES (14, 71, '2022-06-04 22:29:26');
INSERT INTO `mar_submit` VALUES (14, 72, '2022-06-04 22:29:39');
INSERT INTO `mar_submit` VALUES (14, 73, '2022-06-04 22:29:48');
INSERT INTO `mar_submit` VALUES (15, 62, '2022-05-30 01:53:44');
INSERT INTO `mar_submit` VALUES (15, 63, '2022-05-30 01:53:51');
INSERT INTO `mar_submit` VALUES (15, 64, '2022-05-30 01:53:59');
INSERT INTO `mar_submit` VALUES (15, 74, '2022-06-04 22:56:35');
INSERT INTO `mar_submit` VALUES (15, 75, '2022-06-04 23:12:48');
INSERT INTO `mar_submit` VALUES (15, 76, '2022-06-04 23:43:52');
INSERT INTO `mar_submit` VALUES (15, 77, '2022-06-04 23:44:00');
INSERT INTO `mar_submit` VALUES (15, 78, '2022-06-04 23:45:28');
INSERT INTO `mar_submit` VALUES (15, 79, '2022-06-05 11:03:47');

-- ----------------------------
-- Table structure for mar_suggest
-- ----------------------------
DROP TABLE IF EXISTS `mar_suggest`;
CREATE TABLE `mar_suggest`  (
  `goods_id` bigint NULL DEFAULT NULL,
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `release_time` datetime(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mar_suggest
-- ----------------------------
INSERT INTO `mar_suggest` VALUES (60, '未通过', '2022-06-04 09:43:01');
INSERT INTO `mar_suggest` VALUES (63, '未通过', '2022-06-04 10:09:45');
INSERT INTO `mar_suggest` VALUES (68, '', '2022-06-04 22:28:01');
INSERT INTO `mar_suggest` VALUES (68, '', '2022-06-04 22:28:38');
INSERT INTO `mar_suggest` VALUES (71, '', '2022-06-04 22:32:02');
INSERT INTO `mar_suggest` VALUES (71, '', '2022-06-04 22:32:18');
INSERT INTO `mar_suggest` VALUES (71, '', '2022-06-04 22:33:00');
INSERT INTO `mar_suggest` VALUES (71, '', '2022-06-04 22:34:02');
INSERT INTO `mar_suggest` VALUES (71, '', '2022-06-04 22:35:49');
INSERT INTO `mar_suggest` VALUES (71, '', '2022-06-04 22:35:55');
INSERT INTO `mar_suggest` VALUES (72, '', '2022-06-04 22:39:42');
INSERT INTO `mar_suggest` VALUES (72, 'no', '2022-06-04 22:39:58');
INSERT INTO `mar_suggest` VALUES (74, '不通过', '2022-06-04 22:57:16');
INSERT INTO `mar_suggest` VALUES (75, 'mingzaiyouwenti', '2022-06-04 23:13:53');

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
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mar_user
-- ----------------------------
INSERT INTO `mar_user` VALUES (1, 'abc', 'n', '$2a$10$lbB1EJuKxK7zPxPcrmq/tuCzQHAwzLp/SQTHdpT6sVjZEr1YyDL8C', 'c', '/file/ca08135c491b41fd99d37e0248cc4abd.jpg', 1);
INSERT INTO `mar_user` VALUES (2, 'def', 'bb', '$2a$10$a4d9WFnytsKcvOBSOBYrfO3vgsUisAxeoPlz3E9BSu4MQYmwEGBcy', '17850426783', NULL, 1);
INSERT INTO `mar_user` VALUES (12, '123', '123', '$2a$10$pvmYVYyDayqH43gqwJKG7ecK/nBYYPwyLkwxYkB8Qiq5s.cIGqFUG', '123', NULL, 1);
INSERT INTO `mar_user` VALUES (13, '456', NULL, '$2a$10$JNPQvGMh62ZvHusqu7vlCOWb0DAzXR2KTewe60oNkbD7wV9FaTgLq', NULL, NULL, 1);
INSERT INTO `mar_user` VALUES (14, '1', '1', '$2a$10$k2R9pgiwJQ84m05/kMIwQOcePioacPWp9tlqsHbx0vgrie8GRvf36', '1', NULL, 0);
INSERT INTO `mar_user` VALUES (15, '2', '2', '$2a$10$1dwMNKm4LG9rdjEJfN5ffOLEfCIoJo.dIiBXDLEbgJBG4KhvMX1q2', '2', NULL, 1);
INSERT INTO `mar_user` VALUES (16, '5', NULL, '$2a$10$Hj/IgBA2pw/wes9iEQk4NeVVwZVO7wf3IBSpTxAp81FyTXFrXXV2y', NULL, NULL, 1);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 'show', 'all:show');
INSERT INTO `menu` VALUES (2, 'buy', 'common:buy');
INSERT INTO `menu` VALUES (3, 'sell', 'common:sell');
INSERT INTO `menu` VALUES (4, 'check', 'admin:check');
INSERT INTO `menu` VALUES (5, 'show_all_status_goods', 'admin:show_all');
INSERT INTO `menu` VALUES (6, 'show_all_goods_details', 'admin:show_details');
INSERT INTO `menu` VALUES (7, 'show_banned_record', 'admin:show_banned');
INSERT INTO `menu` VALUES (8, 'show_own_submit', 'common:show_submit');
INSERT INTO `menu` VALUES (9, 'add_suggest', 'admin:add_suggest');
INSERT INTO `menu` VALUES (10, 'show_own_goods_suggest', 'common:show_suggest');

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
INSERT INTO `role_menu` VALUES (1, 8);
INSERT INTO `role_menu` VALUES (1, 10);
INSERT INTO `role_menu` VALUES (2, 1);
INSERT INTO `role_menu` VALUES (2, 4);
INSERT INTO `role_menu` VALUES (2, 5);
INSERT INTO `role_menu` VALUES (2, 6);
INSERT INTO `role_menu` VALUES (2, 7);
INSERT INTO `role_menu` VALUES (2, 9);

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
INSERT INTO `user_role` VALUES (12, 1);
INSERT INTO `user_role` VALUES (13, 1);
INSERT INTO `user_role` VALUES (14, 1);
INSERT INTO `user_role` VALUES (15, 1);
INSERT INTO `user_role` VALUES (16, 1);

SET FOREIGN_KEY_CHECKS = 1;
