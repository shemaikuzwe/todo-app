-- drop table todo;
create  table if not exists todo(
   id serial primary key,
   title varchar(100) not null,
   description varchar(200),
   due_date  timestamp,
   completed_at timestamp,
   priority int
);


