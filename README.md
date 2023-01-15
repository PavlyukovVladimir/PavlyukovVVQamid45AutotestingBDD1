[![Build status](https://ci.appveyor.com/api/projects/status/8vkai7it35li5m85?svg=true)](https://ci.appveyor.com/project/PavlyukovVladimir/pavlyukovvvqamid45autotestingbdd1)

***Павлюков Владимир Владимирович, группа*** **QAMID45**

# Домашнее задание к занятию «2.4. BDD»

<details><summary>Вводная часть.</summary>

В качестве результата пришлите ссылки на ваши GitHub-проекты в личном кабинете студента на
сайте [netology.ru](https://netology.ru).

Все задачи этого занятия нужно делать **в разных репозиториях**.

**Важно**: если у вас что-то не получилось, то оформляйте
issue [по установленным правилам](https://github.com/netology-code/aqa-homeworks/blob/master/report-requirements.md).

**Важно**: не делайте ДЗ всех занятий в одном репозитории. Иначе вам потом придётся достаточно сложно подключать системы
Continuous integration.

## Как сдавать задачи

1. Инициализируйте на своём компьютере пустой Git-репозиторий.
1. Добавьте в него готовый файл [.gitignore](https://github.com/netology-code/aqa-homeworks/blob/master/.gitignore).
1. Добавьте в этот же каталог код ваших автотестов.
1. Сделайте необходимые коммиты.
1. Добавьте в каталог `artifacts` целевой сервис (`app-ibank-build-for-testers.jar`).
1. Создайте публичный репозиторий на GitHub и свяжите свой локальный репозиторий с удалённым.
1. Сделайте пуш — удостоверьтесь, что ваш код появился на GitHub.
1. Удостоверьтесь, что в AppVeyor сборка выполняется: запускается тестируемый сервис и тесты. При отсутствии багов в
   сервисе сборка должна быть зелёной.
1. Поставьте бейджик сборки вашего проекта в файл README.md.
1. Ссылку на ваш проект отправьте в личном кабинете на сайте [netology.ru](https://netology.ru).
1. Задачи, отмеченные как необязательные, можно не сдавать, это не повлияет на получение зачёта.
1. Если вы обнаружили подозрительное поведение SUT, похожее на баг, создайте описание в issue на
   GitHub. [Придерживайтесь схемы при описании](https://github.com/netology-code/aqa-homeworks/blob/master/report-requirements.md).
1. Если в проекте реализован тест или тесты, направленные на поиск описанных в issues багов тестируемого сервиса, то
   такие тесты будут падать до исправления багов сервиса, сборка в AppVeyor будет красной.

## Настройка CI

Настройка CI осуществляется аналогично предыдущему заданию. Поскольку у вас и так специальная тестовая сборка, то ничего
в самом сервисе делать не нужно.

</details>

## Задача №1: Page Object's

<details><summary>Развернуть Задача №1: Page Object's</summary>

Вам необходимо добить тестирование функции перевода с карты на карту. Разработчики пока реализовали возможность перевода
только между своими картами, но уже хотят, чтобы вы всё протестировали.

Для этого они не поленились и захардкодили вам целого одного пользователя:

```
* login: 'vasya'
* password: 'qwerty123'
* verification code (hardcoded): '12345'
* cards:
    * first:
        * number: '5559 0000 0000 0001'
        * balance: 10 000 RUB
    * second:
        * number: '5559 0000 0000 0002'
        * balance: 10 000 RUB
```

После логина, который уже мы сделали на лекции, вы получите список карт:

![cards.png](artifacts%2Fimg%2Fcards.png)![](![cards.png](cards.png)pic/cards.png)

Нажав на кнопку «Пополнить», вы перейдёте на страницу перевода средств:

![transfer.png](artifacts%2Fimg%2Ftransfer.png)![](![transfer.png](transfer.png)pic/transfer.png)

При успешном переводе вы вернётесь назад на страницу со списком карт.

Это ключевой кейс, который нужно протестировать.

Нужно, чтобы вы через Page Object's добавили доменные методы:

* перевода с определённой карты на другую карту энной суммы,
* проверки баланса по карте со страницы списка карт.

**Вы можете познакомиться с некоторыми
подсказками [по реализации этой задачи](https://github.com/netology-code/aqa-homeworks/blob/master/bdd/balance.md)**.

P.S. Чтобы вам было не скучно, мы добавили порядком багов, поэтому как минимум один issue в GitHub у вас должен быть 😈

<details>
    <summary>Подсказка</summary>

    Обратите внимание на то, что ваши тесты должны проходить целиком, то есть весь набор тестов. Мы, как всегда, заложили там небольшую ловушку, чтобы вам не было скучно 😈
    
    Не закладывайтесь на то, что на картах для каждого теста всегда одна и та же фиксированная сумма, подумайте, как работать с SUT так, чтобы не приходилось её перезапускать для каждого теста.

</details>

</details>

## Задача №2: BDD (необязательная)

<details><summary>Развернуть Задача №2: BDD (необязательная)</summary>

Используя Page Object's из предыдущей задачи, на базе шаблона Cucumber с лекции реализуйте кастомные steps:
* когда пользователь переводит 5 000 рублей с карты с номером 5559 0000 0000 0002 на свою 1 карту с главной страницы,
* тогда баланс его 1 карты из списка на главной странице должен стать 15 000 рублей.

Тогда вместе с логином, который мы сделали на лекции, всё должно выглядеть вот так:

* пусть пользователь залогинен с именем «vasya» и паролем «qwerty123»,
* когда пользователь переводит 5 000 рублей с карты с номером 5559 0000 0000 0002 на свою 1 карту с главной страницы,
* тогда баланс его 1 карты из списка на главной странице должен стать 15 000 рублей.

</details>

# Запуск тестов

* Runs server: `java -jar artifacts/app-ibank-build-for-testers.jar & echo $! > ./testserver.pid`
* Stop server: `kill -TERM $(cat ./testserver.pid)`

_на удаленной машине эта команда будет чуть другая `java -jar ./artifacts/app-ibank-build-for-testers.jar &`_

* Runs all tests: `./gradlew clean test --info`
* Delete previous data about tests: `./gradlew clean`
* Runs junit tests: `./gradlew test --tests ru.netology.TransferBetweenOwnCardsTest --info`
* Runs cucumber tests: `./gradlew test --tests ru.netology.RunCucumberTest --info`

_на удаленной машине эта команда тоже будет чуть другая `./gradlew test -Dselenide.headless=true --info`_

* [Просмотр отчета(локальное выполнение тестов)](build/reports/tests/test/index.html)
* [Просмотр отчета(appveyor выполнение тестов при push. Можно скачать архив отчета reports.zip, расположен на вкладке Artifacts)](https://ci.appveyor.com/project/PavlyukovVladimir/pavlyukovvvqamid45autotestingselenide/history)

# Багрепорты

* [Возможен перевод большей суммы чем есть на карте](https://github.com/PavlyukovVladimir/PavlyukovVVQamid45AutotestingBDD1/issues/1)
* [При переводе суммы с копейками, сумма перевода увеличивается в 100 раз](https://github.com/PavlyukovVladimir/PavlyukovVVQamid45AutotestingBDD1/issues/2)
