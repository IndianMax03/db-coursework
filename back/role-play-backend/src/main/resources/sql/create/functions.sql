create or replace function create_user(
    login varchar(32),
    name varchar(128),
    password varchar(128),
    picture bytea,
    karma integer,
    timezone text,
    telegram_tag varchar(32),
    vk_tag varchar(32),
    current_status user_status
) returns integer as $$
declare
    new_id integer;
begin
    insert into users (
        login, name, password, picture, karma, timezone, telegram_tag, vk_tag, current_status
    ) values (
        login, name, password, picture, karma, timezone, telegram_tag, vk_tag, current_status
    )
    returning id into new_id;
    return new_id;
end;
$$ language plpgsql;

create or replace function create_friendship(
    sender_user_id integer,
    receiver_user_id integer,
    curr_status request_status
) returns request_status as $$
declare
    inserted_status request_status;
begin
    insert into friendships (
        sender_user_id, receiver_user_id, current_status
    ) values (
        sender_user_id, receiver_user_id, curr_status
    )
    returning current_status into inserted_status;
    return inserted_status;
end;
$$ language plpgsql;

create or replace function create_character(
    name text,
    picture bytea,
    game_system_id integer,
    user_id integer,
    current_status character_status,
    stats bytea
) returns integer as $$
declare
    new_id integer;
begin
    insert into characters (
        name, picture, game_system_id, user_id, current_status, stats
    ) values (
        name, picture, game_system_id, user_id, current_status, stats
    )
    returning id into new_id;
    return new_id;
end;
$$ language plpgsql;

create or replace function is_user_master(
    usr_id integer
) returns boolean as $$
declare
    master_id integer;
    is_master boolean;
begin
    select id into master_id
    from roles
    where name = 'master';

    select exists (
        select 1
        from user_roles
        where user_id = usr_id and role_id = master_id
    ) into is_master;
    return is_master;
end;
$$ language plpgsql;

create or replace function turn_user_to_master(
    usr_id integer
) returns integer as $$
declare
    master_id integer;
    new_master_id integer;
begin
    select id into master_id
    from roles
    where name = 'master';

    insert into user_roles (user_id, role_id)
    values (usr_id, master_id)
    returning user_id into new_master_id;
    return new_master_id;
end;
$$ language plpgsql;

create or replace function create_game(
    name varchar(32),
    game_system_id integer,
    picture bytea,
    master_id integer,
    current_status game_status,
    description text
) returns integer as $$
declare
    new_id integer;
begin
    insert into games (
        name, game_system_id, picture, master_id, creation_date, current_status, finish_date, description
    ) values (
        name, game_system_id, picture, master_id, current_timestamp, current_status, null, description
    )
    returning id into new_id;
    return new_id;
end;
$$ language plpgsql;

create or replace function create_lobby_request(
    lobby_id integer,
    character_id integer,
    current_status request_status
) returns integer as $$
declare
    new_id integer;
begin
    insert into lobby_requests (
        lobby_id, character_id, current_status
    ) values (
        lobby_id, character_id, current_status
    )
    returning id into new_id;
    return new_id;
end;
$$ language plpgsql;

create or replace function create_lobby()
returns trigger as $$
begin
    insert into lobbies (game_id, format)
    values (new.id, 'online');
    return new;
end;
$$ language plpgsql;