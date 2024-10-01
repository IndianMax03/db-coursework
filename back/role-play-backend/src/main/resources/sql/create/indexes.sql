create index if not exists lobby_game_idx on lobbies using hash(game_id);
create index if not exists games_user_idx on games using btree(master_id);
create index if not exists character_user_idx on characters using btree(user_id);
create index if not exists lobby_request_character_idx on lobby_requests using btree(character_id);
create index if not exists lobby_request_lobby_idx on lobby_requests using btree(lobby_id);
