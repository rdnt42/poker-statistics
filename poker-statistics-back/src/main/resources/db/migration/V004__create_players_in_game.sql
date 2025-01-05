create type player_status as enum ('active', 'finished');

create table if not exists players_in_game
(
    id       uuid primary key references players,
    cash_in  integer       not null default 0,
    cash_out integer       not null default 0,
    status   player_status not null
)