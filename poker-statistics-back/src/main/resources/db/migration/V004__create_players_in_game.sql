drop type if exists player_status;
create type player_status as enum ('ACTIVE', 'FINISHED');

create table if not exists players_in_game
(
    id             uuid primary key references players,
    active_game_id uuid references active_games,
    cash_in        integer       not null default 0,
    cash_out       integer,
    status         player_status not null
)

--  TODO constraint for cash_out + status finished