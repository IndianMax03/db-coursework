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

DO $$
begin
   if not exists (select 1 from pg_type where typname = 'user_status') then
      create type user_status as enum ('exists', 'deleted');
   end if;
end $$;

DO $$
begin
   if not exists (select 1 from pg_type where typname = 'game_status') then
      create type game_status as enum ('not-started', 'started', 'finished');
   end if;
end $$;

DO $$
begin
   if not exists (select 1 from pg_type where typname = 'request_status') then
      create type request_status as enum ('on-review', 'rejected', 'approved');
   end if;
end $$;

DO $$
begin
   if not exists (select 1 from pg_type where typname = 'game_format') then
      create type game_format as enum ('online', 'offline');
   end if;
end $$;

DO $$
begin
   if not exists (select 1 from pg_type where typname = 'character_status') then
      create type character_status as enum ('free', 'busy');
   end if;
end $$;
