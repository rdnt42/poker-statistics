create table if not exists historical_games
(
    id            uuid primary key,
    start_date    timestamp with time zone not null,
    end_date      timestamp with time zone not null,
    total_cash    integer                  not null default 0,
    total_players smallint                 not null default 0
)