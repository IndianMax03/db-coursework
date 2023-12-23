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

create or replace function create_friendship(sender_user_id integer, receiver_user_id integer, curr_status request_status)
returns request_status
as $$
declare
    inserted_status request_status;
begin
    insert into friendships
    values (sender_user_id, receiver_user_id, curr_status)
    returning current_status into inserted_status;
    return inserted_status;
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

create or replace function create_lobby()
returns trigger
as $$
begin
    insert into lobbies
    values (nextval('lobbies_id_seq'), NEW.id, 'online');
    return new;
end;
$$ language plpgsql;

create trigger game_creation
after insert on games
for each row
execute function create_lobby();

create or replace function update_characters_status_while_changing_request_status()
returns trigger
as $$
begin
    if new.current_status = cast('rejected' as request_status) then
        update characters
        set current_status = cast('free' as character_status)
        where id = new.character_id;
    elsif new.current_status <> cast('rejected' as request_status) then
        update characters
        set current_status = cast('busy' as character_status)
        where id = new.character_id;
    end if;
    return new;
end;
$$ language plpgsql;

create trigger update_characters_trigger
after update of current_status on lobby_requests
for each row
execute function update_characters_status_while_changing_request_status();

create trigger insert_characters_trigger
after insert on lobby_requests
for each row
execute function update_characters_status_while_changing_request_status();

create or replace function update_characters_status_after_changing_game_status()
returns trigger
as $$
declare
    changed_game_id integer;
begin
    if new.current_status = cast('finished' as game_status) then
        changed_game_id = new.id;
        update characters
        set current_status = cast('free' as character_status)
        where id in (
            select character_id
            from lobby_requests
            where current_status <> cast('rejected' as request_status)
            and
            lobby_id in (
                select id
                from lobbies
                where game_id = changed_game_id
            )
        );
    end if;
    return new;
end;
$$ language plpgsql;

create trigger update_games_trigger
after update of current_status on games
for each row
execute function update_characters_status_after_changing_game_status();
