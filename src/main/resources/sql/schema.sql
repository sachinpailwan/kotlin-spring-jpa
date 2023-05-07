create table if not exists messages (
   id int default RANDOM_UUID() primary key,
   text varchar(255) not null
);