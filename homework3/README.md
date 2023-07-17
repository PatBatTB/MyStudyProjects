# Трекер задач (продолжение)

Приложение для создания и отслеживания выполнения задач.

## Рефакторинг

- Менеджер необходимо сделать интерфейсом, в интерфейсе должны быть объявлены основные методы.
- Старый менеджер необходимо оставить отдельным классом, который будет имплементировать новый интерфейс.

## Новый функционал.

- Добавить историю просмотренных задач.
    - Метод должен возвращать последние 10 просмотренных задач.
    - Метод не должен принимать параметров.
    - Повторно просмотренные задачи не перезатираются.
    - Просмотром считается вызов геттера какой-либо из задач.

- Создать утилитный класс, который будет по запросу возвращать объект менеджера (с типом интерфейса)
- Сделать интерфейс для истории задач с двумя методами:
    - добавление в список просмотров
    - возврат истории в виде списка.
- создать класс, реализующий интерфейс менеджера истории.
- добавить в утилитный класс метод, возвращающий объект хистори менеджера (с типом интерфейса).
- Менеджер должен обращаться к менеджеру истории через интерфейс и использовать реализацию,
  которую возвращает утилитный класс.

## Процесс работы.

Продолжается работа над проектом и делается рефакторинг.
Все изменения будут проводиться в папке прошлого
задания [homework2](https://github.com/PatBatTB/MyStudyProjects/tree/main/homework2)