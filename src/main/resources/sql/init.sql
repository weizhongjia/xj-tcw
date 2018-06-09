drop table `wx_user`;
CREATE TABLE `wx_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) NOT NULL DEFAULT '',
  `unionid` varchar(255) DEFAULT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `avatarUrl` varchar(255) DEFAULT NULL,
  `timestamp` varchar(255) DEFAULT NULL,
  `appid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `wx_room` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `room_name` varchar(255) NOT NULL DEFAULT '',
  `start_time` bigint unsigned DEFAULT NULL,
  `end_time` bigint unsigned DEFAULT NULL,
  `is_limited_by_region` tinyint(1) DEFAULT NULL,
  `create_time` bigint unsigned DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL DEFAULT 'TEXT',
  `send_time` bigint unsigned NOT NULL,
  `open_id` VARCHAR(60) NOT NULL,
  `room_id` int(11) NOT NULL,
  `detail` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `gift` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `avatar` varchar(255) NOT NULL,
  `gif` VARCHAR(255) NOT NULL ,
  `des` VARCHAR(255) NOT NULL ,
  `price` INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `wx_order` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `out_trade_no` VARCHAR(32) NOT NULL UNIQUE ,
  `openid` varchar(255) NOT NULL,
  `trade_type` VARCHAR(20) DEFAULT NULL ,
  `create_time` bigint unsigned NOT NULL,
  `status` VARCHAR(10) NOT NULL ,
  `post_time` BIGINT UNSIGNED DEFAULT null,
  `bank_type` VARCHAR(20) DEFAULT NULL ,
  `end_time` BIGINT UNSIGNED DEFAULT NULL ,
  `transaction_id` VARCHAR(12) DEFAULT NULL ,
  `total_fee` INT(11) DEFAULT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `gift_order` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `out_trade_no` VARCHAR(32) NOT NULL UNIQUE ,
  `openid` varchar(255) NOT NULL,
  `gift_id` INT(11) NOT NULL ,
  `price` INT(11) NOT NULL ,
  `number` INT(11) NOT NULL ,
  `room_id` INT(11) NOT NULL,
  `create_time` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
ALTER TABLE `wx_room` ADD `bg_movie` VARCHAR(255) DEFAULT NULL;
ALTER TABLE `wx_room` ADD `bg_audio` VARCHAR(255) DEFAULT NULL;
ALTER TABLE `gift` ADD `cost_time` INTEGER(11) DEFAULT 5 NOT NULL ;
ALTER TABLE `gift_order` ADD `cost_time` INTEGER(11) DEFAULT  NULL ;
CREATE TABLE `room_bg_image` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `room_id` INT(11) UNSIGNED NOT NULL,
  `src` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
ALTER TABLE `message` ADD `detail_id` INT(11) DEFAULT NULL;
CREATE TABLE `gift_message_detail` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gift_id` INT(11) UNSIGNED NOT NULL,
  `gift_name` VARCHAR(255) NOT NULL ,
  `gift_gif` VARCHAR(255) NOT NULL,
  `gift_avatar` VARCHAR(255) NOT NULL,
  `gift_des` VARCHAR(255) NOT NULL,
  `gift_number` INT(11) NOT NULL,
  `gift_time` INT(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `redpack` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `out_trade_no` VARCHAR(32) NOT NULL UNIQUE ,
  `owner` VARCHAR(60) NOT NULL,
  `total_money` INT(11) NOT NULL,
  `total_count` INT(11) NOT NULL,
  `room_id` INT(11) NOT NULL,
  `send_time` BIGINT NOT NULL,
  `end_time` BIGINT NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `redpack_send_lock` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `redpack_id` INT(11) NOT NULL ,
  `openid` VARCHAR(60) NOT NULL,
  `send_time` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `redpack_unique_id` (`redpack_id`,`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE `redpack_send_list` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `openid` VARCHAR(60) DEFAULT NULL,
  `redpack_id` INT(11) NOT NULL,
  `money` INT(11) NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  `accept_time` BIGINT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
ALTER TABLE `redpack_send_list` ADD `redpack_position` INT(11) NOT NULL ;
ALTER TABLE `redpack_send_list` RENAME TO `redpack_send_history`;
ALTER TABLE `gift_order` RENAME TO `order`;
ALTER TABLE `order` ADD `order_type` VARCHAR(10) NOT NULL;
ALTER TABLE `order` ADD `total_money` INT(11) DEFAULT NULL;
ALTER TABLE `room_bg_image` RENAME TO `room_image`;