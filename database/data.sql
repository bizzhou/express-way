CREATE USER 'test'@'localhost' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON testdb TO 'test'@'localhost';

DROP DATABASE IF EXISTS testdb;
CREATE DATABASE testdb;
use testdb;

CREATE TABLE User(
  username VARCHAR(40) NOT NULL ,
  password VARCHAR(256) NOT NULL ,
  role VARCHAR(20) NOT NULL ,

  PRIMARY KEY (username, password)
);


use testdb;

INSERT INTO User VALUES ("bizzhou", "123", "admin");
INSERT INTO User VALUES ("user", "123", "user");

SELECT * FROM User WHERE username = "bizzhou" AND password="123";

SELECT username, password ,role FROM User u WHERE u.username = "bizzhou" AND u.password = "123";

SELECT username,password,role FROM User LIMIT 1


INSERT INTO User VALUES ("bizzhou", "123", "admin");
INSERT INTO User VALUES ("emp", "123", "employee");
INSERT INTO User VALUES ("user", "123", "user");


SELECT * FROM User;
