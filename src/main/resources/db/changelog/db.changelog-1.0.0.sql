CREATE TABLE user(
   id int auto_increment PRIMARY KEY,
   deleted_at date,
   username VARCHAR(20) not null,
   email VARCHAR(40) not null,
   password VARCHAR(256) not null,
   role VARCHAR(20) not null
);