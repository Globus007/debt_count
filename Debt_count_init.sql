CREATE SCHEMA `debt_count` 
DEFAULT CHARACTER 
SET utf8 COLLATE utf8_unicode_ci ;


DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(45) NOT NULL,
  `date` DATETIME NOT NULL,
  `amount` INT NOT NULL,
  `pay_date` DATETIME NOT NULL,
);


DROP TABLE IF EXISTS `debt`;
CREATE TABLE `debt` (
  `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `fine` INT NULL,
  `percent` INT NULL,
  `calculation_date` DATETIME NULL,
  `bill_id` INT NULL,
  FOREIGN KEY (`bill_id`)  REFERENCES `bill` (`id`)
);
