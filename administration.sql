/*
 Navicat Premium Data Transfer

 Source Server         : 20220110
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : administration

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 17/01/2022 14:02:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goods_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_price` int(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('001', 'Alohomora', 50);
INSERT INTO `goods` VALUES ('002', 'AvadaKedavra', 10000);
INSERT INTO `goods` VALUES ('003', 'Anapneo', 20);
INSERT INTO `goods` VALUES ('004', 'Muffliato', 60);
INSERT INTO `goods` VALUES ('005', 'Flagrate', 30);
INSERT INTO `goods` VALUES ('006', 'Expelliarmus', 3000);
INSERT INTO `goods` VALUES ('007', 'Levicorpus', 1000);
INSERT INTO `goods` VALUES ('008', 'Impervius', 40);
INSERT INTO `goods` VALUES ('009', 'Accio', 500);
INSERT INTO `goods` VALUES ('010', 'Expulso', 5000);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_date` datetime(0) NULL DEFAULT NULL,
  `goods_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
