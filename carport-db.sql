create schema carport;

create table `user`(
	`id` integer not null auto_increment,
    `name` varchar(45) not null,
    `address` varchar(45) not null,
    `zipcode` integer not null,
    `phonenumber` integer not null,
    `email` varchar(45) not null,
    `password` varchar(45),
    `role` enum('customer','employee') not null,
    primary key (`id`)
);

insert into `user`(`name`,`address`,`zipcode`,`phonenumber`,`email`,`password`,`role`) 
values ('Lars Larsen','Hveen Boulevard 8',2630,38383838,'lars@email.com','1234','employee');

insert into `user`(`name`,`address`,`zipcode`,`phonenumber`,`email`,`password`,`role`) 
values ('Jens Hansen','Sivsangervej 19',3080,49758900,'jens@mail.dk','5678','customer');

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

insert into `orders`(`length`,`width`,`height`,`roof_angle`,`shed_width`,`shed_length`,`orderdate`,`phonenumber`,`status`) 
values (600,300,210,0,270,150,'2017-11-28',40404040,'unconfirmed');

drop table `user`;



