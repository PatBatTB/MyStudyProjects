# Filmorate (backend)

Бэкенд для сервиса по работе с фильмами, оценками, и выдаче ТОПа фильмов, рекомендованных к просмотру.

# Описание

Проект переведен в отдельный [репозиторий](https://github.com/PatBatTB/filmorate)

- [x] Добавить подключение к БД H2 в проект.
    - [x] Сформировать структуру БД, собрать запросы в файл schema.sql в папке с ресурсами.
    - [x] Обновить модель сущностей в программе, что бы соответствовали БД.
        - [x] Добавить DAO реализацию интерфейсов *storage для взаимодействия с БД.
        - [x] Добавить недостающие DAO-объекты, соответствующие схеме.