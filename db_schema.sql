-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema paymybuddy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema paymybuddy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `paymybuddy` DEFAULT CHARACTER SET utf8 ;
USE `paymybuddy` ;

-- -----------------------------------------------------
-- Table `paymybuddy`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`Users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `firstname` VARCHAR(255) NOT NULL,
    `lastname` VARCHAR(255) NOT NULL,
    `mail` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `account_balance` DOUBLE NOT NULL DEFAULT 0,
    `date_creation` DATETIME NOT NULL,
    `date_modification` DATETIME NULL DEFAULT NULL,
    UNIQUE INDEX `mail_UNIQUE` (`mail` ASC) VISIBLE,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddy`.`Relations_Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`Relations_Users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `id_user` INT NOT NULL,
    `id_friend` INT NOT NULL,
    `date_relation` DATETIME NOT NULL,
    INDEX `FK_Relations_Users_Users1_idx` (`id_user` ASC) VISIBLE,
    INDEX `FK_Relations_Users_Users2_idx` (`id_friend` ASC) VISIBLE,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_Relations_Users_Users1`
    FOREIGN KEY (`id_user`)
    REFERENCES `paymybuddy`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `FK_Relations_Users_Users2`
    FOREIGN KEY (`id_friend`)
    REFERENCES `paymybuddy`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddy`.`Transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`Transactions` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `amount` DOUBLE NOT NULL,
    `description` VARCHAR(255) CHARACTER SET 'DEFAULT' NOT NULL,
    `id_user` INT NOT NULL,
    `id_friend` INT NOT NULL,
    `type_transaction` VARCHAR(255) NOT NULL,
    `status` VARCHAR(20) NOT NULL,
    `sold_before_transaction` DOUBLE NOT NULL,
    `sold_after_transaction` DOUBLE NOT NULL,
    `date_creation` DATETIME NOT NULL,
    `date_modification` DATETIME NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_Transactions_Users1_idx` (`id_user` ASC) VISIBLE,
    INDEX `FK_Transactions_Users2_idx` (`id_friend` ASC) VISIBLE,
    CONSTRAINT `FK_Transactions_Users1`
    FOREIGN KEY (`id_user`)
    REFERENCES `paymybuddy`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `FK_Transactions_Users2`
    FOREIGN KEY (`id_friend`)
    REFERENCES `paymybuddy`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
