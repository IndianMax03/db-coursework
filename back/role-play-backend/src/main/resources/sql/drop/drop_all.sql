--  запросы для удаления индексов

drop index lobby_game_idx;
drop index games_user_idx;
drop index character_user_idx;
drop index lobby_request_character_idx;
drop index lobby_request_lobby_idx;

--  запросы для удаления таблиц

drop table friendships;
drop table games_tags;
drop table lobby_requests;
drop table reviews;
drop table tags;
drop table user_roles;
drop table characters cascade;
drop table game_systems cascade;
drop table games cascade;
drop table lobbies cascade;
drop table roles cascade;
drop table users cascade;

--  запросы для удаления функций

drop function create_user;
drop function create_friendship;
drop function create_character;
drop function is_user_master;
drop function turn_user_to_master;
drop function create_game;
drop function create_lobby_request;
drop function create_lobby;

--  запросы для удаления енамов

drop type game_format cascade;
drop type game_status cascade;
drop type request_status cascade;
drop type user_status cascade;
drop type character_status cascade;
