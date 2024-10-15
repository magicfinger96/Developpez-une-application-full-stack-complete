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
  `description` varchar(2000),
  `created_at` timestamp
);

CREATE UNIQUE INDEX `USER_email_index` ON `USER` (`email`);
CREATE UNIQUE INDEX `USER_username_index` ON `USER` (`username`);
CREATE UNIQUE INDEX `TOPIC_title_index` ON `TOPIC` (`title`);

ALTER TABLE `POST` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPIC` (`id`);
ALTER TABLE `POST` ADD FOREIGN KEY (`author_id`) REFERENCES `USER` (`id`);

ALTER TABLE `COMMENT` ADD FOREIGN KEY (`post_id`) REFERENCES `POST` (`id`);
ALTER TABLE `COMMENT` ADD FOREIGN KEY (`author_id`) REFERENCES `USER` (`id`);

ALTER TABLE `SUBSCRIPTION` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPIC` (`id`);
ALTER TABLE `SUBSCRIPTION` ADD FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`);

INSERT INTO `TOPIC` (`title`, `description`, `created_at`) VALUES
('Java', 'Java est un langage de programmation orienté objet utilisé pour créer des applications robustes et performantes.', NOW()),
('Python', 'Python est un langage polyvalent connu pour sa simplicité et sa puissance, largement utilisé dans l\'intelligence artificielle et le développement web.', NOW()),
('JavaScript', 'JavaScript est le langage de programmation essentiel pour le développement web interactif, à la fois côté client et côté serveur.', NOW()),
('C++', 'C++ est un langage puissant pour le développement de systèmes, de jeux vidéo, et d\'applications nécessitant un contrôle précis des ressources.', NOW()),
('C#', 'C# est un langage moderne et flexible principalement utilisé pour le développement sur la plateforme .NET de Microsoft.', NOW()),
('Ruby', 'Ruby est un langage élégant et simple, souvent utilisé avec le framework Ruby on Rails pour le développement web.', NOW()),
('PHP', 'PHP est un langage de script côté serveur utilisé pour développer des applications web dynamiques.', NOW()),
('Swift', 'Swift est le langage de programmation d\'Apple pour créer des applications iOS et macOS.', NOW()),
('Go', 'Go, également appelé Golang, est un langage simple et rapide conçu pour les systèmes distribués et les applications cloud.', NOW()),
('Kotlin', 'Kotlin est un langage moderne et concis, compatible avec Java, utilisé principalement pour le développement Android.', NOW()),
('Rust', 'Rust est un langage conçu pour la sécurité, la performance, et la gestion de la mémoire, souvent utilisé dans le développement de systèmes.', NOW()),
('TypeScript', 'TypeScript est un surensemble de JavaScript qui ajoute un typage statique pour améliorer la robustesse des applications.', NOW()),
('Perl', 'Perl est un langage polyvalent utilisé principalement pour l\'administration système, le traitement de texte et les scripts.', NOW()),
('R', 'R est un langage spécialisé dans les statistiques et l\'analyse de données, largement utilisé en recherche scientifique.', NOW()),
('Scala', 'Scala combine la programmation orientée objet et fonctionnelle, utilisé dans les systèmes distribués et le Big Data.', NOW()),
('Dart', 'Dart est un langage optimisé pour les interfaces utilisateur, principalement utilisé avec le framework Flutter pour les applications mobiles.', NOW()),
('Haskell', 'Haskell est un langage fonctionnel pur, connu pour son expressivité et sa rigueur mathématique.', NOW()),
('MATLAB', 'MATLAB est un langage de programmation dédié aux calculs mathématiques et scientifiques.', NOW()),
('Lua', 'Lua est un langage léger et rapide, souvent utilisé dans les jeux vidéo et les systèmes embarqués.', NOW()),
('Shell (Bash, PowerShell)', 'Les scripts Shell, comme Bash ou PowerShell, sont utilisés pour automatiser des tâches dans les systèmes d\'exploitation Unix/Linux et Windows.', NOW());
