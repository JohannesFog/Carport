create schema carport;
create table `orders`(
	`o_id` integer not null auto_increment,
    `length` double not null,
    `width` double not null,
    `height` double not null,
    `roof` enum('fladt','skråt') not null,
    `shed` enum('med','uden') not null,
    `orderdate` date,
    `u_name` varchar(45) null,
    primary key (`o_id`)
);

insert into `orders`(`length`,`width`,`height`,`roof`,`shed`,`orderdate`,`u_name`) 
values (510,330,210,'fladt','uden','2017-11-21','Lars Larsen');

