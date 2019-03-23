/*
Navicat MySQL Data Transfer

Source Server         : 刘柏江
Source Server Version : 50714
Source Host           : 192.168.1.67:3306
Source Database       : yrerp

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-09-25 17:49:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `createEmpno` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sup_code` varchar(255) NOT NULL,
  `updateEmpno` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createEmp` varchar(255) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '111', 'LV', '2018-09-25 10:55:19', 'AA', '0', null, null, null, null, null);

-- ----------------------------
-- Table structure for depot
-- ----------------------------
DROP TABLE IF EXISTS `depot`;
CREATE TABLE `depot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addr` varchar(255) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `createEmpno` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `job_num` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `updateEmpno` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_53qb7yd3gvorv3hvohotxs6hx` (`code`),
  UNIQUE KEY `UK_aqchf57lxyp9d8w3hohbvpwvm` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of depot
-- ----------------------------
INSERT INTO `depot` VALUES ('1', '长沙', '123456', '吕', '2018-09-25 16:42:17', '123456', '一号仓库', null, null);

-- ----------------------------
-- Table structure for icon
-- ----------------------------
DROP TABLE IF EXISTS `icon`;
CREATE TABLE `icon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fontClass` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `unicode` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_i2ui5ceebv3lg0x5l98ex0je8` (`fontClass`),
  UNIQUE KEY `UK_fffogi1b9bhcwih8x0aer9kql` (`unicode`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of icon
-- ----------------------------
INSERT INTO `icon` VALUES ('1', 'layui-icon-rate-half', '半星', '&#xe6c9;');
INSERT INTO `icon` VALUES ('2', 'layui-icon-rate', '星星-空心', '&#xe67b;');
INSERT INTO `icon` VALUES ('3', 'layui-icon-rate-solid', '星星-实心', '&#xe67a;');
INSERT INTO `icon` VALUES ('4', 'layui-icon-cellphone', '手机', '&#xe678;');
INSERT INTO `icon` VALUES ('5', 'layui-icon-vercode', '验证码', '&#xe679;');
INSERT INTO `icon` VALUES ('6', 'layui-icon-login-wechat', '微信', '&#xe677;');
INSERT INTO `icon` VALUES ('7', 'layui-icon-login-qq', 'QQ', '&#xe676;');
INSERT INTO `icon` VALUES ('8', 'layui-icon-login-weibo', '微博', '&#xe675;');
INSERT INTO `icon` VALUES ('9', 'layui-icon-password', '密码', '&#xe673;');
INSERT INTO `icon` VALUES ('10', 'layui-icon-username', '用户名', '&#xe66f;');
INSERT INTO `icon` VALUES ('11', 'layui-icon-refresh-3', '刷新-粗', '&#xe9aa;');
INSERT INTO `icon` VALUES ('12', 'layui-icon-auz', '授权', '&#xe672;');
INSERT INTO `icon` VALUES ('13', 'layui-icon-spread-left', '左向右伸缩菜单', '&#xe66b;');
INSERT INTO `icon` VALUES ('14', 'layui-icon-shrink-right', '右向左伸缩菜单', '&#xe668;');
INSERT INTO `icon` VALUES ('15', 'layui-icon-snowflake', '雪花', '&#xe6b1;');
INSERT INTO `icon` VALUES ('16', 'layui-icon-tips', '提示说明', '&#xe702;');
INSERT INTO `icon` VALUES ('17', 'layui-icon-note', '便签', '&#xe66e;');
INSERT INTO `icon` VALUES ('18', 'layui-icon-home', '主页', '&#xe68e;');
INSERT INTO `icon` VALUES ('19', 'layui-icon-senior', '高级', '&#xe674;');
INSERT INTO `icon` VALUES ('20', 'layui-icon-refresh', '刷新', '&#xe669;');
INSERT INTO `icon` VALUES ('21', 'layui-icon-refresh-1', '刷新', '&#xe666;');
INSERT INTO `icon` VALUES ('22', 'layui-icon-flag', '旗帜', '&#xe66c;');
INSERT INTO `icon` VALUES ('23', 'layui-icon-theme', '主题', '&#xe66a;');
INSERT INTO `icon` VALUES ('24', 'layui-icon-notice', '消息-通知', '&#xe667;');
INSERT INTO `icon` VALUES ('25', 'layui-icon-website', '网站', '&#xe7ae;');
INSERT INTO `icon` VALUES ('26', 'layui-icon-console', '控制台', '&#xe665;');
INSERT INTO `icon` VALUES ('27', 'layui-icon-face-surprised', '表情-惊讶', '&#xe664;');
INSERT INTO `icon` VALUES ('28', 'layui-icon-set', '设置-空心', '&#xe716;');
INSERT INTO `icon` VALUES ('29', 'layui-icon-template-1', '模板', '&#xe656;');
INSERT INTO `icon` VALUES ('30', 'layui-icon-app', '应用', '&#xe653;');
INSERT INTO `icon` VALUES ('31', 'layui-icon-template', '模板', '&#xe663;');
INSERT INTO `icon` VALUES ('32', 'layui-icon-praise', '赞', '&#xe6c6;');
INSERT INTO `icon` VALUES ('33', 'layui-icon-tread', '踩', '&#xe6c5;');
INSERT INTO `icon` VALUES ('34', 'layui-icon-male', '男', '&#xe662;');
INSERT INTO `icon` VALUES ('35', 'layui-icon-female', '女', '&#xe661;');
INSERT INTO `icon` VALUES ('36', 'layui-icon-camera', '相机-空心', '&#xe660;');
INSERT INTO `icon` VALUES ('37', 'layui-icon-camera-fill', '相机-实心', '&#xe65d;');
INSERT INTO `icon` VALUES ('38', 'layui-icon-more', '菜单-水平', '&#xe65f;');
INSERT INTO `icon` VALUES ('39', 'ayui-icon-more-vertical', '菜单-垂直', '&#xe671;');
INSERT INTO `icon` VALUES ('40', 'layui-icon-rmb', '金额-人民币', '&#xe65e;');
INSERT INTO `icon` VALUES ('41', 'layui-icon-dollar', '金额-美元', '&#xe659;');
INSERT INTO `icon` VALUES ('42', 'layui-icon-diamond', '钻石-等级', '&#xe735;');
INSERT INTO `icon` VALUES ('43', 'layui-icon-fire', '火', '&#xe756;');
INSERT INTO `icon` VALUES ('44', 'layui-icon-return', '返回', '&#xe65c;');
INSERT INTO `icon` VALUES ('45', 'layui-icon-location', '位置-地图', '&#xe715;');
INSERT INTO `icon` VALUES ('46', 'layui-icon-read', '办公-阅读', '&#xe705;');
INSERT INTO `icon` VALUES ('47', 'layui-icon-survey', '调查', '&#xe6b2;');
INSERT INTO `icon` VALUES ('48', 'layui-icon-face-smile', '表情-微笑', '&#xe6af;');
INSERT INTO `icon` VALUES ('49', 'layui-icon-face-cry', '表情-哭泣', '&#xe69c;');
INSERT INTO `icon` VALUES ('50', 'layui-icon-cart-simple', '购物车', '&#xe698;');
INSERT INTO `icon` VALUES ('51', 'layui-icon-cart', '购物车', '&#xe657;');
INSERT INTO `icon` VALUES ('52', 'layui-icon-next', '下一页', '&#xe65b;');
INSERT INTO `icon` VALUES ('53', 'layui-icon-prev', '上一页', '&#xe65a;');
INSERT INTO `icon` VALUES ('54', 'layui-icon-upload-drag', '上传-空心-拖拽', '&#xe681;');
INSERT INTO `icon` VALUES ('55', 'layui-icon-upload', '上传-实心', '&#xe67c;');
INSERT INTO `icon` VALUES ('56', 'layui-icon-download-circle', '下载-圆圈', '&#xe601;');
INSERT INTO `icon` VALUES ('57', 'layui-icon-component', '组件', '&#xe857;');
INSERT INTO `icon` VALUES ('58', 'layui-icon-file-b', '文件-粗', '&#xe655;');
INSERT INTO `icon` VALUES ('59', 'layui-icon-user', '用户', '&#xe770;');
INSERT INTO `icon` VALUES ('60', 'layui-icon-find-fill', '发现-实心', '&#xe670;');
INSERT INTO `icon` VALUES ('61', 'layui-icon-loading', 'loading', '&#xe63d;');
INSERT INTO `icon` VALUES ('62', 'layui-icon-loading-1', 'loading', '&#xe63e;');
INSERT INTO `icon` VALUES ('63', 'layui-icon-add-1', '添加', '&#xe654;');
INSERT INTO `icon` VALUES ('64', 'layui-icon-play', '播放', '&#xe652;');
INSERT INTO `icon` VALUES ('65', 'layui-icon-pause', '暂停', '&#xe651;');
INSERT INTO `icon` VALUES ('66', 'layui-icon-headset', '音频-耳机', '&#xe6fc;');
INSERT INTO `icon` VALUES ('67', 'layui-icon-video', '视频', '&#xe6ed;');
INSERT INTO `icon` VALUES ('68', 'layui-icon-voice', '语音-声音', '&#xe688;');
INSERT INTO `icon` VALUES ('69', 'layui-icon-speaker', '消息-通知-喇叭', '&#xe645;');
INSERT INTO `icon` VALUES ('70', 'layui-icon-fonts-del', '删除线', '&#xe64f;');
INSERT INTO `icon` VALUES ('71', 'layui-icon-fonts-code', '代码', '&#xe64e;');
INSERT INTO `icon` VALUES ('72', 'layui-icon-fonts-html', 'HTML', '&#xe64b;');
INSERT INTO `icon` VALUES ('73', 'layui-icon-fonts-strong', '字体加粗', '&#xe62b;');
INSERT INTO `icon` VALUES ('74', 'layui-icon-unlink', '删除链接', '&#xe64d;');
INSERT INTO `icon` VALUES ('75', 'layui-icon-picture', '图片', '&#xe64a;');
INSERT INTO `icon` VALUES ('76', 'layui-icon-link', '链接', '&#xe64c;');
INSERT INTO `icon` VALUES ('77', 'layui-icon-face-smile-b', '表情-笑-粗', '&#xe650;');
INSERT INTO `icon` VALUES ('78', 'layui-icon-align-left', '左对齐', '&#xe649;');
INSERT INTO `icon` VALUES ('79', 'layui-icon-align-right', '右对齐', '&#xe648;');
INSERT INTO `icon` VALUES ('80', 'layui-icon-align-center', '居中对齐', '&#xe647;');
INSERT INTO `icon` VALUES ('81', 'layui-icon-fonts-u', '字体-下划线', '&#xe646;');
INSERT INTO `icon` VALUES ('82', 'layui-icon-fonts-i', '字体-斜体', '&#xe644;');
INSERT INTO `icon` VALUES ('83', 'layui-icon-tabs', 'Tabs 选项卡', '&#xe62a;');
INSERT INTO `icon` VALUES ('84', 'layui-icon-radio', '单选框-选中', '&#xe643;');
INSERT INTO `icon` VALUES ('85', 'layui-icon-circle', '单选框-候选', '&#xe63f;');
INSERT INTO `icon` VALUES ('86', 'layui-icon-edit', '编辑', '&#xe642;');
INSERT INTO `icon` VALUES ('87', 'layui-icon-share', '分享', '&#xe641;');
INSERT INTO `icon` VALUES ('88', 'layui-icon-delete', '删除', '&#xe640;');
INSERT INTO `icon` VALUES ('89', 'layui-icon-form', '表单', '&#xe63c;');
INSERT INTO `icon` VALUES ('90', 'layui-icon-cellphone-fine', '手机-细体', '&#xe63b;');
INSERT INTO `icon` VALUES ('91', 'layui-icon-dialogue', '聊天 对话 沟通', '&#xe63a;');
INSERT INTO `icon` VALUES ('92', 'layui-icon-fonts-clear', '文字格式化', '&#xe639;');
INSERT INTO `icon` VALUES ('93', 'layui-icon-layer', '窗口', '&#xe638;');
INSERT INTO `icon` VALUES ('94', 'layui-icon-date', '日期', '&#xe637;');
INSERT INTO `icon` VALUES ('95', 'layui-icon-water', '水 下雨', '&#xe636;');
INSERT INTO `icon` VALUES ('96', 'layui-icon-code-circle', '代码-圆圈', '&#xe635;');
INSERT INTO `icon` VALUES ('97', 'layui-icon-carousel', '轮播组图', '&#xe634;');
INSERT INTO `icon` VALUES ('98', 'layui-icon-prev-circle', '翻页', '&#xe633;');
INSERT INTO `icon` VALUES ('99', 'layui-icon-layouts', '布局', '&#xe632;');
INSERT INTO `icon` VALUES ('100', 'layui-icon-util', '工具', '&#xe631;');
INSERT INTO `icon` VALUES ('101', 'layui-icon-templeate-1', '选择模板', '&#xe630;');
INSERT INTO `icon` VALUES ('102', 'layui-icon-upload-circle', '上传-圆圈', '&#xe62f;');
INSERT INTO `icon` VALUES ('103', 'layui-icon-tree', '树', '&#xe62e;');
INSERT INTO `icon` VALUES ('104', 'layui-icon-table', '表格', '&#xe62d;');
INSERT INTO `icon` VALUES ('105', 'layui-icon-chart', '图表', '&#xe62c;');
INSERT INTO `icon` VALUES ('106', 'layui-icon-chart-screen', '图标 报表 屏幕', '&#xe629;');
INSERT INTO `icon` VALUES ('107', 'layui-icon-engine', '引擎', '&#xe628;');
INSERT INTO `icon` VALUES ('108', 'layui-icon-triangle-d', '下三角', '&#xe625;');
INSERT INTO `icon` VALUES ('109', 'layui-icon-triangle-r', '右三角', '&#xe623;');
INSERT INTO `icon` VALUES ('110', 'layui-icon-file', '文件', '&#xe621;');
INSERT INTO `icon` VALUES ('111', 'layui-icon-set-sm', '设置-小型', '&#xe620;');
INSERT INTO `icon` VALUES ('112', 'layui-icon-add-circle', '添加-圆圈', '&#xe61f;');
INSERT INTO `icon` VALUES ('113', 'layui-icon-404', '404', '&#xe61c;');
INSERT INTO `icon` VALUES ('114', 'layui-icon-about', '关于', '&#xe60b;');
INSERT INTO `icon` VALUES ('115', 'layui-icon-up', '箭头 向上', '&#xe619;');
INSERT INTO `icon` VALUES ('116', 'layui-icon-down', '箭头 向下', '&#xe61a;');
INSERT INTO `icon` VALUES ('117', 'layui-icon-right', '箭头 向右', '&#xe602;');
INSERT INTO `icon` VALUES ('118', 'layui-icon-circle-dot', '圆点', '&#xe617;');
INSERT INTO `icon` VALUES ('119', 'layui-icon-search', '搜索', '&#xe615;');
INSERT INTO `icon` VALUES ('120', 'layui-icon-set-fill', '设置-实心', '&#xe614;');
INSERT INTO `icon` VALUES ('121', 'layui-icon-group', '群组', '&#xe613;');
INSERT INTO `icon` VALUES ('122', 'layui-icon-friends', '好友', '&#xe612;');
INSERT INTO `icon` VALUES ('123', 'layui-icon-reply-fill', '回复 评论 实心', '&#xe611;');
INSERT INTO `icon` VALUES ('124', 'layui-icon-menu-fill', '菜单 隐身 实心', '&#xe60f;');
INSERT INTO `icon` VALUES ('125', 'layui-icon-log', '记录', '&#xe60e;');
INSERT INTO `icon` VALUES ('126', 'layui-icon-picture-fine', '图片-细体', '&#xe60d;');
INSERT INTO `icon` VALUES ('127', 'layui-icon-face-smile-fine', '表情-笑-细体', '&#xe60c;');
INSERT INTO `icon` VALUES ('128', 'layui-icon-list', '列表', '&#xe60a;');
INSERT INTO `icon` VALUES ('129', 'layui-icon-release', '发布 纸飞机', '&#xe609;');
INSERT INTO `icon` VALUES ('130', 'layui-icon-ok', '对 OK', '&#xe605;');
INSERT INTO `icon` VALUES ('131', 'layui-icon-help', '帮助', '&#xe607;');
INSERT INTO `icon` VALUES ('132', 'layui-icon-chat', '客服', '&#xe606;');
INSERT INTO `icon` VALUES ('133', 'layui-icon-top', 'top 置顶', '&#xe604;');
INSERT INTO `icon` VALUES ('134', 'layui-icon-star', '收藏-空心', '&#xe600;');
INSERT INTO `icon` VALUES ('135', 'layui-icon-star-fill', '收藏-实心', '&#xe658;');
INSERT INTO `icon` VALUES ('136', 'layui-icon-close-fill', '关闭-实心', '&#x1007;');
INSERT INTO `icon` VALUES ('137', 'layui-icon-close', '关闭-空心', '&#x1006;');
INSERT INTO `icon` VALUES ('138', 'layui-icon-ok-circle', '正确', '&#x1005;');
INSERT INTO `icon` VALUES ('139', 'layui-icon-add-circle-fine', '添加-圆圈-细体', '&#xe608;');
INSERT INTO `icon` VALUES ('140', 'layui-icon-left', '箭头 向左', '&#xe603;');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `createEmp` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `fieldNewValue` varchar(255) DEFAULT NULL,
  `fieldOldValue` varchar(255) DEFAULT NULL,
  `modular` varchar(255) NOT NULL,
  `table` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createEmp` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `method` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pic` varchar(255) DEFAULT NULL,
  `pid` int(11) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fva1u374y516dwyfua3c56ogi` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '系统管理', '&#xe613;', '0', 'system');
INSERT INTO `menu` VALUES ('2', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '菜单管理', '&#xe613;', '1', 'menu/menuTable');
INSERT INTO `menu` VALUES ('3', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '日志管理', '&#xe613;', '1', 'log/logTable');
INSERT INTO `menu` VALUES ('4', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '角色管理', '&#xe613;', '1', 'u_role/roleTable');
INSERT INTO `menu` VALUES ('5', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '权限管理', '&#xe613;', '1', 'u_permission/permissionTable');
INSERT INTO `menu` VALUES ('6', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '部门管理', '&#xe613;', '0', 'department/departmentTable');
INSERT INTO `menu` VALUES ('7', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '员工管理', '&#xe613;', '0', 'u_user/userTable');
INSERT INTO `menu` VALUES ('8', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '订单管理', '&#xe613;', '0', 'order/orderTable');
INSERT INTO `menu` VALUES ('9', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '采购订单', '&#xe613;', '8', 'requisition/requisitionTable');
INSERT INTO `menu` VALUES ('10', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '销售订单', '&#xe613;', '8', 'sale_order/sale_orderTable');
INSERT INTO `menu` VALUES ('11', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '仓库管理', '&#xe613;', '0', '/');
INSERT INTO `menu` VALUES ('12', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '商品类型', '&#xe613;', '11', 'ware_type/ware_typeTable');
INSERT INTO `menu` VALUES ('13', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '仓库货架', '&#xe613;', '11', 'wares/waresTable');
INSERT INTO `menu` VALUES ('14', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '供应商管理', '&#xe613;', '0', '//');
INSERT INTO `menu` VALUES ('15', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '供应商品类型', '&#xe613;', '14', 'supp_ware_type/supp_ware_typeTable');
INSERT INTO `menu` VALUES ('16', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '供应商品展示', '&#xe613;', '14', 'supp_wares/supp_waresTable');
INSERT INTO `menu` VALUES ('17', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '其他页面', '&#xe613;', '0', '///');
INSERT INTO `menu` VALUES ('18', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '404页面', '&#xe613;', '17', '404');
INSERT INTO `menu` VALUES ('19', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '无权限页面', '&#xe613;', '17', 'unauthorized');
INSERT INTO `menu` VALUES ('20', '刘柏江', '2018-09-21 16:31:28', '刘柏江', '2018-09-21 16:31:28', 'GET', '登录页面', '&#xe613;', '17', '/login');
INSERT INTO `menu` VALUES ('21', '刘柏江', '2018-09-21 19:17:07', '刘柏江', '2018-09-21 19:17:17', 'GET', 'a', '&#xe613;', '20', '21');
INSERT INTO `menu` VALUES ('22', '刘柏江', '2018-09-21 19:17:59', '刘柏江', '2018-09-21 19:18:04', 'GET', 'b4546', '&#xe613;', '21', '22');
INSERT INTO `menu` VALUES ('23', '刘柏江', '2018-09-21 19:18:24', '刘柏江', '2018-09-21 19:18:32', 'GET', 'c热太热', '&#xe613;', '1', '23');
INSERT INTO `menu` VALUES ('25', '刘柏江', '2018-09-25 15:09:36', '刘柏江', '2018-09-25 15:09:41', 'GET', '仓库信息', '&#xe613;', '11', 'depot/depotTable');
INSERT INTO `menu` VALUES ('26', '刘柏江', '2018-09-25 15:14:31', '刘柏江', '2018-09-25 15:14:36', 'GET', '供应商信息', '&#xe613;', '14', 'supplier/supplierTable');

-- ----------------------------
-- Table structure for requisition
-- ----------------------------
DROP TABLE IF EXISTS `requisition`;
CREATE TABLE `requisition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `approver` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `consignee` varchar(255) DEFAULT NULL,
  `createEmp` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `depa_code` varchar(255) DEFAULT NULL,
  `depot_code` varchar(255) DEFAULT NULL,
  `job_num` varchar(255) DEFAULT NULL,
  `purc_ware_name` varchar(255) DEFAULT NULL,
  `purc_ware_num` bigint(20) DEFAULT NULL,
  `purc_ware_type` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `supp_code` varchar(255) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of requisition
-- ----------------------------
INSERT INTO `requisition` VALUES ('1', '林鑫敏', '10011524', 'adf', null, '2018-08-27 16:45:26', 'abc-123', 'ad', '打发打发', '三种松鼠坚果零食套装', '100', '零食', null, '141102571412', null, null, null, null);
INSERT INTO `requisition` VALUES ('2', '吕丽萍', '141103896', 'fadf', null, '2018-08-27 16:45:26', 'akb774', 'a', 'fadf', '优选卫生纸', '150', '生活用品', null, 'abk253614', null, null, null, null);
INSERT INTO `requisition` VALUES ('3', '潘丹玲', '11111111', 'faaaaaaaa', null, '2018-08-27 16:45:26', '222222222', 'fa', '打发', '德芙巧克力', '20', '零食', null, '3333333', null, null, null, null);
INSERT INTO `requisition` VALUES ('4', '何阳涛', '5656565', 'b', null, '2018-08-27 16:45:26', '121212', 'fadfa', '为热带管', '小猪佩奇抱枕', '100', '抱枕', null, '789651124', '2500', '25', null, '2018-09-24 17:19:54');
INSERT INTO `requisition` VALUES ('5', '王涛', '9658', 'aaaaaaa', null, '2018-08-27 16:45:26', '7845', 'adfa', '啊', '英雄钢笔', '150', '学校用品', null, '1411021513', '7500', '50', null, '2018-09-24 17:25:32');
INSERT INTO `requisition` VALUES ('6', '琳琳', '78', 'hhhhhhh', null, '2018-08-27 16:45:26', '78', 'adfadf', '啊', '包子', '1500', '零食', null, '85244', '67500', '45', null, '2018-09-24 17:31:53');
INSERT INTO `requisition` VALUES ('7', '琳琳', '222', 'aa', null, '2018-08-27 16:45:26', '2323', 'fadfadf', '打发点', '花花公子衬衫', '450', '衣服', null, '152203647', '21600', '48', null, '2018-09-24 17:35:06');
INSERT INTO `requisition` VALUES ('8', '李柚子', '99', 'aaaa', null, '2018-09-24 17:40:41', '999999999', 'afadfa', '打发点', '桌子', '1524', '生活用品', null, '7785241', '102870', '67.5', null, '2018-09-24 17:40:42');
INSERT INTO `requisition` VALUES ('9', 'fadfadfadf', 'fadfad', 'tttttt', null, '2018-09-24 17:52:00', 'fadfadf', 'adfad', '阿道夫', 'fadfadf', '1820', 'fadfadsf', '0', 'fadfadfadf', '276640', '152', null, '2018-09-24 17:52:00');
INSERT INTO `requisition` VALUES ('10', '理念', 'woshi', '偷不来', null, '2018-09-25 11:14:58', 'busi', 'adf', 'tahsi', '天津狗不理', '1520', '包子', '1', 'bubu', '71440', '47', null, '2018-09-25 11:14:58');

-- ----------------------------
-- Table structure for sale_order
-- ----------------------------
DROP TABLE IF EXISTS `sale_order`;
CREATE TABLE `sale_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `approver` varchar(255) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `consignee` varchar(255) DEFAULT NULL,
  `customer_buy` varchar(255) NOT NULL,
  `depot_code` varchar(255) DEFAULT NULL,
  `money` double NOT NULL,
  `number` bigint(20) NOT NULL,
  `r_phoneNumber` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `requ_name` varchar(255) DEFAULT NULL,
  `s_phoneNumber` varchar(255) NOT NULL,
  `salesperson` varchar(255) NOT NULL,
  `states` int(11) NOT NULL,
  `ware_code` varchar(255) NOT NULL,
  `createEmp` varchar(255) DEFAULT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ru47mtcbthg6ivad6ojw1ehru` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sale_order
-- ----------------------------
INSERT INTO `sale_order` VALUES ('2', 'lili', '2020', 'lili', 'hh', '101', '15', '2', '123569887', 'kk', 'lili', '12655874423', 'ss', '1', 'sss', null, null, '2018-09-25 11:19:48', '2018-09-24 11:33:22');
INSERT INTO `sale_order` VALUES ('3', '扣扣', '54321', '扣扣', 'oo', '102', '190', '3', '15669887725', '牛血色', '小宝', '12355856971', 'dd', '1', '12d', null, null, '2018-09-24 11:19:50', '2018-09-23 11:33:27');
INSERT INTO `sale_order` VALUES ('6', '啊哈', '111111', '啊哈', 'hh', '103', '15', '2', '123665856322', 'pink', '哦豁', '12655874423', 'aa', '1', '132', null, null, '2018-09-25 11:19:56', '2018-09-15 11:33:30');

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addr` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `createEmp` varchar(255) NOT NULL,
  `createTime` date NOT NULL,
  `name` varchar(255) NOT NULL,
  `phoneNumber` varchar(255) NOT NULL,
  `rank` varchar(255) NOT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  `updateTime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_u0lh6hby20ok7au7646wrewl` (`code`),
  UNIQUE KEY `UK_c3fclhmodftxk4d0judiafwi3` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES ('1', '上海', '1001', '吕', '2018-09-25', '美的', '15575367426', '一级供应商', null, null);

-- ----------------------------
-- Table structure for suppwaretype
-- ----------------------------
DROP TABLE IF EXISTS `suppwaretype`;
CREATE TABLE `suppwaretype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `createEmp` varchar(255) NOT NULL,
  `createTime` date NOT NULL,
  `name` varchar(255) NOT NULL,
  `sup_code` varchar(255) NOT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  `updateTime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kncah8go572cl969orjidhhxd` (`code`),
  UNIQUE KEY `UK_ctjc94vrf3xl652nolcxe485r` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of suppwaretype
-- ----------------------------

-- ----------------------------
-- Table structure for supp_wares
-- ----------------------------
DROP TABLE IF EXISTS `supp_wares`;
CREATE TABLE `supp_wares` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addr` varchar(255) NOT NULL,
  `brand` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `createEmp` varchar(255) NOT NULL,
  `createTime` date NOT NULL,
  `name` varchar(255) NOT NULL,
  `supp_code` varchar(255) NOT NULL,
  `total_inventory` bigint(20) NOT NULL,
  `type` varchar(255) NOT NULL,
  `unit_price` double NOT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  `updateTime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ra2cupmtjyomkmpgkqnened7g` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of supp_wares
-- ----------------------------
INSERT INTO `supp_wares` VALUES ('1', '北京', 'A', 'E085', 'wy', '2018-09-03', '铅笔', 'E045', '500', '文具', '2', null, null);

-- ----------------------------
-- Table structure for u_permission
-- ----------------------------
DROP TABLE IF EXISTS `u_permission`;
CREATE TABLE `u_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `method` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `sup_id` int(11) NOT NULL,
  `supId` int(11) NOT NULL,
  `createEmp` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_permission
-- ----------------------------
INSERT INTO `u_permission` VALUES ('1', 'GET', '菜单列表', '/menu/menuTable', '0', '0', null, null, null, null);
INSERT INTO `u_permission` VALUES ('2', 'POST', '新增菜单', '/menu/menuTable', '1', '0', null, null, null, null);
INSERT INTO `u_permission` VALUES ('3', 'PUT', '修改菜单', '/menu/menuTable', '1', '0', null, null, null, null);
INSERT INTO `u_permission` VALUES ('4', 'DELETE', '删除菜单', '/menu/menuTable', '1', '0', null, null, null, null);

-- ----------------------------
-- Table structure for u_role
-- ----------------------------
DROP TABLE IF EXISTS `u_role`;
CREATE TABLE `u_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createEmp` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jc07o365i9t5w7r587ea9hrs` (`code`),
  UNIQUE KEY `UK_q1gss8a045n528eqx9jtd46sa` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_role
-- ----------------------------

-- ----------------------------
-- Table structure for u_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `u_role_permission`;
CREATE TABLE `u_role_permission` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`rid`,`pid`),
  KEY `FKxu4pb7kvc4q7arfox8gfnxv6` (`pid`),
  CONSTRAINT `FK6dtd4u4es5aquyd09tf7h4scn` FOREIGN KEY (`rid`) REFERENCES `u_permission` (`id`),
  CONSTRAINT `FKxu4pb7kvc4q7arfox8gfnxv6` FOREIGN KEY (`pid`) REFERENCES `u_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for u_user
-- ----------------------------
DROP TABLE IF EXISTS `u_user`;
CREATE TABLE `u_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createEmp` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phoneNumber` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `states` int(11) NOT NULL,
  `depa_code` varchar(255) DEFAULT NULL,
  `job_num` varchar(255) NOT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_73oe4d5wpupis8knom5yi2n79` (`name`),
  UNIQUE KEY `UK_aqar42tfxxxeu7ww7r2prjfsj` (`job_num`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_user
-- ----------------------------
INSERT INTO `u_user` VALUES ('1', '十多个', '2018-09-25 08:53:42', '寡凫单鹄', '2018-09-25 08:53:47', '深圳', '2018-09-25', '小红', '111111', '15899627461', '/photos', '1', '1', '123456', '123456', null);
INSERT INTO `u_user` VALUES ('2', '三国杀多个', '2018-09-25 08:53:42', '寡凫单鹄', '2018-09-25 08:53:47', '深圳', '2018-09-25', '小蓝', '222222', '15899627461', '/photos', '1', '1', '123457', '123457', null);
INSERT INTO `u_user` VALUES ('3', '时代光华大厦', '2018-09-25 08:53:42', '寡凫单鹄', '2018-09-25 08:53:47', '深圳', '2018-09-25', '小黑', '333333', '15899627461', '/photos', '1', '1', '123458', '123458', null);
INSERT INTO `u_user` VALUES ('4', '啦啦啦啦', '2018-09-25 08:53:42', '寡凫单鹄', '2018-09-25 08:53:47', '深圳', '2018-09-25', '小绿', '444444', '15899627461', '/photos', '1', '1', '123459', '123459', null);
INSERT INTO `u_user` VALUES ('5', '数十个', '2018-09-25 08:53:42', '寡凫单鹄', '2018-09-25 08:53:47', '深圳', '2018-09-25', '小紫', '555555', '15899627461', '/photos', '1', '1', '123460', '123460', null);
INSERT INTO `u_user` VALUES ('6', '十多个', '2018-09-25 08:53:42', '寡凫单鹄', '2018-09-25 08:53:47', '深圳', '2018-09-25', '小是的故事', '111111', '15899627461', '/photos', '1', '1', '123461', '123461', null);
INSERT INTO `u_user` VALUES ('7', '三国杀多个', '2018-09-25 08:53:42', '寡凫单鹄', '2018-09-25 08:53:47', '深圳', '2018-09-25', '小十多个', '222222', '15899627461', '/photos', '1', '1', '123462', '123462', null);
INSERT INTO `u_user` VALUES ('8', '时代光华大厦', '2018-09-25 08:53:42', '寡凫单鹄', '2018-09-25 08:53:47', '深圳', '2018-09-25', '小相持不下', '333333', '15899627461', '/photos', '1', '1', '123463', '123463', null);
INSERT INTO `u_user` VALUES ('9', '啦啦啦啦', '2018-09-25 08:53:42', '寡凫单鹄', '2018-09-25 08:53:47', '深圳', '2018-09-25', '小一日游', '444444', '15899627461', '/photos', '1', '1', '123464', '123464', null);
INSERT INTO `u_user` VALUES ('10', '数十个', '2018-09-25 08:53:42', '寡凫单鹄', '2018-09-25 08:53:47', '深圳', '2018-09-25', '小价格', '555555', '15899627461', '/photos', '1', '1', '123465', '123465', null);
INSERT INTO `u_user` VALUES ('11', '速递公司', '2018-09-25 08:53:42', '寡凫单鹄', '2018-09-25 08:53:47', '深圳', '2018-09-25', '速递公司', '555555', '15899627461', '/photos', '1', '1', '123466', '123466', null);

-- ----------------------------
-- Table structure for u_user_role
-- ----------------------------
DROP TABLE IF EXISTS `u_user_role`;
CREATE TABLE `u_user_role` (
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  PRIMARY KEY (`uid`,`rid`),
  KEY `FKaspn9chwigpxbhqpakvvampum` (`rid`),
  CONSTRAINT `FKaspn9chwigpxbhqpakvvampum` FOREIGN KEY (`rid`) REFERENCES `u_user` (`id`),
  CONSTRAINT `FKli836kk3lyxwt2q3shsh0jlg` FOREIGN KEY (`uid`) REFERENCES `u_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for ware
-- ----------------------------
DROP TABLE IF EXISTS `ware`;
CREATE TABLE `ware` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addr` varchar(255) NOT NULL,
  `bar_code` varchar(255) DEFAULT NULL,
  `brand` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `createEmp` varchar(255) NOT NULL,
  `createTime` date NOT NULL,
  `In_unit_price` double NOT NULL,
  `name` varchar(255) NOT NULL,
  `out_unit_price` double NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `total_inventory` bigint(20) NOT NULL,
  `type` varchar(255) NOT NULL,
  `updateEmp` varchar(255) DEFAULT NULL,
  `updateTime` date DEFAULT NULL,
  `ware_photo` varchar(255) NOT NULL,
  `ware_type_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7qqd73qbmuh1aawl9wlqmkhxq` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ware
-- ----------------------------

-- ----------------------------
-- Table structure for wares
-- ----------------------------
DROP TABLE IF EXISTS `wares`;
CREATE TABLE `wares` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addr` varchar(255) NOT NULL,
  `bar_code` varchar(255) DEFAULT NULL,
  `brand` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `createEmp` varchar(255) NOT NULL,
  `createTime` date NOT NULL,
  `In_unit_price` double NOT NULL,
  `name` varchar(255) NOT NULL,
  `out_unit_price` double NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `total_inventory` bigint(20) NOT NULL,
  `type` varchar(255) NOT NULL,
  `updateEmp` varchar(255) NOT NULL,
  `updateTime` date NOT NULL,
  `ware_photo` varchar(255) NOT NULL,
  `ware_type_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_smh2hw7jvf2uplma9aeypucef` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wares
-- ----------------------------

-- ----------------------------
-- Table structure for ware_type
-- ----------------------------
DROP TABLE IF EXISTS `ware_type`;
CREATE TABLE `ware_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `createEmp` varchar(255) NOT NULL,
  `createTime` date NOT NULL,
  `name` varchar(255) NOT NULL,
  `sup_code` varchar(255) NOT NULL,
  `updateEmp` varchar(255) NOT NULL,
  `updateTime` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_l3h1vyobuwqjdfos0bn0w8u5e` (`code`),
  UNIQUE KEY `UK_c3roobrs879bdch9igel927ip` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ware_type
-- ----------------------------
