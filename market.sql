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

 Date: 30/05/2022 22:16:53
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
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
