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

-- Создание пользователей
select create_user('indian_max03', 'Maxim', 'cors123', null, 112, 'Russia/Moscow', 'tg_max', 'vk_max', 'exists');
select create_user('deaad', 'Ksenia', 'react123', null, 12, 'Russia/Moscow', 'tg_ksenia', 'vk_ksenia', 'exists');

-- Присвоение роли пользователю
insert into user_roles values (2, 1);

-- Создание игры
select create_game(
    'Игра игрулька',
    1,
    null,
    2,
    cast ('not-started' as game_status),
    'Игра для любителей темных лесов и таинственных путешествий! Будь осторожен путник, на твоей дороге могут встретиться самые страшные создания!!!'
);

-- Создание лобби для игры
insert into lobbies (id, game_id, format)
values (1, 1, 'online');

-- Создание персонажа
select create_character('Липрикон', null, 1, 1, cast ('busy' as character_status), null);

-- Создание запроса в лобби
select create_lobby_request(1, 1, cast('on-review' as request_status));

-- Создание дружбы между пользователями
select create_friendship(1, 2, cast('approved' as request_status));

-- Добавление отзыва
insert into reviews values (
    1,
    2,
    'Больше всего зашла последняя часть с мнением автора. было приятно читать и четко виден поинт. прости за рейтинг 3, чуть ли не рандомом определял. спасибо за игру и хорошего дня',
    now(),
    3
);
