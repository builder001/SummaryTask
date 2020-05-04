SET NAMES utf8;

DROP DATABASE IF EXISTS library;
CREATE DATABASE library CHARACTER SET utf8 COLLATE utf8_bin;

USE library;

create table categories
(
  id int not null auto_increment
    primary key,
  name varchar(50) not null,
  constraint id_UNIQUE
  unique (id),
  constraint nameCategory_UNIQUE
  unique (name)
)
;

INSERT INTO categories VALUES(1, 'Politics');
INSERT INTO categories VALUES(2, 'Economy');
INSERT INTO categories VALUES(3, 'Sport');
INSERT INTO categories VALUES(4, 'Different');

create table editions
(
  id int not null auto_increment
    primary key,
  name varchar(100) not null,
  price int not null,
  categories_id int not null,
  constraint id_UNIQUE
  unique (id),
  constraint fk_editions_categories1
  foreign key (categories_id) references categories (id)
    on delete cascade
)
;

create index fk_editions_categories1_idx
  on editions (categories_id)
;

INSERT INTO editions VALUES(1, 'What You Should Know About Politics…But Don’t', 150, 1);
INSERT INTO editions VALUES(2, 'Fascism: A Warning', 350, 1);
INSERT INTO editions VALUES(3, 'Why Nations Fail', 220, 1);
INSERT INTO editions VALUES(4, 'Basic Economics', 290, 2);
INSERT INTO editions VALUES(5, 'Capital', 1290, 2);
INSERT INTO editions VALUES(6, 'MoneyBall', 990, 3);
INSERT INTO editions VALUES(7, 'The Silent Patient', 110, 4);
INSERT INTO editions VALUES(8, 'Before I go to Sleep', 110, 4);
INSERT INTO editions VALUES(9, 'The Wife Between Us', 110, 4);

create table orders
(
  id int not null auto_increment
    primary key,
  bill int not null,
  users_id int not null,
  statuses_id int not null,
  constraint id_UNIQUE
  unique (id)
)
;

create index fk_orders_statuses1_idx
  on orders (statuses_id)
;

create index fk_orders_users1_idx
  on orders (users_id)
;

INSERT INTO orders VALUES(DEFAULT, 0, 2, 0);
INSERT INTO orders VALUES(DEFAULT, 0, 2, 3);

create table orders_editions
(
  orders_id int not null,
  editions_id int not null,
  constraint fk_orders_editions_orders1
  foreign key (orders_id) references orders (id),
  constraint fk_orders_editions_editions1
  foreign key (editions_id) references editions (id)
    on delete cascade
)
;

create index fk_orders_editions_editions1_idx
  on orders_editions (editions_id)
;

create index fk_orders_editions_orders1_idx
  on orders_editions (orders_id)
;

INSERT INTO orders_editions VALUES(1, 1);
INSERT INTO orders_editions VALUES(1, 7);
INSERT INTO orders_editions VALUES(1, 5);

INSERT INTO orders_editions VALUES(2, 1);
INSERT INTO orders_editions VALUES(2, 7);

create table roles
(
  id int not null primary key,
  name varchar(15) not null,
  constraint nameRole_UNIQUE
  unique (name)
)
;

INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'librarian');
INSERT INTO roles VALUES(2, 'reader');

create table statuses
(
  id int not null primary key,
  name varchar(30) not null,
  constraint nameStatus_UNIQUE
  unique (name)
)
;

INSERT INTO statuses VALUES(0, 'opened');
INSERT INTO statuses VALUES(1, 'confirmed');
INSERT INTO statuses VALUES(2, 'paid');
INSERT INTO statuses VALUES(3, 'closed');

alter table orders
  add constraint fk_orders_statuses1
foreign key (statuses_id) references statuses (id)
;

create table user_lock
(
  id int not null auto_increment
    primary key,
  name varchar(15) not null,
  constraint id_UNIQUE
  unique (id)
)
;

INSERT INTO user_lock VALUES(DEFAULT, 'Nikolay');
INSERT INTO user_lock VALUES(DEFAULT, 'Boris');
INSERT INTO user_lock VALUES(DEFAULT, 'Mike');

create table users
(
  id int not null auto_increment
    primary key,
  login varchar(20) not null,
  password varchar(128) not null,
  first_name varchar(20) not null,
  last_name varchar(20) not null,
  email varchar(100) not null,
  roles_id int not null,
  bill int not null,
  user_lock_id int not null,
  constraint id_UNIQUE
  unique (id),
  constraint login_UNIQUE
  unique (login),
  constraint fk_users_roles1
  foreign key (roles_id) references roles (id)
    on delete cascade,
  constraint fk_users_user_lock1
  foreign key (user_lock_id) references user_lock (id)
    on delete cascade
)
;
-- id = 1
INSERT INTO users VALUES (DEFAULT, 'admin', 'Admin123', 'John', 'Doe', 'admin@example.com', 0, 0, 2);
-- id = 2
INSERT INTO users VALUES (DEFAULT, 'librarian', 'Librarian123', 'Petr', 'Petrov', 'petrov@example.com', 1, 0, 3);
-- id = 3
INSERT INTO users VALUES (DEFAULT, 'reader', 'Reader123', 'Ivan', 'Ivanov', 'ivanov@example.com', 2, 0, 2);

create index fk_users_roles1_idx
  on users (roles_id)
;

create index fk_users_user_lock1_idx
  on users (user_lock_id)
;

alter table orders
  add constraint fk_orders_users1
foreign key (users_id) references users (id)
;

