create index lobby_game_idx on lobbies using hash (game_id);

create index games_user_idx on games using btree(user_id);
create index character_user_idx on characters using btree(user_id);
create index lobby_request_character_idx on lobby_requests using btree(character_id);
create index lobby_request_lobby_idx on lobby_requests using btree(lobby_id);
