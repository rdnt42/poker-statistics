create table if not exists historical_games
(
    id            uuid primary key,
    start_date    timestamp with time zone,
    end_date      timestamp with time zone,
    total_cash    integer,
    total_players smallint
)