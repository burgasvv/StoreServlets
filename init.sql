drop table if exists order_products;
drop table if exists orders;
drop table if exists products;

create table if not exists products(
    id integer primary key generated by default as identity unique ,
    name varchar(250) not null ,
    description varchar(1000) not null ,
    price numeric check ( price >= 0 ) not null
);

create table if not exists orders(
    id integer primary key generated by default as identity unique ,
    order_number varchar(50) not null ,
    date timestamp not null
);

create table if not exists order_products(
    id integer primary key generated by default as identity unique ,
    order_id integer not null ,
    product_id integer not null ,
    product_count integer not null ,
    foreign key (product_id) references products(id) on delete cascade ,
    foreign key (order_id) references orders(id) on delete cascade
);

insert into products(name, description, price)
values ('Milk', 'For drink', 90.20),
       ('Bread', 'For eat', 50.50),
       ('Apples', 'For eat', 150.50),
       ('Potatoes','For eat',75.34),
       ('Lemonade','For drink',80.45);

insert into orders(order_number, date) values ('C85',now());
insert into orders(order_number, date) values ('A50',now());
insert into orders(order_number, date) values ('B40',now());
insert into orders(order_number, date) values ('A25',now());
insert into orders(order_number, date) values ('B44',now());
insert into orders(order_number, date) values ('C60',now());

insert into order_products(order_id, product_id, product_count) VALUES (1,2,2),(1,1,3),(1,3,9),(1,4,25),(1,5,1);
insert into order_products(order_id, product_id, product_count) VALUES (2,2,2),(2,1,3),(2,3,9),(2,4,25),(2,5,1);
insert into order_products(order_id, product_id, product_count) VALUES (3,2,1),(3,1,1),(3,5,1);
insert into order_products(order_id, product_id, product_count) VALUES (4,3,5),(4,4,12);
insert into order_products(order_id, product_id, product_count) VALUES (5,1,2),(5,2,2),(5,4,10);
insert into order_products(order_id, product_id, product_count) VALUES (6,3,5),(6,5,2),(6,2,2),(6,4,8);