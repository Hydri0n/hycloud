BEGIN
CREATE TABLE if not exists `project` (
  `id` int(11) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `data_msg` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE if not exists `incrementer` (
  `table_name` varchar(20) NOT NULL,
  `number` int(11) DEFAULT '0',
  PRIMARY KEY (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `incrementer` VALUES ('project', '0');
END