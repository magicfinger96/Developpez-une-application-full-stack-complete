CREATE DATABASE mddapiddb;
USE mddapiddb;

CREATE TABLE `USER` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255),
  `username` varchar(255),
  `password` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `POST` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(255),
  `content` varchar(2000),
  `topic_id` integer NOT NULL,
  `author_id` integer NOT NULL,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `COMMENT` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `post_id` integer,
  `author_id` integer,
  `message` varchar(2000),
  `created_at` timestamp
);

CREATE TABLE `SUBSCRIPTION` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `topic_id` integer,
  `user_id` integer,
  `created_at` timestamp
);

CREATE TABLE `TOPIC` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(255),
  `content` varchar(2000),
  `created_at` timestamp
);

CREATE UNIQUE INDEX `USER_email_index` ON `USER` (`email`);
CREATE UNIQUE INDEX `USER_username_index` ON `USER` (`username`);

ALTER TABLE `POST` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPIC` (`id`);
ALTER TABLE `POST` ADD FOREIGN KEY (`author_id`) REFERENCES `USER` (`id`);

ALTER TABLE `COMMENT` ADD FOREIGN KEY (`post_id`) REFERENCES `POST` (`id`);
ALTER TABLE `COMMENT` ADD FOREIGN KEY (`author_id`) REFERENCES `USER` (`id`);

ALTER TABLE `SUBSCRIPTION` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPIC` (`id`);
ALTER TABLE `SUBSCRIPTION` ADD FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`);