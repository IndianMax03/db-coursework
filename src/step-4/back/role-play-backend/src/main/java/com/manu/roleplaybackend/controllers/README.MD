# NOTE

Любой запрос, пришедший без токена (кроме регистрации и логина) будет отклонен с кодом ошибки 401 (UNAUTHORIZED)

# Ручки

- `/enter/register` : **регистрация**
    - POST: login, name, password, [timezone, telegramTag, vkTag]
    - returns: token в хедере
- `enter/login` : **логин**
    - POST: login, password
    - returns: token в хедере

---

- `users/all` : **получить всех юзеров**
    - GET
    - returns: массив всех юзеров
- `users/{login}` : **получить юзера по логину**
    - GET: {login} в переменной пути
    - returns: информация о пользователе (кроме пароля)
