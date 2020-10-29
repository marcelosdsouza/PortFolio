CREATE TABLE agenda
( id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20),
phone CHAR(10),
address VARCHAR(50),
email VARCHAR(50),
role VARCHAR(20)
);

INSERT INTO agenda(name, phone, address, email, role) VALUES ('Marcelo', 1234567890, '1234 here street', 'marcelo@gmail.com','Admin'),
('Mark', 1098765432, '1234 nowhere street', 'marcelo@sheridancollege.com','Member'),
('Dummy Contact', 1237894560, '1234 right now street, Toronto, ON', 'dummy@email.com','Guest'),
('Not Dummy Contact', 9876543210, '1234 right here street', 'notDummy@gmail.com','Member'),
('Souza', 1234567890, '1234 here street', 'marcelo@gmail.com','Admin'),
('Silva', 1098765432, '1234 nowhere street', 'marcelo@sheridancollege.com','Admin'),
('Contact', 1237894560, '1234 right now street, Toronto, ON', 'dummy@email.com','Member'),
('Not a Contact', 9876543210, '1234 right here street', 'notDummy@gmail.com','Guest');

create table SEC_USER
(
  userId           BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userName         VARCHAR(36) NOT NULL UNIQUE,
  encryptedPassword VARCHAR(128) NOT NULL,
  ENABLED           BIT NOT NULL 
) ;


create table SEC_ROLE
(
  roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
) ;


create table USER_ROLE
(
  ID      BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

alter table USER_ROLE
  add constraint USER_ROLE_UK unique (userId, roleId);

alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (userId)
  references SEC_USER (userId);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (roleId)
  references SEC_ROLE (roleId);
  

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Admin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Not Admin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Guest', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
insert into sec_role (roleName)
values ('ROLE_ADMIN');
 
insert into sec_role (roleName)
values ('ROLE_MEMBER');
 
insert into sec_role (roleName)
values ('ROLE_GUEST');
 
insert into user_role (userId, roleId)
values (1, 1);
 
insert into user_role (userId, roleId)
values (1, 2);
 
insert into user_role (userId, roleId)
values (2, 2);

insert into user_role (userId, roleId)
values (2, 3);

insert into user_role (userId, roleId)
values (3, 3);

COMMIT;