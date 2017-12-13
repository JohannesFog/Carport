create schema carport;

drop table `user`;

create table `user`(
    `id` integer not null auto_increment,
    `name` varchar(45) not null,
    `address` varchar(45) not null,
    `zipcode` integer not null,
    `phonenumber` integer not null unique,
    `email` varchar(45) not null unique,
    `password` varchar(45),
    `role` enum('customer','employee') not null,
    primary key (`id`)
);

insert into `user`(`name`,`address`,`zipcode`,`phonenumber`,`email`,`password`,`role`) values 
('Lars Larsen','Hveen Boulevard 8',2630,38383838,'lars@email.com','1234','employee'),
('Jens Hansen','Sivsangervej 19',3080,49758900,'jens@mail.dk','5678','customer');


create table `orders`(
	`id` integer not null auto_increment,
    `length` double not null,
    `width` double not null,
    `height` double not null,
    `roof_angle` double,
    `shed_width` double,
    `shed_length` double,
    `orderdate` date,
    `phonenumber` integer not null,
    `status` enum ('draft','unconfirmed','confirmed','paid'),
    primary key (`id`)
);

-- -----------------------------------------------------
-- Table `carport`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `carport`.`orders` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `length` DOUBLE NOT NULL,
  `width` DOUBLE NOT NULL,
  `height` DOUBLE NOT NULL,
  `roof_angle` DOUBLE NULL DEFAULT NULL,
  `shed_width` DOUBLE NULL DEFAULT NULL,
  `shed_length` DOUBLE NULL DEFAULT NULL,
  `orderdate` DATE NULL DEFAULT NULL,
  `phonenumber` INT(11) NOT NULL,
  `status` ENUM('draft', 'unconfirmed', 'confirmed', 'paid') NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_phone_idx` (`phonenumber` ASC),
  UNIQUE INDEX `phonenumber_UNIQUE` (`phonenumber` ASC),
  CONSTRAINT `fk_phone`
    FOREIGN KEY (`phonenumber`)
    REFERENCES `carport`.`user` (`phonenumber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;

insert into `orders`(`length`,`width`,`height`,`roof_angle`,`shed_width`,`shed_length`,`orderdate`,`phonenumber`,`status`) values 
(600,300,210,0,270,150,'2017-11-28',40404040,'unconfirmed');

create table `materials`(
	`id` integer not null auto_increment,
    `name` varchar(45) not null,
    `price` double not null,
    primary key (`id`)
);

insert into `materials`(`name`,`price`) values 
('19x100 mm. trykimp. bræt',6.95),
('25x50 mm. trykimp. bræt',7.95),
('25x125 mm. trykimp. bræt',29.95),
('25x150 mm. trykimp. bræt',38.95),
('25x200 mm. trykimp. bræt',50.95),
('38x73 mm. taglægte C18',12.95),
('45x95 mm. reglar ubh.',44.95),
('45x195 mm. spærtræ ubh.',37.95),
('97x97 mm. trykimp. stolpe',27.95),
('Færdigskåret (byg-selv spær)',500.00),
('Plastmo Ecolite blåtonet',330.00),
('Toplægte holder',18.50),
('Vingetagsten Gl. Dansk',14.95),
('Rygsten Model Volstrup',89.95),
('Rygstensbeslag',4.95),
('Tagstens bindere & nakkekroge 200 stk.',779.00),
('Hulbånd 1x20 mm. 10 meter',189.00),
('Universal 190 mm. højre',21.95),
('Universal 190 mm. venstre',21.95),
('Vinkelbeslag',20.95),
('Bræddebolte 10*120 mm.',32.83),
('Stalddørsgreb 50x75 mm.',189.00),
('T-hængsel 390 mm.',109.00),
('Firkantskiver 40x40x11 mm.',9.41),
('4,0 x 50 mm. beslagskruer 250 stk.',239.00),
('4,5 x 50 mm. Skruer Climate TX20 - 200 stk.',129.00),
('4,5 x 60 mm. skruer 200 stk.',209.00),
('4,5 x 70 mm. Skruer Climate TX20 - 200 stk.',199.00),
('5,0 x 40 mm. beslagskruer 250 stk.',199.00),
('5,0 x 100 mm. skruer 100 stk.',179.00);






