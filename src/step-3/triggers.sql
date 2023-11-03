create trigger add_lobby_to_game
after insert on games
for each row 
execute function create_lobby();

create trigger add_finish_date
after update on games 
for each row
exucute function update_finish_date();

create trigger check_master_role
before update on games
for each row 
execute function check_if_master_role_is_present();

create trigger check_friendship
before update on friendship
for each row 
execute function check_if_friendship_already_exists();

create trigger check_character_game_system_id 
before update on lobby_request
for each row 
execute function check_character_game_system_correct();

create trigger check_if_character_is_available
before update on lobby_request
for each row 
execute function  check_if_character_is_already_in_game();

create trigger update_character_status
after update on games
for each row 
execute function change_character_status();