/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : etps

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2016-07-06 08:16:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for c_industry
-- ----------------------------
DROP TABLE IF EXISTS `c_industry`;
CREATE TABLE `c_industry` (
  `code` int(11) NOT NULL AUTO_INCREMENT COMMENT '行业代码',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '行业名称',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for c_registration_authority
-- ----------------------------
DROP TABLE IF EXISTS `c_registration_authority`;
CREATE TABLE `c_registration_authority` (
  `code` int(11) NOT NULL AUTO_INCREMENT COMMENT '代码',
  `name` varchar(50) NOT NULL COMMENT '注册机关',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_attachment
-- ----------------------------
DROP TABLE IF EXISTS `t_attachment`;
CREATE TABLE `t_attachment` (
  `id` int(10) NOT NULL COMMENT '主键',
  `file_name` varchar(255) NOT NULL DEFAULT '' COMMENT '文件名',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `file_content` blob COMMENT '二进制文件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `t_enterprise`;
CREATE TABLE `t_enterprise` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '企业名称',
  `industry_code` tinyint(4) NOT NULL DEFAULT '0' COMMENT '行业',
  `legal_person` varchar(20) NOT NULL DEFAULT '' COMMENT '法人代表人',
  `reg_address` varchar(500) NOT NULL DEFAULT '' COMMENT '注册地址',
  `business_scope` varchar(1000) NOT NULL DEFAULT '' COMMENT '经营范围',
  `reg_auth_code` tinyint(2) NOT NULL DEFAULT '0' COMMENT '注册机关',
  `reg_date` date DEFAULT NULL COMMENT '注册日期',
  `phone` varchar(50) NOT NULL DEFAULT '' COMMENT '联系方式',
  `update_user_id` int(11) NOT NULL COMMENT '最后更新人',
  `update_time` datetime NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `number` varchar(20) DEFAULT NULL COMMENT '工号',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `last_login_ip` varchar(20) DEFAULT NULL COMMENT '最后登陆IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
