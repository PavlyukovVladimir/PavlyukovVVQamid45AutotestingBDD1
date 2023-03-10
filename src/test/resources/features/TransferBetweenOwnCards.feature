#language:ru

Функциональность: Переводы между собственными картами

  Структура сценария: перевод с первой на вторую карту (позитивный)
    Пусть пользователь залогинен и находится в личном кабинете
    Когда пользователь переводит <amount> рублей с первой на вторую карту
    Тогда баланс его первой карты должен уменьшиться на <amount> рублей
    И должен увеличиться баланс его второй карты на <amount> рублей
    Примеры:
      | amount |
      | "100"  |
      | "0"    |
      | "0,01" |
      | "1,01" |

  Структура сценария: перевод со второй на первую карту (позитивный)
    Пусть пользователь залогинен и находится в личном кабинете
    Когда пользователь переводит <amount> рублей со второй на первую карту
    Тогда баланс его первой карты должен увеличиться на <amount> рублей
    И должен уменьшиться баланс его второй карты на <amount> рублей
    Примеры:
      | amount |
      | "100"  |
      | "0"    |
      | "0,01" |
      | "1,01" |

  Структура сценария: перевод с первой карты на саму себя (позитивный)
    Пусть пользователь залогинен и находится в личном кабинете
    Когда пользователь переводит <amount> рублей с первой на первую карту
    Тогда баланс его первой карты не изменится
    И баланс его второй карты не должен измениться
    Примеры:
      | amount |
      | "100"  |
      | "0"    |
      | "0,01" |
      | "1,01" |

  Структура сценария: перевод со второй карты на саму себя (позитивный)
    Пусть пользователь залогинен и находится в личном кабинете
    Когда пользователь переводит <amount> рублей с первой на первую карту
    Тогда баланс его второй карты не изменится
    И баланс его первой карты не должен измениться
    Примеры:
      | amount |
      | "100"  |
      | "0"    |
      | "0,01" |
      | "1,01" |