create table if not exists active_games
(
    id         uuid primary key,
    start_date timestamp with time zone
)