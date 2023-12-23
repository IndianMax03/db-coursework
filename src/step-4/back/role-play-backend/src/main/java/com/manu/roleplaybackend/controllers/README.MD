# NOTE

Любой запрос, пришедший без токена (кроме регистрации и логина) будет отклонен с кодом ошибки 401 (UNAUTHORIZED)

# Ручки

## Вход в приложение

- `/enter/register` : **регистрация**
    - POST: login, name, password, [timezone, telegramTag, vkTag]
    - returns: token в хедере, в боди весь юзер (с айдишником)
- `/enter/login` : **логин**
    - POST: login, password
    - returns: token в хедере, в боди весь юзер

## Информация о юзере

- `/users/all` : **получить всех юзеров**
    - GET
    - returns: массив всех юзеров
- `/users/{login}` : **получить юзера по логину**
    - GET: {login} в переменной пути
    - returns: информация о пользователе (кроме пароля)
- `/users/roles/{login}` : **получить роли юзера**
    - GET: {login} в переменной пути
    - returns: список ролей (id, name)
- `/users/characters/{login}` : **получить персонажей юзера**
    - GET: {login} в переменной пути
    - returns: список персонажей
- `/users/games/{login}` : **получить игры юзера**
    - GET: {login} в переменной пути
    - returns: список игр
- `/users/update/karma` : **изменить карму для игрока (+- 1)**
    - PATCH: senderUserId, receiverUserId, {increase = true | decrease = true}
    - returns: новое значение karma для receiverUserId

## Информация о персонажах

- `characters/create` : **создать персонажа**
    - POST: name, gameSystemId, userId, [picture, stats]
    - returns: информация о созданном персонаже (с айдишником)
- `characters/request` : **создать запрос в лобби**
    - POST: lobbyId, characterId
    - returns: сущность lobby request

## Информация об играх

- `games/create` : **создать игру**
    - POST: name, gameSystemId, masterId, [picture, currentStatus, finishDate, description]
    - returns: созданную игру с заполненными айди и датой создания
- `/games/all` : **получить все игры**
    - GET
    - returns: массив всех игр
- `/games/update/status` : **изменить статус игры**
    - PATCH: id, currentStatus
    - returns: см. статус ответа
