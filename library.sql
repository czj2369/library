/*
 Navicat MySQL Data Transfer

 Source Server         : TestDB
 Source Server Type    : MySQL
 Source Server Version : 50560
 Source Host           : localhost:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 50560
 File Encoding         : 65001

 Date: 14/12/2018 21:46:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `adminID` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `adminUser` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `adminPass` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`adminID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('100001', 'admin', 'admin123');
INSERT INTO `admin` VALUES ('100002', 'anjian', 'czj1542');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `bookID` int(11) NOT NULL AUTO_INCREMENT COMMENT '图书编号',
  `bookName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书名称',
  `bookAuthor` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书作者',
  `bookPress` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书出版社',
  `bookType` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书类型',
  `bookRest` int(11) NOT NULL COMMENT '图书库存量',
  `bookSum` int(11) NOT NULL COMMENT '图书总量',
  `bookNum` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书索书号',
  PRIMARY KEY (`bookID`) USING BTREE,
  UNIQUE INDEX `bookNumUnique`(`bookNum`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (3, '计算机网络', '吴功宜', '清华大学出版社', '计算机', 8, 10, '3');
INSERT INTO `book` VALUES (4, '地球科学导论', '刘本培, 蔡运龙主编', '	高等教育出版社 2000', '天文学', 5, 20, 'P183/JC1');
INSERT INTO `book` VALUES (5, '金融学原理.第5版', '彭兴韵著', '	格致出版社 2013', '经济', 60, 72, 'F830/JC22(5D)');
INSERT INTO `book` VALUES (8, '追风筝的人', '(美)卡勒德·胡赛尼(Khaled Hosseini)著', '上海人民出版社', '文学', 18, 20, 'I712.45/1225');
INSERT INTO `book` VALUES (9, '挪威的森林', '(日)村上春树著', '上海译文出版社', '文学', 29, 30, 'I313.45/141');

-- ----------------------------
-- Table structure for bookbook
-- ----------------------------
DROP TABLE IF EXISTS `bookbook`;
CREATE TABLE `bookbook`  (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `userID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bookBookName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bookBookNum` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bookTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bookbook
-- ----------------------------
INSERT INTO `bookbook` VALUES (8, 'reader1', '金融学原理.第5版', 'F830/JC22(5D)', '2018-12-06 00:00:00');
INSERT INTO `bookbook` VALUES (9, 'reader1', '追风筝的人', 'I712.45/1225', '2018-12-05 00:00:00');
INSERT INTO `bookbook` VALUES (11, 'reader2', '挪威的森林', 'I313.45/141', '2018-12-04 00:00:00');

-- ----------------------------
-- Table structure for bookrecommen
-- ----------------------------
DROP TABLE IF EXISTS `bookrecommen`;
CREATE TABLE `bookrecommen`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bookauthor` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bookpress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bookreason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bookrecommen
-- ----------------------------
INSERT INTO `bookrecommen` VALUES (1, '暗恋橘生淮南', '八月长安[著]', '湖南文艺出版社 2014', '挺好看的！');
INSERT INTO `bookrecommen` VALUES (2, '圣殿春秋', '(英)肯·福莱特(Ken Follett)著', '译文出版社 2017', '听说不错');
INSERT INTO `bookrecommen` VALUES (3, '最好的我们', '八月长安[著]', '湖南文艺出版社 2013', '想看');

-- ----------------------------
-- Table structure for finepay
-- ----------------------------
DROP TABLE IF EXISTS `finepay`;
CREATE TABLE `finepay`  (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `userID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fineReason` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fineTime` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fineMoney` float NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of finepay
-- ----------------------------
INSERT INTO `finepay` VALUES (1, '16251104133', '图书损坏', '2018.9.1', 100);
INSERT INTO `finepay` VALUES (2, '16251104133', '破坏公物', '2018.10.3', 999);
INSERT INTO `finepay` VALUES (3, 'reader1', '图书过期', '2018-12-02', 1);
INSERT INTO `finepay` VALUES (4, 'reader1', '图书过期', '2018-12-02', 2.15);

-- ----------------------------
-- Table structure for freeback
-- ----------------------------
DROP TABLE IF EXISTS `freeback`;
CREATE TABLE `freeback`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of freeback
-- ----------------------------
INSERT INTO `freeback` VALUES (1, '不错，还可以', 'reader1');
INSERT INTO `freeback` VALUES (2, '我来反馈下', 'reader1');
INSERT INTO `freeback` VALUES (3, '我也来反馈下', 'reader2');

-- ----------------------------
-- Table structure for historybook
-- ----------------------------
DROP TABLE IF EXISTS `historybook`;
CREATE TABLE `historybook`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hbookNum` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hbookName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hbookAuthor` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hbookPress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hbookType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hbooklendTime` datetime NULL DEFAULT NULL,
  `hbookbackTime` datetime NULL DEFAULT NULL,
  `readerID` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of historybook
-- ----------------------------
INSERT INTO `historybook` VALUES (1, 'P183/JC1', '地球科学导论', '刘本培, 蔡运龙主编', '	高等教育出版社 2000', '天文学', '2018-12-02 14:52:20', '2018-12-06 10:35:39', '16251104133');
INSERT INTO `historybook` VALUES (2, 'I712.45/1225', '追风筝的人', '(美)卡勒德·胡赛尼(Khaled Hosseini)著', '上海人民出版社', '文学', '2018-12-02 14:57:18', '2018-12-06 10:37:11', '16251104134');
INSERT INTO `historybook` VALUES (3, 'I313.45/141', '挪威的森林', '(日)村上春树著', '上海译文出版社', '文学', '2018-12-02 14:49:33', '2018-12-06 10:37:48', '16251101133');
INSERT INTO `historybook` VALUES (4, 'I712.45/1225', '追风筝的人', '(美)卡勒德·胡赛尼(Khaled Hosseini)著', '上海人民出版社', '文学', '2018-08-06 10:38:25', '2018-12-06 10:38:29', '16251104132');
INSERT INTO `historybook` VALUES (5, 'P183/JC1', '地球科学导论', '刘本培, 蔡运龙主编', '	高等教育出版社 2000', '天文学', '2018-12-06 10:44:09', '2018-12-06 10:44:19', '16251104133');
INSERT INTO `historybook` VALUES (6, 'I313.45/141', '挪威的森林', '(日)村上春树著', '上海译文出版社', '文学', '2018-08-06 10:44:35', '2018-12-06 10:44:55', '16251104131');
INSERT INTO `historybook` VALUES (7, 'P183/JC1', '地球科学导论', '刘本培, 蔡运龙主编', '	高等教育出版社 2000', '天文学', '2017-12-20 10:45:47', '2018-12-06 10:47:09', '16251104132');
INSERT INTO `historybook` VALUES (8, 'F830/JC22(5D)', '金融学原理.第5版', '彭兴韵著', '	格致出版社 2013', '经济', '2018-12-08 21:05:14', '2018-12-08 21:05:29', '16251104135');
INSERT INTO `historybook` VALUES (9, 'I712.45/1225', '追风筝的人', '(美)卡勒德·胡赛尼(Khaled Hosseini)著', '上海人民出版社', '文学', '2018-12-06 10:44:44', '2018-12-13 08:22:55', '16251104133');

-- ----------------------------
-- Table structure for lendbook
-- ----------------------------
DROP TABLE IF EXISTS `lendbook`;
CREATE TABLE `lendbook`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookNum` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `readerID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `lendTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lendbook
-- ----------------------------
INSERT INTO `lendbook` VALUES (9, 'I712.45/1225', '16251104132', '2018-12-02 14:58:49');
INSERT INTO `lendbook` VALUES (15, 'P183/JC1', '16251104132', '2018-12-13 10:39:12');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL,
  `noticeContent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '预约图书记得及时去图书馆前台凭读书证拿取！！！!!!!');

-- ----------------------------
-- Table structure for nowuser
-- ----------------------------
DROP TABLE IF EXISTS `nowuser`;
CREATE TABLE `nowuser`  (
  `nowUser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nowID` int(11) NOT NULL,
  `nowUserID` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of nowuser
-- ----------------------------
INSERT INTO `nowuser` VALUES ('reader1', 1, '16251104133');

-- ----------------------------
-- Table structure for outhistory
-- ----------------------------
DROP TABLE IF EXISTS `outhistory`;
CREATE TABLE `outhistory`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `outNum` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `outReason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `outTime` datetime NULL DEFAULT NULL,
  `outAmount` int(11) NULL DEFAULT NULL,
  `outName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of outhistory
-- ----------------------------
INSERT INTO `outhistory` VALUES (1, '3', '就想出', '2018-12-13 17:23:58', 1, NULL);
INSERT INTO `outhistory` VALUES (2, '3', '损坏', '2018-12-14 18:30:18', 1, '计算机网络');
INSERT INTO `outhistory` VALUES (3, 'P183/JC1', '书籍出现损坏', '2018-12-14 21:42:52', 2, '地球科学导论');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `readerID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '读者证号',
  `username` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '读者证状态',
  PRIMARY KEY (`userID`) USING BTREE,
  UNIQUE INDEX `readerID`(`readerID`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '16251104133', 'reader1', '123456', '读者1', '男', '123@163.com', '13632111257', '挂失');
INSERT INTO `user` VALUES (2, NULL, 'reader2', 'zzzxxx', '安见', '男', '11111456@qq.com', '15964524444', '正常');
INSERT INTO `user` VALUES (3, NULL, 'reader3', 'abc123', '读者2', '女', '799@qq.com', '14652365541', '正常');
INSERT INTO `user` VALUES (4, '16251104134', 'reader5', '1542123', '读者5', '', NULL, NULL, '正常');
INSERT INTO `user` VALUES (7, '16251104132', 'reader6', '123456', '111', '男', NULL, NULL, '正常');

SET FOREIGN_KEY_CHECKS = 1;
