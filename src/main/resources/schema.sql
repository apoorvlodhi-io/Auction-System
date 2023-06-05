create database `mydb`;
use `mydb`;

CREATE TABLE `auction` (
  `auction_id` bigint NOT NULL AUTO_INCREMENT,
  `auction_name` varchar(255) DEFAULT NULL,
  `auction_status` int DEFAULT NULL,
  `base_price` bigint DEFAULT NULL,
  `duration` bigint DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `highest_bid` bigint DEFAULT NULL,
  `item_id` bigint DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `step_rate` bigint DEFAULT NULL,
  PRIMARY KEY (`auction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bids` (
  `bid_id` bigint NOT NULL AUTO_INCREMENT,
  `auction_id` bigint DEFAULT NULL,
  `bid_amount` bigint DEFAULT NULL,
  `bid_status` int DEFAULT NULL,
  `bid_time` datetime(6) DEFAULT NULL,
  `item_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`bid_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `items` (
  `item_id` bigint NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;