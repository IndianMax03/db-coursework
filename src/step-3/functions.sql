--  запросы для создания функций, реализующих бизнес процессы из этапа №1
create function create_game(name varchar(32), game_system_id integer, picture bytea, master_id integer, creation_date timestamp, current_status game_status, finish_date timestamp, description text) returns void as '
    begin
        insert into games (name, game_system_id, picture, master_id, creation_date, current_status, finish_date, description)
        values (name, game_system_id, picture, master_id, creation_date, current_status, finish_date, description);
    end;
' language SQL;

create function create_character(board_game_system varchar(32), user_id integer, stats bytea) returns void as '
    begin
        insert into character (board_game_system, user_id, stats)
        values (board_game_system, user_id, stats);
    end;
' language SQL;

create_lobby_request(lobby_id integer, character_id integer, current_status request_status) returns void as '
     begin
        insert into lobby_request (lobby_id, character_id, current_status)
        values (lobby_id, character_id, current_status);
    end;
' language SQL;

create function create_reviews(reviewer_user_id integer, recipient_user_id integer, content text, date timestamp, rating smallint) returns void as '
    begin
        insert into reviews (reviewer_user_id, recipient_user_id, content, date, rating)
        values (reviewer_user_id, recipient_user_id, content, date, rating);
    end;
' language SQL;

create function approve_request(id integer, new_status request_status) returns void as '
    begin
        update lobby_request
        set current_status = 'approved'
        where request_id = id;
    end;
' language SQL;

create function reject_request(id integer, new_status request_status) returns void as '
    begin
        update lobby_request
        set current_status = 'rejected'
        where request_id = id;
    end;
' language SQL;

create function change_game_status(game_id integer, new_status game_status) returns void as '
    begin
        update games
        set game_status = new_status
        where id = id;
    end;
' language SQL;

create or replace function create_lobby() returns trigger as 
$$
begin
    insert into lobby(game_id) values (NEW.id);
    return NEW;
end;
$$ language plpgsql;

create or replace function update_finish_date() returns trigger as 
$$
begin
    if NEW.current_status <> OLD.current_status then
        NEW.finish_date := CURRENT_TIMESTAMP
    end if;
    return new;
end;
$$ language plpgsql;
