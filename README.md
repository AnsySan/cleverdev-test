# Тестовое задание

## Требования

Реализовать импорт данных из Старой системы в Новую систему. Импортируются текстовые заметки.  Текстовая заметка делается конкретным пользователем (user) для конкретного пациента (clients в Старой системе и patients в Новой системе). При импорте:

•	Заметка в Старой системе идентифицируется по полю guid заметки в Старой системе.  
•	Дата создания заметки в Новой системе должна равняться дате создания заметки в Старой системе  
•	В новой системе заметка должна также привязываться к пациенту и пользователю системы.   
•	Сопоставление клиентов из Старой системы и пациентов из Новой системы:   
1)	Одному пациенту в Новой системе может соответствовать либо один и более клиентов в Старой системе, либо ни одного клиента.
2)	Одному клиенту в Старой системе соответствует один пациент либо ни одного пациента в Новой системе.
3)	В старой системе идентификатором клиента является поле guid.
4)	В Новой системе у пациента в таблице patient_profile есть строковое поле old_client_guid которое содержит набор guid из старой системы записанных через разделитель (запятую). Можно предложить и реализовать альтернативный механизм хранения списка guid в patient_profile.
   
•	Сопоставление пользователей из Строй и Новой систем:  
1)	Одному пользователю в Старой системе соответствует один пользователь в Новой системе, либо ни одного пользователя.
2)	Идентификатором пользователя в Старой системе является логин  пользователя client_note.logged_user.
3)	Пользователь в Новой системе имеет поле company_user.login. Для одного и того же пользователя логины в обеих системах совпадают. Если пользователя с таким логином в новой системе нет, то его необходимо будет создать.
   
•	Импорт в приложении запускается по расписанию без участия пользователя.  
•	Импортируются заметки только для пациентов которые имеют в Новой системе статус Active (patient_profile.status_id имеет одно из следующих значений 200,210,230)  
•	Импорт должен также обновлять в Новой системе импортированные ранее заметки, если они были изменены в Старой системе. Если заметка была изменена и в старой, и в новой системе, то должна сохраниться наиболее поздняя по дате изменения версия.  
•	Реализовать логирование сбоев в процессе импорта. Реализовать логирование статистики импорта.  
•	Тело заметки содержится в поле client_note.comments  

Для получения данных из Старой системы используется REST API.

•	Метод /clients возвращает список клиентов из старой системы  
•	Метод /notes возвращает список заметок для конкретного клиента из старой системы. Для вызова данного метода кроме clientGuid необходимо знать agency к которому приписан client в старой системе. Код agency можно получить только из сущности client из поле agency. В новой системе информация об agency отсутствует.  
Скрипт создания пациентов и пользователей в Новой системе предоставляется.
Количество пациентов в Новой системе 5 тысяч, количество заметок у пациента в среднем 15.

## Результат

•	Разработать реализацию импорта ноутов как Spring Boot приложение с встроенным запуском импорта по расписанию (каждые два часа, в 15 минут первого часа).  
•	Использовать JPA, желательно Spring Data. Создать минимально необходимые сущности JPA.   
•	При необходимости можно вносить изменения в структуру (добавлять поля и таблицы) в существующую БД Новой системы.    
•	Код должен компилироваться. Наличие тестов будет плюс.   
•	Имитация REST API старой системы доступна в виде приложения, которое можно запустить локально.    

## Технологии

- Java 17.
- Gradle.
- PostgreSQL.
- Spring Boot 3.3.4
- Spring-boot-starter-data-jpa
- Spring-boot-starter-jdbc
- Spring-boot-starter-security
- Spring-boot-starter-web
- Spring-boot-starter-validation
- Liquibase
- Lombok
- Junit
  
## Инструкция для запуска приложения

1. Для того чтобы приложения хорошо запускалось нужно чтобы все технологии строго были похожи на те которые описаны выше.
2. Все настройки проекта лежат в application.yml.
3. Для работы с базой данной был подключён liquibase и написаны 2 скрипта один для создания таблиц, другой для их заполнения.
4. Запустить CleverdevApplication.java
5. Проект готов к работе.
