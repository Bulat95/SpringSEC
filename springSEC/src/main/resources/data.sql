create table MY_USER (login varchar, position varchar, password varchar, role varchar);

insert into my_user(login, position, password, role) values('user', '1', 'password', 'USER');
insert into my_user( login, position, password, role) values('admin', '2', 'password', 'ADMIN');


select * from my_user;