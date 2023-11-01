--  запросы для инициализации модели

create type user_status as enum ('exists', 'deleted');

create table if not exists users (
    id serial primary key,
    login varchar(32) not null,
    name varchar(128) not null,
    password varchar(128) not null,
    karma integer default 0 not null,
    timezone text,
    telegram_tag varchar(32),
    vk_tag varchar(32),
    current_status user_status not null
);

create type request_status as enum ('on-review', 'rejected', 'approved');

create table if not exists friendships (
    sender_user_id integer references users (id) on delete restrict,
    receiver_user_id integer references users (id) on delete restrict,
    current_status request_status not null,
    primary key (sender_user_id, receiver_user_id)
);

create table if not exists reviews (
    reviewer_user_id integer references users (id) on delete restrict,
    recipient_user_id integer references users (id) on delete restrict,
    content text,
    date timestamp not null,
    rating smallint not null,
    primary key (reviewer_user_id, recipient_user_id)
);

create table if not exists roles (
    id serial primary key,
    name varchar(32) not null
);

create table if not exists user_roles (
    user_id integer references users (id) on delete restrict,
    role_id integer references roles (id) on delete cascade,
    primary key (user_id, role_id)
);

create table if not exists game_systems ( --  новая таблица (d&d, pf, ...)
    id serial primary key,
    name varchar(32) not null
);

create type game_status as enum ('not-started', 'started', 'finished');

create table if not exists games (
    id serial primary key,
    name varchar(32) not null,
    game_system_id integer references game_systems (id) on delete restrict, --  ссылка на новую таблицу
    picture bytea,
    master_id integer references users (id) on delete restrict, --  instead of user_id
    creation_date timestamp not null,
    current_status game_status not null, --  instead of varchar(32)
    --  game_type убрана в пользу такого же поля в lobbies
    finish_date timestamp,
    description text --  instead of content
);

create table if not exists tags (
    id serial primary key,
    name varchar(32) not null
);

create table if not exists games_tags (
    game_id integer references games (id) on delete restrict,
    tag_id integer references tags (id) on delete cascade,
    primary key (game_id, tag_id)
);

create table if not exists characters (
    id serial primary key,
    board_game_system varchar(32),
    user_id integer references users (id) on delete restrict,
    stats bytea
);

create type game_format as enum ('online', 'offline');

create table if not exists lobbies (
    id serial primary key,
    game_id integer references games (id) on delete restrict,
    format game_format --  теперь enum
);

create table if not exists lobby_requests (
    id serial primary key,
    lobby_id integer references lobbies (id) on delete restrict,
    character_id integer references characters (id) on delete cascade,
    current_status request_status
);

--  имхо таблица notifications - бесполезная, потому что нет смысл отправлять изменение статуса запроса (просто обнови страницу)
