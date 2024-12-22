# Рефакторинг баз данных и приложений для курсовой работы по дисциплине "Информационные системы и базы данных"

Итерация №3

## Запуск приложения для MacOS

Для начала установите утилиты по менеджменту k8s и его кластера:
```zsh
brew install kubernetes-cli
brew install minikube
```

Запустите кластер k8s на вашей машине:
```zsh
minikube start
```

Для доступа в консоль администратора воспользуйтесь командой:
```zsh
minikube dashboard
```

Примените существующий файл конфигурации k8s:
```zsh
kubectl apply -f k8s.yaml
```

Определите имя созданного pod:
```zsh
indianmax@MacBook-Pro-Manu db-coursework % kubectl get pods       
NAME                                 READY   STATUS    RESTARTS   AGE
role-play-5fd98b8dc9-4v9th           3/3     Running   0          22m
```

Для упрощения доступа к приложению выполните проброс портов:
```zsh
kubectl port-forward pod/<pod-name> 3000:3000
kubectl port-forward pod/<pod-name> 8081:8081
```

Теперь вы можете выполнить заполнение базы данных тестовыми значениями:
```zsh
sh ./back/role-play-backend/requests.sh
```

И обнаружить наше приложение по адресу:
```zsh
http://localhost:3000/login
```

## Документация

Подробное описание настоящего проекта представлено в документации [docs/Refactoring.md](docs/Refactoring.md)

## Выполнили:

- М.Р.Тучков
- К.М.Кондратьева
