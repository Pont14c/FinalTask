SET NAMES 'utf8';
SET GLOBAL event_scheduler=ON;

DROP DATABASE IF EXISTS st4db;
CREATE DATABASE st4db CHARACTER SET utf8 COLLATE utf8_bin;

USE st4db;

CREATE TABLE roles(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);

INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'client');

CREATE TABLE users(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	login VARCHAR(20) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	role_id INTEGER NOT NULL REFERENCES roles(id) 
		ON DELETE CASCADE 
		ON UPDATE RESTRICT
);

INSERT INTO users VALUES(DEFAULT, 'admin', sha2('admin',224), 'Ivan', 'Ivanov', 0);
INSERT INTO users VALUES(DEFAULT, 'client', sha2('client',224), 'Petr', 'Petrov', 1);

CREATE TABLE rooms(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
    price INTEGER NOT NULL  REFERENCES prices(price_by_room) 
		ON DELETE CASCADE 
		ON UPDATE RESTRICT,
    place INTEGER NOT NULL REFERENCES places(places_in_room) 
		ON DELETE CASCADE 
		ON UPDATE RESTRICT,
    room_stars INTEGER NOT NULL REFERENCES rooms_types(stars) 
		ON DELETE CASCADE 
		ON UPDATE RESTRICT
);

CREATE TABLE prices(
	id INTEGER NOT NULL PRIMARY KEY,
    price_by_room INTEGER NOT NULL
);

INSERT INTO prices VALUES(1, 100);
INSERT INTO prices VALUES(2, 150);
INSERT INTO prices VALUES(3, 200);

CREATE TABLE places(
	id INTEGER NOT NULL PRIMARY KEY,
    places_in_room INTEGER NOT NULL
);

INSERT INTO places VALUES(1, 1);
INSERT INTO places VALUES(2, 2);
INSERT INTO places VALUES(3, 3);

CREATE TABLE rooms_types(
	id INTEGER NOT NULL PRIMARY KEY,
    stars INTEGER NOT NULL UNIQUE
);

INSERT INTO rooms_types VALUES(1, 3);
INSERT INTO rooms_types VALUES(2, 4);
INSERT INTO rooms_types VALUES(3, 5);

CREATE TABLE statuses(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO statuses VALUES(0, 'available');
INSERT INTO statuses VALUES(1, 'reserved');
INSERT INTO statuses VALUES(2, 'booked');
INSERT INTO statuses VALUES(3, 'unavailable');

INSERT INTO rooms VALUES(default,100,1,3);
INSERT INTO rooms VALUES(default,100,1,3);
INSERT INTO rooms VALUES(default,100,2,3);
INSERT INTO rooms VALUES(default,150,2,4);
INSERT INTO rooms VALUES(default,150,2,4);
INSERT INTO rooms VALUES(default,150,3,4);
INSERT INTO rooms VALUES(default,200,2,5);
INSERT INTO rooms VALUES(default,200,3,5);
INSERT INTO rooms VALUES(default,200,3,5);

CREATE TABLE booking(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
    id_room INTEGER NOT NULL REFERENCES rooms(id),
    date_in DATE NOT NULL,
    date_out DATE NOT NULL,
    status_room INTEGER NOT NULL REFERENCES statuses(id),
    login_user VARCHAR(20) NOT NULL references users(login),
    date_paid DATE
);

INSERT INTO booking VALUES(default, 1, CURDATE(), date_add(CURDATE(), interval 5 day), 3, 'admin', date_add(CURDATE(), interval 5 day));
INSERT INTO booking VALUES(default, 2, CURDATE(), date_add(CURDATE(), interval 5 day), 3, 'admin', date_add(CURDATE(), interval 5 day));
INSERT INTO booking VALUES(default, 3, CURDATE(), date_add(CURDATE(), interval 5 day), 3, 'admin', date_add(CURDATE(), interval 5 day));

CREATE TABLE orders(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
    date_in DATE NOT NULL,
    date_out DATE NOT NULL,
    login_user VARCHAR(20) NOT NULL references users(login),
    price INTEGER NOT NULL,
    place INTEGER NOT NULL,
    room_type INTEGER NOT NULL
);

DELIMITER $$
CREATE EVENT IF NOT EXISTS `st4db`.`deleteUnpaid`
ON SCHEDULE
EVERY 1 DAY
DO
BEGIN
DELETE FROM `st4db`.`booking` WHERE status_room = 1 and `date_paid` < NOW();
END$$ 
DELIMITER ;
