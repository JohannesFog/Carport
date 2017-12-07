CREATE SCHEMA carportTest;
USE carportTest;

CREATE TABLE carportTest.materials LIKE carport.materials;
CREATE TABLE materialsTest LIKE carportTest.materials;
INSERT INTO `materialsTest` (`name`,`price`) VALUES 
('19x100 mm. trykimp. bræt',6.95),
('25x50 mm. trykimp. bræt',7.95),
('25x125 mm. trykimp. bræt',29.95),
('25x150 mm. trykimp. bræt',38.95);

CREATE TABLE carportTest.orders LIKE carport.orders;
CREATE TABLE ordersTest LIKE carportTest.orders;
INSERT INTO `ordersTest`(`length`,`width`,`height`,`roof_angle`,`shed_width`,`shed_length`,`orderdate`,`phonenumber`,`status`) VALUES 
(600,300,210,0,270,150,'2017-11-28',40404040,'unconfirmed');

CREATE TABLE carportTest.user LIKE carport.user;
CREATE TABLE userTest LIKE carportTest.user;
INSERT INTO `userTest`(`name`,`address`,`zipcode`,`phonenumber`,`email`,`password`,`role`) VALUES 
('Lars Larsen','Hveen Boulevard 8',2630,38383838,'lars@email.com','1234','employee'),
('Jens Hansen','Sivsangervej 19',3080,49758900,'jens@mail.dk','5678','customer');

CREATE USER 'testuser'@'%' IDENTIFIED BY 'trytotest1234';
GRANT ALL PRIVILEGES ON carportTest.* TO 'testuser'@'%';