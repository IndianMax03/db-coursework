response=$(
  curl -i -X POST http://localhost:8081/enter/register \
  -H "Content-Type: multipart/form-data" \
  -k \
  -F "login=maxim" \
  -F "name=Максимилианчик" \
  -F "password=123" \
  -F "timezone=Russia/Moscow" \
  -F "telegramTag=mamibobi" \
  -F "vkTag=vkmamibobi" \
)

token=$(echo "$response" | grep -Fi 'token:' | awk '{print $2}' | tr -d '\r')

curl -i -X POST http://localhost:8081/enter/register \
-H "Content-Type: multipart/form-data" \
-k \
-F "login=Ksenia" \
-F "name=Ксюнечка" \
-F "password=321" \
-F "timezone=Russia/Moscow" \
-F "telegramTag=kskschkchkmumua" \
-F "vkTag=vkkskschkchkmumua"

curl -X GET http://localhost:8081/users/maxim/roles/become-master \
-H "Content-Type: application/json" \
-H "token: $token" \
-k

curl -i -X POST http://localhost:8081/games \
-H "Content-Type: multipart/form-data" \
-H "token: $token" \
-k \
-F "name=Подземелье вкусностей" \
-F "gameSystemId=1" \
-F "masterId=1" \
-F "description=Легенды гласят, что правитель золотого королевства пообещал одарить несметными богатствами героя, который сумеет победить тёмного мага. Могущественный колдун сумел не только уничтожить всё живое, но и превратить королевство в руины."

curl -i -X POST http://localhost:8081/characters \
-H "Content-Type: multipart/form-data" \
-H "token: $token" \
-k \
-F "name=Мишка косолапый" \
-F "gameSystemId=1" \
-F "userId=1"

curl -i -X POST http://localhost:8081/characters \
-H "Content-Type: multipart/form-data" \
-H "token: $token" \
-k \
-F "name=Дюймовочка" \
-F "gameSystemId=1" \
-F "userId=2"
