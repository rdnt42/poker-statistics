create table if not exists players
(
    id        uuid primary key,
    name      text,
    nickname  text     not null unique,
    total_in  integer  not null default 0,
    total_out smallint not null default 0
)