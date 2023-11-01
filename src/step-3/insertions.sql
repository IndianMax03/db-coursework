--  запросы для заполнения таблиц

insert into users (login, name, password, karma, timezone, telegram_tag, vk_tag, current_status)
values ('user1', 'John', '12345', 0, 'UTC+3', '@john', 'vk.com/john', 'exists'),
       ('user2', 'Jane', 'qwerty', 0, 'UTC+5', '@jane', 'vk.com/jane', 'exists'),
       ('user3', 'Bob', 'password123', 0, 'UTC+2', '@bob', 'vk.com/bob', 'exists'),
       ('user4', 'Alice', 'abcde', 0, 'UTC+4', '@alice', 'vk.com/alice', 'exists'),
       ('user5', 'Mike', 'password', 0, 'UTC+1', '@mike', 'vk.com/mike', 'exists');

insert into friendships (sender_user_id, receiver_user_id, current_status)
values (1, 2, 'on-review'),
       (2, 1, 'approved'),
       (1, 3, 'rejected'),
       (3, 1, 'on-review'),
       (1, 4, 'approved'),
       (4, 1, 'on-review');

insert into reviews (reviewer_user_id, recipient_user_id, content, date, rating)
values (2, 1, 'Good user!', now(), 5),
       (3, 1, 'Bad user!', now(), 1),
       (4, 1, 'Great user!', now(), 5);

insert into roles (name)
values ('admin'),
       ('moderator'),
       ('user');

insert into user_roles (user_id, role_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 3),
       (5, 3);

insert into game_systems (name)
values ('D&D'),
       ('Pathfinder'),
       ('Call of Cthulhu'),
       ('Warhammer 40k'),
       ('Shadowrun');

insert into games (name, game_system_id, picture, master_id, creation_date, current_status, finish_date, description)
values ('Adventure in the Forgotten Tree', 1, null, 1, now(), 'not-started', null, 'Join the epic quest to save the world!'),
       ('Rise of the Runelords', 2, null, 2, now(), 'started', now(), 'Will you be able to defeat the ancient evil?'),
       ('The Haunting', 3, null, 3, now(), 'finished', now(), 'Survive the horrors of the cursed mansion!'),
       ('The Emperor Protects', 4, null, 4, now(), 'started', null, 'Fight for the glory of the Imperium!'),
       ('Neon Nights', 5, null, 5, now(), 'not-started', null, 'In the world of shadows and neon lights anything is possible.');

insert into tags (name)
values ('fantasy'),
       ('horror'),
       ('sci-fi'),
       ('adventure'),
       ('mystery');

insert into games_tags (game_id, tag_id)
values (1, 1),
       (1, 4),
       (2, 1),
       (2, 4),
       (3, 2),
       (3, 5),
       (4, 1),
       (4, 3),
       (5, 3),
       (5, 4);

insert into characters (board_game_system, user_id, stats)
values ('D&D', 1, null),
       ('Pathfinder', 2, null),
       ('Call of Cthulhu', 3, null),
       ('Warhammer 40k', 4, null),
       ('Shadowrun', 5, null);

insert into lobbies (game_id, format)
values (1, 'online'),
       (2, 'offline'),
       (3, 'online'),
       (4, 'offline'),
       (5, 'online');

insert into lobby_requests (lobby_id, character_id, current_status)
values (1, 1, 'on-review'),
       (1, 2, 'rejected'),
       (2, 3, 'approved'),
       (3, 4, 'on-review'),
       (3, 5, 'approved');
