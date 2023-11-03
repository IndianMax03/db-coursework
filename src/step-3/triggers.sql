create trigger add_lobby_to_game
after insert on games
for each row 
execute function create_lobby();

create trigger add_finish_date
after update on games 
for each row
exucute function update_finish_date();