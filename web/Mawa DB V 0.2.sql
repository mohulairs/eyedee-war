SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `eyedee` ;
CREATE SCHEMA IF NOT EXISTS `eyedee` DEFAULT CHARACTER SET utf8 ;
USE `eyedee` ;

-- -----------------------------------------------------
-- Table `eyedee`.`_config_address_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_address_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_amount_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_amount_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_bank_account_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_bank_account_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_bank_account_usage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_bank_account_usage` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_bank_name`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_bank_name` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_claim_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_claim_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_contact_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_contact_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_date_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_date_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_document_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_document_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_file_location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_file_location` (
  `LOCATION_NAME` VARCHAR(20) NOT NULL,
  `FILE_PATH` VARCHAR(200) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`LOCATION_NAME`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_identity_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_identity_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_location_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_location_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_mail_list`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_mail_list` (
  `ID` VARCHAR(10) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_object_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_object_type` (
  `OBJECT_ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(30) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`OBJECT_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_order_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_order_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(30) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_partner_attributes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_partner_attributes` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_partner_function`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_partner_function` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_payment_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_payment_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_product_attribute`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_product_attribute` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_product_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_product_category` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(30) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_product_pricing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_product_pricing` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_relation_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_relation_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_role_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_role_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_workcenter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_workcenter` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_role_workcenter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_role_workcenter` (
  `ROLE_TYPE` VARCHAR(20) NOT NULL,
  `WORKCENTER` VARCHAR(20) NOT NULL,
  `SEQUENCE` INT(11) NULL DEFAULT NULL,
  `ACTIVE` VARCHAR(1) NULL DEFAULT NULL,
  PRIMARY KEY (`ROLE_TYPE`, `WORKCENTER`),
  INDEX `WC_WORKCENTER_FK_idx` (`WORKCENTER` ASC),
  CONSTRAINT `WC_ROLETYPE_FK`
    FOREIGN KEY (`ROLE_TYPE`)
    REFERENCES `eyedee`.`_config_role_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `WC_WORKCENTER_FK`
    FOREIGN KEY (`WORKCENTER`)
    REFERENCES `eyedee`.`_config_workcenter` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_sales_area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_sales_area` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(30) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_setting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_setting` (
  `SETTING` VARCHAR(20) NOT NULL,
  `ATTRIBUTE` VARCHAR(30) NOT NULL,
  `VALUE` VARCHAR(60) NULL DEFAULT NULL,
  PRIMARY KEY (`SETTING`, `ATTRIBUTE`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_status` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_status_reason`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_status_reason` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_status_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_status_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_table_name`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_table_name` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`_config_transaction_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`_config_transaction_type` (
  `ID` VARCHAR(20) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`card_usage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`card_usage` (
  `card_usage_id` INT(11) NOT NULL AUTO_INCREMENT,
  `swipe_date` DATETIME NULL DEFAULT NULL,
  `card_no` VARCHAR(10) NULL DEFAULT NULL,
  `supplier` VARCHAR(45) NULL DEFAULT NULL,
  `amount` DECIMAL(15,2) NULL DEFAULT NULL,
  PRIMARY KEY (`card_usage_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 358
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`change_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`change_log` (
  `change_id` INT(11) NOT NULL AUTO_INCREMENT,
  `object_name` VARCHAR(10) NULL DEFAULT NULL,
  `object_id` VARCHAR(10) NULL DEFAULT NULL,
  `change_type` VARCHAR(10) NULL DEFAULT NULL,
  `old_value` VARCHAR(45) NULL DEFAULT NULL,
  `new_value` VARCHAR(45) NULL DEFAULT NULL,
  `changed_by` VARCHAR(10) NULL DEFAULT NULL,
  `change_at` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`change_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`partner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`partner` (
  `partner_no` VARCHAR(10) NOT NULL,
  `type` VARCHAR(10) NULL DEFAULT NULL,
  `name1` VARCHAR(45) NULL DEFAULT NULL,
  `name2` VARCHAR(45) NULL DEFAULT NULL,
  `name3` VARCHAR(45) NULL DEFAULT NULL,
  `name4` VARCHAR(45) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  `created_by` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`partner_no`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`check_out`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`check_out` (
  `checking_id` VARCHAR(10) NOT NULL,
  `terminal_id` VARCHAR(10) NULL DEFAULT NULL,
  `created_by` VARCHAR(10) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `sales_area` VARCHAR(20) NULL DEFAULT NULL,
  `received_by` VARCHAR(10) NULL DEFAULT NULL,
  `receipt_from` VARCHAR(10) NULL DEFAULT NULL,
  `receipt_to` VARCHAR(10) NULL DEFAULT NULL,
  `cash_amount` DECIMAL(15,2) NULL DEFAULT NULL,
  `deposit_amount` DECIMAL(15,2) NULL DEFAULT NULL,
  PRIMARY KEY (`checking_id`),
  INDEX `createdByFK_idx` (`created_by` ASC),
  INDEX `receivedByFK_idx` (`received_by` ASC),
  INDEX `salesAreaFK_idx` (`sales_area` ASC),
  CONSTRAINT `checkOutCreatedByFK`
    FOREIGN KEY (`created_by`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `checkOutReceivedByFK`
    FOREIGN KEY (`received_by`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `checkOutSalesAreaFK`
    FOREIGN KEY (`sales_area`)
    REFERENCES `eyedee`.`_config_sales_area` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`direct_deposit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`direct_deposit` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `CHECKING_ID` VARCHAR(10) NULL DEFAULT NULL,
  `DEPOSIT_DATE` DATE NULL DEFAULT NULL,
  `DEPOSIT_REFERENCE` VARCHAR(45) NULL DEFAULT NULL,
  `AMOUNT` DECIMAL(10,2) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`invoice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`invoice` (
  `invoice_no` VARCHAR(10) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`invoice_no`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`mail_list`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`mail_list` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `LIST_ID` VARCHAR(10) NULL DEFAULT NULL,
  `EMAIL_ADDRESS` VARCHAR(60) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`message` (
  `MESSAGE_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `MESSAGE_TYPE` VARCHAR(10) NULL DEFAULT NULL,
  `RECIPIENT` VARCHAR(45) NULL DEFAULT NULL,
  `MESSAGE_BODY` LONGTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`MESSAGE_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`message_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`message_status` (
  `MESSAGE_ID` INT(11) NOT NULL,
  `STATUS` VARCHAR(10) NOT NULL,
  `SET` INT(11) NULL DEFAULT NULL,
  `SET_AT` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`MESSAGE_ID`, `STATUS`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`notification` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `TYPE` VARCHAR(10) NULL DEFAULT NULL,
  `RECIPIENT` VARCHAR(60) NULL DEFAULT NULL,
  `SUBJECT` TEXT NULL DEFAULT NULL,
  `BODY` LONGTEXT NULL DEFAULT NULL,
  `STATUS` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 60
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`notification_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`notification_log` (
  `LOG_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `NOTIFICATION_ID` INT(11) NULL DEFAULT NULL,
  `ACTION` VARCHAR(10) NULL DEFAULT NULL,
  `RESULT` VARCHAR(10) NULL DEFAULT NULL,
  `EXECUTION_TIME` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`LOG_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 3572
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`num_range`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`num_range` (
  `NUM_RANGE_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `OBJECT_TYPE` VARCHAR(20) NULL DEFAULT NULL,
  `CURRENT_NUMBER` VARCHAR(10) NULL DEFAULT NULL,
  `START_NUMBER` VARCHAR(10) NULL DEFAULT NULL,
  `END_NUMBER` VARCHAR(10) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`NUM_RANGE_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`object_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`object_status` (
  `obect_type` VARCHAR(10) NOT NULL,
  `object_no` VARCHAR(10) NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  `set_time` DATETIME NULL DEFAULT NULL,
  `active` VARCHAR(1) NULL DEFAULT NULL,
  PRIMARY KEY (`obect_type`, `object_no`, `status`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`partner_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`partner_address` (
  `address_id` INT(11) NOT NULL AUTO_INCREMENT,
  `address_type` VARCHAR(10) NULL DEFAULT NULL,
  `partner_no` VARCHAR(10) NULL DEFAULT NULL,
  `address_line1` VARCHAR(45) NULL DEFAULT NULL,
  `address_line2` VARCHAR(45) NULL DEFAULT NULL,
  `address_line3` VARCHAR(45) NULL DEFAULT NULL,
  `postal_code` VARCHAR(45) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  INDEX `addressTypeFK_idx` (`address_type` ASC),
  INDEX `partnerFK_idx` (`partner_no` ASC),
  CONSTRAINT `addressTypeFK`
    FOREIGN KEY (`address_type`)
    REFERENCES `eyedee`.`_config_address_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `partnerFK`
    FOREIGN KEY (`partner_no`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`partner_attribute`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`partner_attribute` (
  `partner_no` VARCHAR(10) NOT NULL,
  `attribute` VARCHAR(10) NOT NULL,
  `value` VARCHAR(45) NOT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`partner_no`, `attribute`, `value`),
  INDEX `attributeFK_idx` (`attribute` ASC),
  CONSTRAINT `attributeFK`
    FOREIGN KEY (`attribute`)
    REFERENCES `eyedee`.`_config_partner_attributes` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `partnerAttributeFK`
    FOREIGN KEY (`partner_no`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`partner_contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`partner_contact` (
  `contact_id` INT(11) NOT NULL AUTO_INCREMENT,
  `partner_no` VARCHAR(10) NULL DEFAULT NULL,
  `type` VARCHAR(10) NULL DEFAULT NULL,
  `value` VARCHAR(45) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`contact_id`),
  INDEX `partnerContactFK_idx` (`partner_no` ASC),
  INDEX `contactTypeFK_idx` (`type` ASC),
  CONSTRAINT `contactTypeFK`
    FOREIGN KEY (`type`)
    REFERENCES `eyedee`.`_config_contact_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `partnerContactFK`
    FOREIGN KEY (`partner_no`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`partner_identity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`partner_identity` (
  `partner_no` VARCHAR(10) NULL DEFAULT NULL,
  `id_type` VARCHAR(10) NOT NULL,
  `id_number` VARCHAR(45) NOT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id_type`, `id_number`),
  INDEX `identityTypeFK_idx` (`id_type` ASC),
  INDEX `partnerIdentityFK` (`partner_no` ASC),
  CONSTRAINT `identityTypeFK`
    FOREIGN KEY (`id_type`)
    REFERENCES `eyedee`.`_config_identity_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `partnerIdentityFK`
    FOREIGN KEY (`partner_no`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`partner_relation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`partner_relation` (
  `relations_type` VARCHAR(10) NOT NULL,
  `partner_no_1` VARCHAR(10) NOT NULL,
  `partner_no_2` VARCHAR(10) NOT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`relations_type`, `partner_no_1`, `partner_no_2`),
  INDEX `partner1RelationFK_idx` (`partner_no_1` ASC),
  INDEX `partner2RelationFK_idx` (`partner_no_2` ASC),
  CONSTRAINT `partner1RelationFK`
    FOREIGN KEY (`partner_no_1`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `partner2RelationFK`
    FOREIGN KEY (`partner_no_2`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `partnerRelationTypeFK`
    FOREIGN KEY (`relations_type`)
    REFERENCES `eyedee`.`_config_relation_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`partner_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`partner_role` (
  `role_id` VARCHAR(20) NOT NULL,
  `partner_no` VARCHAR(10) NOT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`, `partner_no`),
  INDEX `rolePartnerFK_idx` (`partner_no` ASC),
  CONSTRAINT `partnerRoleTypeFK`
    FOREIGN KEY (`role_id`)
    REFERENCES `eyedee`.`_config_role_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `rolePartnerFK`
    FOREIGN KEY (`partner_no`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`product` (
  `product_code` VARCHAR(10) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`product_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`product_attribute`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`product_attribute` (
  `ATTIBUTE_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_CODE` VARCHAR(10) NULL DEFAULT NULL,
  `ATTRIBUTE_TYPE` VARCHAR(20) NULL DEFAULT NULL,
  `ATTRIBUTE_VALUE` VARCHAR(60) NULL DEFAULT NULL,
  `VALID_FROM` DATE NULL DEFAULT NULL,
  `VALID_TO` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ATTIBUTE_ID`),
  INDEX `attributeProductFK_idx` (`PRODUCT_CODE` ASC),
  INDEX `productAttributeFK_idx` (`ATTRIBUTE_TYPE` ASC),
  CONSTRAINT `attributeProductFK`
    FOREIGN KEY (`PRODUCT_CODE`)
    REFERENCES `eyedee`.`product` (`product_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `productAttributeFK`
    FOREIGN KEY (`ATTRIBUTE_TYPE`)
    REFERENCES `eyedee`.`_config_product_attribute` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`product_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`product_category` (
  `product_code` VARCHAR(10) NOT NULL,
  `product_category` VARCHAR(20) NOT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`product_code`, `product_category`),
  INDEX `productCategoryTypeFK_idx` (`product_category` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`product_pricing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`product_pricing` (
  `product_code` VARCHAR(10) NOT NULL,
  `pricing_type` VARCHAR(20) NOT NULL,
  `value` DECIMAL(15,2) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`product_code`, `pricing_type`),
  INDEX `productPricingTypeFK_idx` (`pricing_type` ASC),
  CONSTRAINT `productPricingFK`
    FOREIGN KEY (`product_code`)
    REFERENCES `eyedee`.`product` (`product_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `productPricingTypeFK`
    FOREIGN KEY (`pricing_type`)
    REFERENCES `eyedee`.`_config_product_pricing` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`selection_option`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`selection_option` (
  `option_id` INT(11) NOT NULL AUTO_INCREMENT,
  `field_name` VARCHAR(10) NULL DEFAULT NULL,
  `option_code` VARCHAR(10) NULL DEFAULT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`option_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction` (
  `transaction_no` VARCHAR(10) NOT NULL,
  `transaction_type` VARCHAR(20) NULL DEFAULT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `status` VARCHAR(20) NULL DEFAULT NULL,
  `status_date` DATETIME NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`transaction_no`),
  INDEX `transactionTypeFK_idx` (`transaction_type` ASC),
  INDEX `statusFK_idx` (`status` ASC),
  CONSTRAINT `statusFK`
    FOREIGN KEY (`status`)
    REFERENCES `eyedee`.`_config_status_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionTypeFK`
    FOREIGN KEY (`transaction_type`)
    REFERENCES `eyedee`.`_config_transaction_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_amount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_amount` (
  `amount_type` VARCHAR(20) NOT NULL,
  `transaction_no` VARCHAR(10) NOT NULL,
  `amount` DECIMAL(15,2) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`amount_type`, `transaction_no`),
  INDEX `transactionAmountFK_idx` (`transaction_no` ASC),
  CONSTRAINT `transactionAmountFK`
    FOREIGN KEY (`transaction_no`)
    REFERENCES `eyedee`.`transaction` (`transaction_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionAmountTypeFK`
    FOREIGN KEY (`amount_type`)
    REFERENCES `eyedee`.`_config_amount_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_bank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_bank` (
  `transaction_bank_id` INT(11) NOT NULL AUTO_INCREMENT,
  `transaction_no` VARCHAR(10) NOT NULL,
  `bank_name` VARCHAR(45) NULL DEFAULT NULL,
  `account_type` VARCHAR(10) NULL DEFAULT NULL,
  `usage_type` VARCHAR(20) NULL DEFAULT NULL,
  `account_no` VARCHAR(20) NULL DEFAULT NULL,
  `account_id_number` VARCHAR(20) NULL DEFAULT NULL,
  `account_holder` VARCHAR(60) NULL DEFAULT NULL,
  `branch_code` VARCHAR(10) NULL DEFAULT NULL,
  `branch_name` VARCHAR(45) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`transaction_bank_id`),
  INDEX `transactionBankFK_idx` (`transaction_no` ASC),
  INDEX `transactionBankNameFK_idx` (`bank_name` ASC),
  INDEX `trxBankAccountTypeFK_idx` (`account_type` ASC),
  INDEX `trxAccountUsageFK_idx` (`usage_type` ASC),
  CONSTRAINT `transactionBankFK`
    FOREIGN KEY (`transaction_no`)
    REFERENCES `eyedee`.`transaction` (`transaction_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionBankNameFK`
    FOREIGN KEY (`bank_name`)
    REFERENCES `eyedee`.`_config_bank_name` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `trxAccountUsageFK`
    FOREIGN KEY (`usage_type`)
    REFERENCES `eyedee`.`_config_bank_account_usage` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `trxBankAccountTypeFK`
    FOREIGN KEY (`account_type`)
    REFERENCES `eyedee`.`_config_bank_account_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_date`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_date` (
  `transaction_no` VARCHAR(10) NOT NULL,
  `date_type` VARCHAR(20) NOT NULL,
  `date_value` DATE NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`transaction_no`, `date_type`),
  INDEX `transactionDateTypeFK_idx` (`date_type` ASC),
  CONSTRAINT `transactionDateFK`
    FOREIGN KEY (`transaction_no`)
    REFERENCES `eyedee`.`transaction` (`transaction_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionDateTypeFK`
    FOREIGN KEY (`date_type`)
    REFERENCES `eyedee`.`_config_date_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_document` (
  `transaction_no` VARCHAR(10) NOT NULL,
  `document_type` VARCHAR(20) NULL DEFAULT NULL,
  `location` VARCHAR(100) NULL DEFAULT NULL,
  `created_by` VARCHAR(10) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`transaction_no`),
  INDEX `transactionDocumentTypeFK_idx` (`document_type` ASC),
  CONSTRAINT `transactionDocumentFK`
    FOREIGN KEY (`transaction_no`)
    REFERENCES `eyedee`.`transaction` (`transaction_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionDocumentTypeFK`
    FOREIGN KEY (`document_type`)
    REFERENCES `eyedee`.`_config_document_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_item` (
  `item_id` INT(11) NOT NULL AUTO_INCREMENT,
  `transaction_no` VARCHAR(10) NULL DEFAULT NULL,
  `product_id` VARCHAR(10) NULL DEFAULT NULL,
  `item_category` VARCHAR(20) NULL DEFAULT NULL,
  `quantity` INT(11) NULL DEFAULT NULL,
  `unit_price` DECIMAL(15,2) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  INDEX `transactionProduct_idx` (`product_id` ASC),
  INDEX `transactionItemCategory_idx` (`item_category` ASC),
  INDEX `transactionItemFK` (`transaction_no` ASC),
  CONSTRAINT `transactionItemCategory`
    FOREIGN KEY (`item_category`)
    REFERENCES `eyedee`.`_config_product_category` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionItemFK`
    FOREIGN KEY (`transaction_no`)
    REFERENCES `eyedee`.`transaction` (`transaction_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionProduct`
    FOREIGN KEY (`product_id`)
    REFERENCES `eyedee`.`product` (`product_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 92
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_item_pricing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_item_pricing` (
  `item_id` INT(11) NOT NULL,
  `pricing_type` VARCHAR(20) NOT NULL,
  `value` DECIMAL(15,2) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`, `pricing_type`),
  INDEX `trxItemPricingTypeFK_idx` (`pricing_type` ASC),
  CONSTRAINT `itemPricingFK`
    FOREIGN KEY (`item_id`)
    REFERENCES `eyedee`.`transaction_item` (`item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `itemPricingTypeFK`
    FOREIGN KEY (`pricing_type`)
    REFERENCES `eyedee`.`_config_product_pricing` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_location` (
  `location_type` VARCHAR(20) NOT NULL,
  `transaction_no` VARCHAR(10) NOT NULL,
  `location_id` VARCHAR(20) NOT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`transaction_no`, `location_type`, `location_id`),
  INDEX `locationTypeFK_idx` (`location_type` ASC),
  INDEX `locationFK_idx` (`location_id` ASC),
  CONSTRAINT `locationFK`
    FOREIGN KEY (`location_id`)
    REFERENCES `eyedee`.`_config_sales_area` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `locationTypeFK`
    FOREIGN KEY (`location_type`)
    REFERENCES `eyedee`.`_config_location_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionLocationFK`
    FOREIGN KEY (`transaction_no`)
    REFERENCES `eyedee`.`transaction` (`transaction_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_partner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_partner` (
  `partner_type` VARCHAR(20) NOT NULL,
  `transaction_no` VARCHAR(10) NOT NULL,
  `partner_no` VARCHAR(10) NOT NULL,
  `date_added` DATETIME NULL DEFAULT NULL,
  `date_effective` DATETIME NULL DEFAULT NULL,
  `status` VARCHAR(20) NULL DEFAULT NULL,
  `status_reason` VARCHAR(20) NULL DEFAULT NULL,
  `status_date` DATETIME NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  `created_by` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`transaction_no`, `partner_type`, `partner_no`),
  INDEX `transactionPartnerFK_idx` (`partner_no` ASC),
  INDEX `partnerFunctionFK_idx` (`partner_type` ASC),
  INDEX `trxPartnerStatusFK_idx` (`status` ASC),
  INDEX `trxPartnerStatusReasonFK_idx` (`status_reason` ASC),
  CONSTRAINT `partnerFunctionFK`
    FOREIGN KEY (`partner_type`)
    REFERENCES `eyedee`.`_config_partner_function` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `partnerTransactionFK`
    FOREIGN KEY (`transaction_no`)
    REFERENCES `eyedee`.`transaction` (`transaction_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionPartnerFK`
    FOREIGN KEY (`partner_no`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_payment` (
  `payment_no` INT(11) NOT NULL AUTO_INCREMENT,
  `payment_date` DATE NULL DEFAULT NULL,
  `payment_type` VARCHAR(20) NULL DEFAULT NULL,
  `transaction_no` VARCHAR(10) NULL DEFAULT NULL,
  `amount` DECIMAL(15,2) NULL DEFAULT NULL,
  `created_by` VARCHAR(10) NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `external_receipt_no` VARCHAR(10) NULL DEFAULT NULL,
  `received_by` VARCHAR(10) NULL DEFAULT NULL,
  `terminal_id` VARCHAR(10) NULL DEFAULT NULL,
  `checking_id` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`payment_no`),
  UNIQUE INDEX `external_receipt_no_UNIQUE` (`external_receipt_no` ASC),
  INDEX `paymentTransactionFK_idx` (`transaction_no` ASC),
  INDEX `transactionPaymentTypeFK_idx` (`payment_type` ASC),
  INDEX `paymentCreatedByFK_idx` (`created_by` ASC),
  INDEX `paymentReceivedByFK_idx` (`received_by` ASC),
  CONSTRAINT `paymentCreatedByFK`
    FOREIGN KEY (`created_by`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `paymentReceivedByFK`
    FOREIGN KEY (`received_by`)
    REFERENCES `eyedee`.`partner` (`partner_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `paymentTransactionFK`
    FOREIGN KEY (`transaction_no`)
    REFERENCES `eyedee`.`transaction` (`transaction_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionPaymentTypeFK`
    FOREIGN KEY (`payment_type`)
    REFERENCES `eyedee`.`_config_payment_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 48
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_relation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_relation` (
  `type` VARCHAR(20) NOT NULL,
  `transaction_no1` VARCHAR(10) NOT NULL,
  `transaction_no2` VARCHAR(10) NOT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`transaction_no1`, `transaction_no2`, `type`),
  INDEX `transaction2RelationFK_idx` (`transaction_no2` ASC),
  INDEX `relationTypeFK_idx` (`type` ASC),
  CONSTRAINT `relationTypeFK`
    FOREIGN KEY (`type`)
    REFERENCES `eyedee`.`_config_transaction_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transaction1RelationFK`
    FOREIGN KEY (`transaction_no1`)
    REFERENCES `eyedee`.`transaction` (`transaction_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transaction2RelationFK`
    FOREIGN KEY (`transaction_no2`)
    REFERENCES `eyedee`.`transaction` (`transaction_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_status` (
  `transaction_no` VARCHAR(10) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `status_date` DATETIME NULL DEFAULT NULL,
  `active` VARCHAR(1) NULL DEFAULT NULL,
  `created_by` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`transaction_no`, `status`),
  INDEX `transactionStatusTypeFK_idx` (`status` ASC),
  CONSTRAINT `transactionStatusFK`
    FOREIGN KEY (`transaction_no`)
    REFERENCES `eyedee`.`transaction` (`transaction_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `transactionStatusTypeFK`
    FOREIGN KEY (`status`)
    REFERENCES `eyedee`.`_config_status_type` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`transaction_trip`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`transaction_trip` (
  `trip_id` INT(11) NOT NULL,
  `driver` VARCHAR(10) NULL DEFAULT NULL,
  `vehicle_id` VARCHAR(10) NULL DEFAULT NULL,
  `transaction_no` VARCHAR(10) NULL DEFAULT NULL,
  `start_point` VARCHAR(10) NULL DEFAULT NULL,
  `end_point` VARCHAR(10) NULL DEFAULT NULL,
  `distance` DECIMAL(5,2) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`trip_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`user` (
  `user_id` VARCHAR(10) NOT NULL,
  `partner_no` VARCHAR(10) NOT NULL,
  `password` BLOB NULL DEFAULT NULL,
  `temp_password` BLOB NULL DEFAULT NULL,
  `status` VARCHAR(10) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`user_role` (
  `user_id` VARCHAR(20) NOT NULL,
  `user_role` VARCHAR(20) NOT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `user_role`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eyedee`.`usr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`usr` (
  `user_id` VARCHAR(10) NOT NULL,
  `partner_no` VARCHAR(10) NOT NULL,
  `password` BLOB NULL DEFAULT NULL,
  `temp_password` BLOB NULL DEFAULT NULL,
  `status` VARCHAR(10) NULL DEFAULT NULL,
  `valid_from` DATE NULL DEFAULT NULL,
  `valid_to` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `eyedee` ;

-- -----------------------------------------------------
-- Placeholder table for view `eyedee`.`config_tables`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eyedee`.`config_tables` (`table_name` INT, `table_type` INT, `engine` INT);

-- -----------------------------------------------------
-- View `eyedee`.`config_tables`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eyedee`.`config_tables`;
USE `eyedee`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `eyedee`.`config_tables` AS select `information_schema`.`tables`.`TABLE_NAME` AS `table_name`,`information_schema`.`tables`.`TABLE_TYPE` AS `table_type`,`information_schema`.`tables`.`ENGINE` AS `engine` from `information_schema`.`tables` where ((`information_schema`.`tables`.`TABLE_SCHEMA` = 'eyedee') and (`information_schema`.`tables`.`TABLE_NAME` like '_config%')) order by `information_schema`.`tables`.`TABLE_NAME`;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
