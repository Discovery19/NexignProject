# Telecom #
### Стек технологий  ###
- OpenJDK 21 (17)
- Maven
- Spring Boot 3.2.4
- Kafka
- Docker
- Swagger
- PostgreSQL
### Краткое описание проекта ###
Данный сервис эмулирует работу телефонной компании. Он состоит из 4 микросервисов: CDR, BTR, HRS, CRM.

**CDR**
*CDR – Call Data Record*

1. Генерирует случайные записи о звонках абонентов (как оператора «Ромашка», так и другого оператора).
2. Формирует CDR файл в текстовом формате, содержащий 10 записей о звонках абонентов.
3. Отправляет CDR файл на сервис BRT, через Kafka.

**BRT**

*BRT – Billing Real Time*
1. Содержит базу данных об абонентах: номере телефона, тарифе и текущем балансе. 
2. Получает данные о звонках от CDR файла. 
3. Осуществляет авторизацию абонентов оператора «Ромашка», отсеивая записи, которые принадлежат другим операторам. 
4. Передает данные о звонках и тарифе на сервис HRS для осуществления расчета абонента. 
5. Осуществляет списание со счета абонентов на основе полученных данных с сервиса HRS.

**HRS**

*Сервис HRS:*
HRS – High performance rating server.
1.	Получает данные о длительности звонков и тарифе абонентов от сервиса BRT. 
2.	Рассчитывает сумму списания с баланса абонента на основе условий его тарифа.
3.	Обратно передает данные о расчетах на сервис BRT для списания с баланса.

**CRM**

![image](https://github.com/Discovery19/NexignProject/assets/112725051/f82675a4-c2d6-4bd9-b088-d9a79f143120)

### Схема БД ###
![image](https://github.com/Discovery19/NexignProject/assets/112725051/6fe1cf07-e5d9-4ae1-82f3-67dcc42ca753)

![image](https://github.com/Discovery19/NexignProject/assets/112725051/6f9f6f8b-c8de-4a26-a547-40363556b364)

### Схема взаимодействия сервисов ###

![image](https://github.com/Discovery19/NexignProject/assets/112725051/8fec2d5b-2cfe-4fba-ba60-69837b642301)

### Как работать с приложением ###
**Запуск приложения**
Выполните следующие команды))
```
docker compose -f compose.yaml build  
docker compose -f compose.yaml up    
```

**Для запуска приложения, без собранных контейнеров в docker:**

Для начала в application.yaml в каждом микросервисе раскоментируйте данные строчки:
*Пример (BRT-service):*
```app:
  cdr-listener-topic: cdr-topic
  brt-producer-topic: brt-topic
  hrs-listener-topic: hrs-topic
  hrs-producer-month-topic: hrs-month-topic
  server: # localhost:9092                        #Здесь раскоментировать!
spring:
  application:
    name: btr-service
  datasource:
#      url: jdbc:postgresql://localhost:5433/main  #Здесь раскоментировать!
#      username: postgres                          #Здесь раскоментировать!
#      password: postgres                          #Здесь раскоментировать!
      driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update

server:
  port: 8081
```

Затем выполните следующую команду

```  docker compose -f compose_no_services.yaml up ```

**Использование CRM:**
> Для более наглядной работы советуется уменьшить длительность периода тарификации и количество файлов на каждый месяц в application.yaml (CDR)

Для Админа:

![image](https://github.com/Discovery19/NexignProject/assets/112725051/32e57acd-967d-4859-abfb-bf4a2fd88133)

*Логин пароль менеджера*

![image](https://github.com/Discovery19/NexignProject/assets/112725051/edf32126-2d1f-40b8-9bca-badbd3a398cd)

Для пользователя:

![image](https://github.com/Discovery19/NexignProject/assets/112725051/1f4b9662-e67f-4774-80ea-5b5dd113468e)

*Логин пользователя вводится как часть строки, а пароль не нужен по заданию*

Выше был приложен пример в Postman, но также можно сипользовать Swagger. Вот ссылка для него:

*https://localhost:8083/swagger-ui.html*
