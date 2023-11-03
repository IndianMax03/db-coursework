# Анализ использования созданной базы данных

todo: строится на основе функций и процедур (там смотрим, к каким объектам обращаемся чаще всего)

# Создание индексов

Было принято создать следующие индексы:

## hash:

### `lobby_game_idx` для столбца `lobbies`.`game_id`

Поскольку таблицы `lobbies` и `games` имеют соотношение 1-1, и в базу данных часто будут осуществляться запросы для получения `games`.`id` через таблицу `lobbies`.

Доказательство:

![lobby_game_idx.png](https://github.com/IndianMax03/db-coursework/blob/main/img/lobby_game_idx.png)

## btree:

### `games_user_idx` для столбца `games`.`user_id`

Поскольку игры тесно связаны с юзерами, в бд часто будут поступать запросы для получения всех игр мастера по его айди. Для оптимизации подобных запросов было решено добавить этот индекс.

Доказательство:

![games_user_idx.png](https://github.com/IndianMax03/db-coursework/blob/main/img/games_user_idx.png)

### `character_user_idx` для столбца `characters`.`user_id`

По аналогии с играми, персонажи также будут часто запрашиваться по айди пользователя, следовательно, оптимально создать индекс.

Доказательство:

![character_user_idx.png](https://github.com/IndianMax03/db-coursework/blob/main/img/character_user_idx.png)

### `lobby_request_character_idx` для столбца `lobby_requests`.`character_id`

Для самих же персонажей актуальной информацией будет состяние запроса в лобби. А значит оптимален индекс по айди персонажа в таблице запросов к лобби.

Доказательство:

![lobby_request_character_idx.png](https://github.com/IndianMax03/db-coursework/blob/main/img/lobby_request_character_idx.png)

### `lobby_request_lobby_idx` для столбца `lobby_requests`.`lobby_id`

Для запросов к лобби также будет полезным использовать индекс для столбца айди лобби (в том числе для оптимизации соединений)

Доказательство:

![lobby_request_lobby_idx.png](https://github.com/IndianMax03/db-coursework/blob/main/img/lobby_request_lobby_idx.png)
