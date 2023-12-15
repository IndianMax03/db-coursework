# NOTE

Любой запрос, пришедший без токена (кроме регистрации и логина) будет отклонен с кодом ошибки 401 (UNAUTHORIZED)

# Ручки

- `/enter/register` : **регистрация**
    - POST: login, name, password, [timezone, telegramTag, vkTag]
    - returns: token в хедере, в боди весь юзер (с айдишником)
- `/enter/login` : **логин**
    - POST: login, password
    - returns: token в хедере, в боди весь юзер

---

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

---

- `characters/create` : **создать персонажа**
    - POST: name, gameSystemId, userId, [picture, stats]
    - returns: информация о созданном персонаже (с айдишником)

---

- `games/create` : **создать игру**
    - POST: name, gameSystemId, masterId, [picture, currentStatus, finishDate, description]
    - returns: созданную игру с заполненными айди и датой создания