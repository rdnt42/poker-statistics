create table if not exists players
(
    id        uuid primary key,
    name      text,
    nickname  text,
    total_in  integer,
    total_out smallint
)