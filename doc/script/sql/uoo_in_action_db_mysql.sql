/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

# Create Database
# ------------------------------------------------------------

CREATE SCHEMA `UooInAction` DEFAULT CHARSET utf8mb4;

Use UooInAction;

# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE user
(
    id BIGINT UNSIGNED PRIMARY KEY COMMENT 'id主键' AUTO_INCREMENT,
    gmt_create DATETIME NOT NULL COMMENT '创建时间',
    gmt_modified DATETIME NOT NULL COMMENT '修改时间',
    name NVARCHAR(32) NOT NULL COMMENT '用户名',
    phone_number NVARCHAR(32) NOT NULL COMMENT '手机号'
);
CREATE UNIQUE INDEX uk_phone_number ON user (phone_number);
ALTER TABLE user COMMENT = '用户表';

# Dump of table organization
# ------------------------------------------------------------

DROP TABLE IF EXISTS `organization`;

CREATE TABLE organization
(
    id BIGINT UNSIGNED PRIMARY KEY COMMENT 'id主键' AUTO_INCREMENT,
    gmt_create DATETIME NOT NULL COMMENT '创建时间',
    gmt_modified DATETIME NOT NULL COMMENT '修改时间',
    name NVARCHAR(32) NOT NULL COMMENT '组织名'
);
ALTER TABLE organization COMMENT = '组织表';

# Dump of table user_org
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_org`;

CREATE TABLE user_org
(
    id BIGINT UNSIGNED PRIMARY KEY COMMENT 'id主键' AUTO_INCREMENT,
    gmt_create DATETIME NOT NULL COMMENT '创建时间',
    gmt_modified DATETIME NOT NULL COMMENT '修改时间',
    user_id BIGINT UNSIGNED NOT NULL COMMENT '用户id',
    org_id BIGINT UNSIGNED NOT NULL COMMENT '组织id'
);
CREATE UNIQUE INDEX uk_user_id_org_id ON user_org (user_id, org_id);
ALTER TABLE user_org COMMENT = '用户组织关系映射表';

# Sample Data
# ------------------------------------------------------------

INSERT INTO `UooInAction`.user (id, gmt_create, gmt_modified, name, phone_number) VALUES (null, now(), now(), 'user1', '13276999645');
INSERT INTO `UooInAction`.user (id, gmt_create, gmt_modified, name, phone_number) VALUES (null, now(), now(), 'user2', '13276999646');
INSERT INTO `UooInAction`.user (id, gmt_create, gmt_modified, name, phone_number) VALUES (null, now(), now(), 'user3', '13276999647');
INSERT INTO `UooInAction`.user (id, gmt_create, gmt_modified, name, phone_number) VALUES (null, now(), now(), 'user4', '13276999648');
INSERT INTO `UooInAction`.user (id, gmt_create, gmt_modified, name, phone_number) VALUES (null, now(), now(), 'user5', '13276999649');

INSERT INTO `UooInAction`.organization (id, gmt_create, gmt_modified, name) VALUES (null, now(), now(), 'org1');
INSERT INTO `UooInAction`.organization (id, gmt_create, gmt_modified, name) VALUES (null, now(), now(), 'org2');
INSERT INTO `UooInAction`.organization (id, gmt_create, gmt_modified, name) VALUES (null, now(), now(), 'org3');

INSERT INTO `UooInAction`.user_org (id, gmt_create, gmt_modified, user_id, org_id) VALUES (null, now(), now(), 1, 1);
INSERT INTO `UooInAction`.user_org (id, gmt_create, gmt_modified, user_id, org_id) VALUES (null, now(), now(), 1, 2);
INSERT INTO `UooInAction`.user_org (id, gmt_create, gmt_modified, user_id, org_id) VALUES (null, now(), now(), 2, 3);

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
