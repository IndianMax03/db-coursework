-- Создание ролей
insert into roles values (nextval('roles_id_seq'), 'master');
insert into roles values (nextval('roles_id_seq'), 'admin');

-- Создание тегов
insert into tags values (nextval('tags_id_seq'), 'long');
insert into tags values (nextval('tags_id_seq'), 'medium');
insert into tags values (nextval('tags_id_seq'), 'short');

-- Создание игровых систем
insert into game_systems values (nextval('game_systems_id_seq'), 'DnD');
insert into game_systems values (nextval('game_systems_id_seq'), 'Pathfinder');
