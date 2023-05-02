CREATE TABLE users
(
    id       serial primary key not null,
    email    varchar(50),
    password varchar(120),
    username varchar(20)
);

CREATE TABLE roles
(
    id   serial primary key not null,
    name varchar(30)
);

INSERT INTO roles(name)
values ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_MODERATOR');

CREATE TABLE user_roles
(
    user_id int not null ,
    role_id int not null,
    primary key (user_id,role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);


CREATE TABLE shops
(
    id           serial primary key not null,
    name_of_shop varchar(30)
);

INSERT INTO shops(name_of_shop)
values ('Лента'),
       ('Авито'),
       ('Пятёрочка'),
       ('Авто-ру');

CREATE TABLE categories
(
    id               serial primary key not null,
    name_of_category varchar(30)
);

INSERT INTO categories(name_of_category)
values ('cars'),
       ('food'),
       ('clothes');

CREATE TABLE products
(
    id              serial primary key not null,
    category_id     integer            not null,
    name_of_product varchar(30)        not null,
    foreign key (category_id) references categories (id)
);

INSERT INTO products(category_id, name_of_product)
values (1,'bmw'),
       (1,'kia'),
       (2,'milk'),
       (2,'bread'),
       (3,'shirt'),
       (3,'jacket');


CREATE TABLE products_monitoring
(   id serial not null primary key ,
    product_id bigint not null ,
    shop_id    bigint not null,
    price      double precision,
    make_date  timestamp without time zone default (now() at time zone 'Europe/Moscow'),
    foreign key (product_id) references products(id),
    foreign key (shop_id) references shops(id)
);