create or replace function create_user(login varchar(32), name varchar(128), password varchar(128), picture bytea, karma integer, timezone text, telegram_tag varchar(32), vk_tag varchar(32), current_status user_status)
returns integer
as $$
declare
    new_id integer;
begin
    insert into users
    values (nextval('users_id_seq'), login, name, password, picture, karma, timezone, telegram_tag, vk_tag, current_status)
    returning id into new_id;
    return new_id;
end;
$$ language plpgsql;

create or replace function create_character(name text, picture bytea, game_system_id integer, user_id integer, current_status character_status, stats bytea)
returns integer
as $$
declare
    new_id integer;
begin
    insert into characters
    values (nextval('characters_id_seq'), name, picture, game_system_id, user_id, current_status, stats)
    returning id into new_id;
    return new_id;
end;
$$ language plpgsql;

create or replace function is_user_master(usr_id integer)
returns boolean
as $$
declare
    master_id integer;
    is_master boolean;
begin
    select id from roles where name = 'master' into master_id;
    select exists (select * from user_roles where (user_id = usr_id and role_id = master_id)) into is_master;
    return is_master;
end
$$ language plpgsql;

create or replace function create_game(name varchar(32), game_system_id integer, picture bytea, master_id integer, current_status game_status, description text)
returns integer
as $$
declare
    curr_timstmp timestamp;
    new_id integer;
begin
    select current_timestamp into curr_timstmp;
    insert into games
    values (nextval('games_id_seq'), name, game_system_id, picture, master_id, curr_timstmp, current_status, null, description)
    returning id into new_id;
    return new_id;
end;
$$ language plpgsql;

create or replace function create_lobby_request(lobby_id integer, character_id integer, current_status request_status)
returns integer
as $$
declare
    new_id integer;
begin
    insert into lobby_requests
    values (nextval('lobby_requests_id_seq'), lobby_id, character_id, current_status)
    returning id into new_id;
    return new_id;
end;
$$ language plpgsql;
