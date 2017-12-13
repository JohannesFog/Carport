CREATE SCHEMA carportTest;
USE carportTest;

CREATE TABLE carportTest.materials LIKE carport.materials;
CREATE TABLE materialsTest LIKE carportTest.materials;
INSERT INTO `materialsTest` (`name`,`price`) VALUES 
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