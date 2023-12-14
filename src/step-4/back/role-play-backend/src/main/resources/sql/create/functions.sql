create or replace function create_user(login varchar(32), name varchar(128), password varchar(128), picture bytea, karma integer, timezone text, telegram_tag varchar(32), vk_tag varchar(32), current_status user_status)
returns void
as $$
begin
    insert into users
    values (nextval('users_id_seq'), login, name, password, picture, karma, timezone, telegram_tag, vk_tag, current_status);
end;
$$ language plpgsql;
