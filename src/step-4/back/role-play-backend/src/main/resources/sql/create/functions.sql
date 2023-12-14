create or replace function create_user(login varchar(32), name varchar(128), password varchar(128), picture bytea, karma integer, timezone text, telegram_tag varchar(32), vk_tag varchar(32), current_status user_status)
returns void
as $$
begin
    insert into users
    values (nextval('users_id_seq'), login, name, password, picture, karma, timezone, telegram_tag, vk_tag, current_status);
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
