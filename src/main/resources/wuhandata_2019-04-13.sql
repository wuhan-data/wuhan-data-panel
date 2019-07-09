# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.21)
# Database: wuhandata
# Generation Time: 2019-04-13 02:33:43 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table analysis_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `analysis_detail`;

CREATE TABLE `analysis_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '版块id',
  `theme_id` varchar(64) NOT NULL COMMENT '所属栏目id',
  `theme_name` varchar(64) NOT NULL COMMENT '所属栏目名称',
  `class_name` varchar(64) NOT NULL COMMENT '版块名称',
  `indi_code` varchar(64) NOT NULL COMMENT '指标代码（by同方）',
  `freq_code` varchar(16) NOT NULL COMMENT '支持的频度 MM/SS/YY（by同方）',
  `start_time` varchar(16) NOT NULL COMMENT '允许查询的起始时间',
  `end_time` varchar(16) NOT NULL COMMENT '允许查询的结束时间',
  `show_type` varchar(64) NOT NULL DEFAULT 'table' COMMENT '展示类型 echarts/table/picture',
  `echarts_type` varchar(255) NOT NULL COMMENT '调用的echarts组件类型',
  `is_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否显示0-正常 1-不展示',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='经济分析指标内展示版块管理';



# Dump of table analysis_list
# ------------------------------------------------------------

DROP TABLE IF EXISTS `analysis_list`;

CREATE TABLE `analysis_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '栏目id',
  `type_name` varchar(64) NOT NULL COMMENT '一级栏目名称',
  `theme_name` varchar(255) NOT NULL COMMENT '栏目名称',
  `is_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否展示0-正常 1-不展示',
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=181 DEFAULT CHARSET=utf8mb4 COMMENT='经济分析栏目管理';

LOCK TABLES `analysis_list` WRITE;
/*!40000 ALTER TABLE `analysis_list` DISABLE KEYS */;

INSERT INTO `analysis_list` (`id`, `type_name`, `theme_name`, `is_show`, `weight`)
VALUES
	(1,'综合','PMI指数（全国）',0,9),
	(2,'综合','全社会用电量',0,9),
	(3,'综合','工业用电量',0,9),
	(4,'综合','铁路货运量',0,9),
	(5,'综合','金融机构新增贷款余额',0,9),
	(6,'综合','货运量增幅',0,9),
	(7,'综合','湖北PMI',0,9),
	(8,'综合','项目新开工个数增幅',0,9),
	(9,'综合','项目新开工投资增幅',0,9),
	(10,'综合','总体情况走势',0,9),
	(11,'综合','全国/主要省份（中部）比较',0,9),
	(12,'综合','三次产业增加值增速、占比',0,9),
	(13,'综合','各市（州）GDP总量、增速',0,9),
	(14,'综合','广义货币供应量',0,9),
	(15,'综合','城镇新增就业人数',0,9),
	(16,'综合','进出口总额及增速',0,9),
	(17,'综合','财政收入',0,9),
	(18,'综合','财政支出',0,9),
	(19,'综合','综合异动指标',0,9),
	(20,'综合','宏观经济监测综合预警指数',0,9),
	(21,'综合','GDP增速与用电量、总货运量、中长期贷款、财政收入支出、就业等对比',0,9),
	(22,'综合','全省经济形势分析报告',0,9),
	(23,'工业','工业信贷量及增速',0,8),
	(24,'工业','总货运量及增速',0,8),
	(25,'工业','工业用电量及增速',0,8),
	(26,'工业','PPI',0,8),
	(27,'工业','制造业PMI指数（全国）',0,8),
	(28,'工业','规模以上工业增加值增速总体情况及走势',0,8),
	(29,'工业','全国/主要省份（中部）比较',0,8),
	(30,'工业','工业增加值增速（分行业）',0,8),
	(31,'工业','工业增加值增速【分市（州）】',0,8),
	(32,'工业','主要工业产品产量及增速总体情况及走势',0,8),
	(33,'工业','分主要产品（汽车、钢铁、工业机器人等32小类）产量/增速\n',0,8),
	(35,'工业','产品销售收入及增速',0,8),
	(36,'工业','工业产销率及增速',0,8),
	(34,'工业','工业企业利润及增速',0,8),
	(37,'工业','企业亏损额及亏损面',0,8),
	(38,'工业','工业应收账款增速',0,8),
	(39,'工业','工业主营业务收入和企业利润增速',0,8),
	(40,'工业','工业异动指标',0,8),
	(41,'工业','规上工业增加值增速与各先行指标、结果指标对比(自主勾选)',0,8),
	(42,'工业','规上工业增加值增速监测、预测',0,8),
	(43,'工业','工业形势分析报告',0,8),
	(44,'农业','降雨量、霜冻天气',0,7),
	(45,'农业','日照时间、积温',0,7),
	(46,'农业','农业区划卫星遥感数据',0,7),
	(47,'农业','农业生产资料价格指数',0,7),
	(48,'农业','能繁母猪存栏数量',0,7),
	(49,'农业','化肥、农药、柴油采购量',0,7),
	(50,'农业','全年粮食总产量及增速',0,7),
	(51,'农业','农业增加值总量及增速',0,7),
	(52,'农业','主要农产品产量',0,7),
	(53,'农业','农产品加工业产值',0,7),
	(54,'农业','农业异动指标',0,7),
	(55,'农业','农业增加值总量、降雨量、日照时间',0,7),
	(56,'农业','农业经济形势分析报告',0,7),
	(57,'服务业','服务业固定资产投资及增速【分行业、分市（州）】',0,6),
	(58,'服务业','服务业用电量',0,6),
	(59,'服务业','服务业信贷量',0,6),
	(60,'服务业','服务业新增登记市场主体数量',0,6),
	(61,'服务业','服务业PMI指数（全国）',0,6),
	(62,'服务业','服务业增加值及增速',0,6),
	(63,'服务业','服务业增加值占GDP比重',0,6),
	(64,'服务业','规模以上服务业企业效益营业收入、成本',0,6),
	(65,'服务业','规模以上服务业企业效益利润总额及增速',0,6),
	(66,'服务业','规模以上服务业企业效益营业税金及附加',0,6),
	(67,'服务业','服务业异动指标',0,6),
	(68,'服务业','服务业增加值、服务业税收收入',0,6),
	(69,'服务业','服务业发展形势分析报告',0,6),
	(70,'投资','项目核准备案个数及金额',0,5),
	(71,'投资','投资地图',0,5),
	(72,'投资','招投标网站项目数（互联网大数据）',0,5),
	(73,'投资','项目环评个数',0,5),
	(74,'投资','国有建设用地出让面积（互联网大数据）',0,5),
	(75,'投资','中长期贷款余额（互联网大数据）',0,5),
	(76,'投资','土地招拍挂',0,5),
	(77,'投资','土地税收',0,5),
	(78,'投资','设备购置费',0,5),
	(79,'投资','施工项目个数及增速',0,5),
	(80,'投资','新开工项目个数及增速',0,5),
	(81,'投资','亿元以上项目情况',0,5),
	(82,'投资','10亿元以上项目情况',0,5),
	(83,'投资','雨雪冰冻天数',0,5),
	(84,'投资','固定资产投资额及增速【总量、分产业、分市（州）、全国、中部省份】',0,5),
	(85,'投资','三次产业投资占比及增速',0,5),
	(86,'投资','基础设施投资及增速【分市（州）、分行业】',0,5),
	(87,'投资','房地产投资及增速【分市（州）、分行业】',0,5),
	(88,'投资','技改投资及增速【分市（州）、分行业】',0,5),
	(89,'投资','民间投资及增速【分市（州）、分行业】',0,5),
	(90,'投资','投资异动指标',0,5),
	(91,'投资','GDP增速、固定资产投资增速与先行指标、税收等对比',0,5),
	(92,'投资','固定资产投资监测预警',0,5),
	(93,'投资','固定资产投资增速预测',0,5),
	(94,'投资','投资形势分析报告',0,5),
	(95,'消费','居民存款储蓄',0,4),
	(96,'消费','城乡居民人均可支配收入',0,4),
	(97,'消费','城镇居民平均工资水平',0,4),
	(98,'消费','CPI',0,4),
	(99,'消费','总体情况及走势【分行业、分市（州）】',0,4),
	(100,'消费','全国/主要省份（中部）比较',0,4),
	(101,'消费','网络零售额/占社零比重',0,4),
	(102,'消费','电子商务交易额',0,4),
	(103,'消费','商品房销售面积、销售额/增速',0,4),
	(104,'消费','汽车消费零售额/增速',0,4),
	(105,'消费','旅游业总收入',0,4),
	(106,'消费','全省商品房待售面积',0,4),
	(107,'消费','消费异动指标',0,4),
	(108,'消费','GDP、CPI、居民人均可支配收入、社零总额、网络零售额、电子商务交易额、旅游业总收入\n',0,4),
	(109,'消费','社会消费品零售总额预测预警',0,4),
	(110,'消费','消费形势分析报告',0,4),
	(111,'对外开放','民航出入境人数',0,3),
	(112,'对外开放','外商注册登记企业数',0,3),
	(113,'对外开放','企业出口收、付汇金额',0,3),
	(114,'对外开放','海关通关单量',0,3),
	(115,'对外开放','铁路货运量',0,3),
	(116,'对外开放','水路运输货物发送量',0,3),
	(117,'对外开放','劳务外派人数',0,3),
	(118,'对外开放','总体情况及走势【分行业、分市（州）】',0,3),
	(119,'对外开放','全国/主要省份（中部）比较',0,3),
	(120,'对外开放','新批外商投资项目规模/增速',0,3),
	(121,'对外开放','实际利用外资总额/增速',0,3),
	(122,'对外开放','合同外资金额/增速',0,3),
	(123,'对外开放','对外投资金额/增速',0,3),
	(124,'对外开放','引进省外项目数量和资金',0,3),
	(125,'对外开放','对外开放异动指标',0,3),
	(126,'对外开放','GDP、进出口总额、实际利用外资总额、企业境外投资金额',0,3),
	(127,'对外开放','进出口总额预测预警',0,3),
	(128,'对外开放','对外经济形势分析报告',0,3),
	(129,'新经济','新增企业数量（互联网大数据）',0,2),
	(130,'新经济','新经济网上就业岗位发布数、简历投档数',0,2),
	(131,'新经济','新经济信贷资金数',0,2),
	(132,'新经济','新经济行业风险投资比例（互联网大数据）',0,2),
	(133,'新经济','新经济行业岗位占比',0,2),
	(134,'新经济','每万人口发明专利拥有量',0,2),
	(135,'新经济','技术合同交易额',0,2),
	(136,'新经济','千人拥有研发人员数',0,2),
	(137,'新经济','R&D经费支出占GDP比重',0,2),
	(138,'新经济','发放大学生创业担保贷款数',0,2),
	(139,'新经济','创业板上市企业家数',0,2),
	(140,'新经济','高新技术产业增加值/增速--总体情况及走势【分行业、分市（州）】\n',0,2),
	(141,'新经济','高新技术产业增加值占GDP比重--总体情况及走势【分市（州）】\n',0,2),
	(142,'新经济','电子商务交易额/增速',0,2),
	(143,'新经济','高新技术产品认定数',0,2),
	(144,'新经济','高新技术制造业与规上工业增加值走势比较',0,2),
	(145,'新经济','新经济异动指标',0,2),
	(146,'新经济','GDP增速、高新技术产业增加增速、电子商务交易额增速',0,2),
	(147,'新经济','湖北新经济发展分析报告',0,2),
	(148,'新经济','高新技术产业增加值预测预警',0,2),
	(149,'绿色发展','单位生产总值能耗',0,1),
	(150,'绿色发展','单位生产总值全社会用电量',0,1),
	(151,'绿色发展','单位生产总值煤炭消耗量',0,1),
	(152,'绿色发展','单位生产总值二氧化碳排放量',0,1),
	(153,'绿色发展','主要河流断面水质劣V类比例',0,1),
	(154,'绿色发展','全省17个重点城市空气质量优良天数比例',0,1),
	(155,'绿色发展','污染源在线监测信息',0,1),
	(156,'绿色发展','水质自动监测信息（重点湖泊、湿地、河流水质）',0,1),
	(157,'绿色发展','全省17个重点城市主要污染物月均浓度',0,1),
	(158,'绿色发展','绿色发展异动指标',0,1),
	(159,'绿色发展','全社会用电量、煤炭消耗量、二氧化碳排放量、空气优良天数',0,1),
	(160,'绿色发展','绿色发展分析报告',0,1),
	(161,'民生','农村居民人均可支配收入及增速',0,0),
	(162,'民生','城镇新增就业人数',0,0),
	(163,'民生','城镇登记失业人数/失业率',0,0),
	(164,'民生','城乡居民人均可支配收入',0,0),
	(165,'民生','城乡居民基本养老保险参保人数',0,0),
	(166,'民生','城乡居民基本医疗保险参保人数',0,0),
	(167,'民生','低保人数',0,0),
	(168,'民生','网络求职申请人数与网络招聘岗位数量',0,0),
	(169,'民生','全省新开工保障性安居工程',0,0),
	(170,'民生','工业企业利润及增速',0,0),
	(171,'民生','监测企业岗位数',0,0),
	(172,'民生','企业稳岗补贴人数',0,0),
	(173,'民生','领取失业保险金人数',0,0),
	(174,'民生','民生异动指标',0,0),
	(175,'民生','GDP、城镇新增就业人数、城乡居民人均可支配收入、领取失业保险金人数\n',0,0),
	(176,'民生','就业形势分析报告',0,0),
	(179,'测试','测试数据',0,0),
	(180,'测试1','测试数据1',0,0);

/*!40000 ALTER TABLE `analysis_list` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table collect
# ------------------------------------------------------------

DROP TABLE IF EXISTS `collect`;

CREATE TABLE `collect` (
  `id` int(11) NOT NULL COMMENT '收藏id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `type` varchar(32) NOT NULL COMMENT '收藏的类型analysis/index',
  `index_id` int(11) NOT NULL COMMENT '指标数据id',
  `create_time` datetime NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';



# Dump of table error_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `error_log`;

CREATE TABLE `error_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '错误日志id',
  `func` varchar(255) NOT NULL COMMENT '功能模块',
  `code` varchar(15) NOT NULL COMMENT '错误代码',
  `msg` text NOT NULL COMMENT '错误信息',
  `data` text NOT NULL COMMENT '报错数据',
  `remark` text NOT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='错误日志表';



# Dump of table history_search
# ------------------------------------------------------------

DROP TABLE IF EXISTS `history_search`;

CREATE TABLE `history_search` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '历史搜索id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `keyword` varchar(255) NOT NULL COMMENT '搜索关键字',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='历史搜索表';



# Dump of table history_view
# ------------------------------------------------------------

DROP TABLE IF EXISTS `history_view`;

CREATE TABLE `history_view` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '历史浏览id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `func_name` varchar(64) NOT NULL COMMENT '功能模块名称',
  `title` varchar(64) NOT NULL COMMENT '浏览具体页面标题',
  `view_url` varchar(255) NOT NULL COMMENT '浏览url',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='历史浏览表';



# Dump of table index_list
# ------------------------------------------------------------

DROP TABLE IF EXISTS `index_list`;

CREATE TABLE `index_list` (
  `id` int(11) NOT NULL,
  `indi_code` varchar(64) NOT NULL COMMENT '指标代码',
  `indi_name` varchar(255) NOT NULL COMMENT '指标名称',
  `is_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '指标状态0-正常 1-不展示',
  `source` varchar(64) NOT NULL COMMENT '数据来源',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='管理指标';



# Dump of table panel_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `panel_user`;

CREATE TABLE `panel_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态0-正常 1-封禁',
  `role_list` text NOT NULL COMMENT '权限列表',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='后台用户表';



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态0-正常 1-封禁',
  `gender` tinyint(1) NOT NULL DEFAULT '9' COMMENT '性别0-女 1-男 9-未知',
  `tel` varchar(32) NOT NULL COMMENT '电话号码',
  `real_name` varchar(64) NOT NULL COMMENT '真实姓名',
  `role_list` text NOT NULL COMMENT '权限列表',
  `role_name` varchar(64) NOT NULL COMMENT '权限名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='用户表';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
