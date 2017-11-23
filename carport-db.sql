create schema carport;
create table `orders`(
	`o_id` integer not null auto_increment,
    `length` double not null,
    `width` double not null,
    `height` double not null,
    `roof` enum('fladt','skråt') not null,
    `roof_angle` double,
    `shed` enum('med','uden') not null,
    `shed_width` double,
    `shed_length` double,
    `orderdate` date,
    `u_name` varchar(45) null,
    primary key (`o_id`)
);

insert into `orders`(`length`,`width`,`height`,`roof`,`roof_angle`,`shed`,`shed_width`,`shed_length`,`orderdate`,`u_name`) 
values (510,330,210,'fladt',0,'uden',0,0,'2017-11-21','Lars Larsen');

