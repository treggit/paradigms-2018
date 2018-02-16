Тесты к курсу «Парадигмы программирования»
====

[Условия домашних заданий](http://www.kgeorgiy.info/courses/paradigms/homeworks.html)


Домашнее задание 1. Хэширование
----
Модификации
 * *Базовая*
    * Исходный код тестов:
       [CalcMD5Test.java](java/hash/CalcMD5Test.java),
       [HashChecker.java](java/hash/HashChecker.java)
    * [Откомпилированные тесты](artifacts/hash/CalcMD5Test.jar)
 * *Для M3136, 37*
    * Напишите простой аналог утилиты [sha256sum](http://linux.die.net/man/1/sha256sum)
    * Класс должен называться `SHA256Sum`
    * Список файлов для хэширования передается в виде аргументов командной строки
    * Если список файлов пуст, то хэшируется стандартный ввод а именем файла считается `-`
    * Вывод хэшей осуществляется в формате `<хэш> *<имя файла>`
    * [Исходный код тестов](java/hash/SHA256SumTest.java)
    * [Откомпилированные тесты](artifacts/hash/SumSHA256Test.jar)
 * *Для M3138, 39*
    * Класс должен иметь имя `CalcSHA256` и подсчитывать [SHA-256](https://en.wikipedia.org/wiki/Secure_Hash_Algorithm)
    * [Исходный код тестов](java/hash/CalcSHA256Test.java)
    * [Откомпилированные тесты](artifacts/hash/CalcSHA256Test.jar)

Для того, чтобы протестировать базовую модификацию домашнего задания:

 1. Скачайте тесты ([CalcMD5Test.jar](artifacts/hash/CalcMD5Test.jar))
 * Откомпилируйте `CalcMD5.java`
 * Проверьте, что создался `CalcMD5.class`
 * В каталоге, в котором находится `CalcMD5.class` выполните команду

    ```
       java -jar <путь к CalcMD5Test.jar>
    ```

    Например, если `CalcMD5Test.jar` находится в текущем каталоге, выполните команду

    ```
        java -jar CalcMD5Test.jar
    ```
