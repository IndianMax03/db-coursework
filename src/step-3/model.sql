--  запросы для инициализации модели

create type user_status as enum ('exists', 'deleted');

create table if not exists users (
    id serial primary key,
    login varchar(32) not null,
    name varchar(128) not null,
    password varchar(128) not null,
    karma integer default 0,
    timezone text,
    telegram_tag varchar(32),
    vk_tag varchar(32),
    current_status user_status not null
);

create type request_status as enum ('on-review', 'rejected', 'approved');

create table if not exists friendships (
    sender_user_id integer references users (id),
    receiver_user_id integer references users (id),
    current_status request_status not null,
    primary key (sender_user_id, receiver_user_id)
);

create table if not exists reviews (
    reviewer_user_id integer references users (id),
    recipient_user_id integer references users (id),
    content text,
    date timestamp,
    rating smallint,
    primary key (reviewer_user_id, recipient_user_id)
);

create table if not exists roles (
    id serial primary key,
    name varchar(32) not null
);

create table if not exists user_roles (
    user_id integer references users (id),
    role_id integer references roles (id),
    primary key (user_id, role_id)
);

create type game_status as enum ('not-started', 'started', 'finished');

create table if not exists games (
    id serial primary key,
    name varchar(32) not null,
    board_game_system varchar(32), --  мб enum/новую таблу?
    picture bytea,
    master_id integer references users (id), --  instead of user_id
    creation_date timestamp,
    current_status game_status, --  instead of varchar(32)
    game_type varchar(32), --  мб enum/новую таблу?
    finish_date timestamp,
    description text --  instead of content
);

create table if not exists tags (
    id serial primary key,
    name varchar(32) not null
);

create table if not exists games_tags (
    game_id integer references games (id),
    tag_id integer references tags (id),
    primary key (game_id, tag_id)
);

create table if not exists characters (
    id serial primary key,
    board_game_system varchar(32),
    --  lobby_request_id
    user_id integer references users (id),
    --  status
    stats bytea
);

create table if not exists lobbies (
    id serial primary key,
    game_id integer references games (id),
    lobby_type varchar(32) --  мб enum/новую таблу?
);

create table if not exists lobby_requests (
    id serial primary key,
    lobby_id integer references lobbies (id),
    character_id integer references characters (id),
    current_status request_status
);

--  имхо таблица notifications - бесполезная, потому что нет смысл отправлять изменение статуса запроса (просто обнови страницу)
