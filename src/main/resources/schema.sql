create database `auction_system`;
use `auction_system`;

CREATE TABLE `items` (
  `item_id` bigint NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) NOT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB;

CREATE TABLE `auction` (
  `auction_id` bigint NOT NULL AUTO_INCREMENT,
  `auction_name` varchar(255) NOT NULL,
  `auction_status` varchar(255) NOT NULL,
  `start_time` datetime(6) NOT NULL,
  `duration` bigint NOT NULL,
  `item_id` bigint NOT NULL,
  `base_price` bigint NOT NULL,
  `step_rate` bigint NOT NULL,
  `highest_bid` bigint NOT NULL DEFAULT '0',
  `revision_version` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`auction_id`),
  FOREIGN KEY (`item_id`) REFERENCES `items`(`item_id`)
) ENGINE=InnoDB;

CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB;

CREATE TABLE `bids` (
  `bid_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `auction_id` bigint NOT NULL,
  `bid_amount` bigint NOT NULL,
  `bid_status` varchar(255) NOT NULL,
  `bid_time` datetime(6) NOT NULL,
  PRIMARY KEY (`bid_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
  FOREIGN KEY (`auction_id`) REFERENCES `auction`(`auction_id`)
) ENGINE=InnoDB;