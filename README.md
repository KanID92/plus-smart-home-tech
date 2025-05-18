### :hammer_and_wrench: Languages and Tools :
<img src="https://github.com/devicons/devicon/blob/master/icons/java/java-original-wordmark.svg" title="Java" alt="Java" width="60" height="60"/>&nbsp;
<img src="https://github.com/devicons/devicon/blob/master/icons/spring/spring-original-wordmark.svg" title="Spring" alt="Spring" width="60" height="60"/>&nbsp;
<img src="https://github.com/devicons/devicon/blob/master/icons/sqldeveloper/sqldeveloper-original.svg" title="sql" alt="sql" width="60" height="60"/>&nbsp;
<img src="https://img.icons8.com/?size=100&id=38561&format=png&color=000000.png" title="Postgresql" alt="Postgresql" width="60" height="60"/>&nbsp;
<img src="https://github.com/devicons/devicon/blob/master/icons/intellij/intellij-original.svg" title="intellij" alt="intellij" width="60" height="60"/>&nbsp;
<img src="https://github.com/devicons/devicon/blob/master/icons/maven/maven-original.svg" title="Maven" alt="Maven" width="60" height="60"/>&nbsp;
<img src="https://img.icons8.com/?size=100&id=22813&format=png&color=000000.png" title="Docker" alt="Docker" width="60" height="60"/>&nbsp;
<img src="https://img.icons8.com/?size=100&id=fOhLNqGJsUbJ&format=png&color=000000.png" title="apachekafka" alt="apachekafka" width="60" height="60"/>&nbsp;
<img src="https://github.com/devicons/devicon/blob/master/icons/grpc/grpc-original.svg" title="grpc" alt="grpc" width="60" height="60"/>&nbsp;
<img src="https://img.icons8.com/?size=100&id=IoYmHUxgvrFB&format=png&color=000000.png" title="postman" alt="postman" width="60" height="60"/>&nbsp;
# Платформа для анализа телеметрии (модуль telemetry)

Представляет собой систему для сбора и анализа данных от датчиков умного дома. На вход подаются данные датчиков, приложение — собирает эту информацию, обрабатывает и определяет, какие сценарии умного дома необходимо запустить.

Данные от хабов пользователей принимает сервис `Hub router`. Он преобразует эти данные в понятные системе сообщения и направляет в другой сервис — `Collector`.

`Collector` принимает данные каждого пользовательского хаба, которые передаёт `Hub router`.  Преобразовывает их в формат Apache Avro и сохраняет в топик Apache Kafka. Далее из этого топика данные могут считывать другие сервисы для своих нужд.

`Aggregator` cчитывает показания всех датчиков из топика Kafka и агрегирует по признаку принадлежности к хабу. Так получается снимок состояния всех датчиков, расположенных в пределах квартиры или дома. Результат агрегации записывается в топик Kafka.

`Analyzer` cчитывает агрегированное состояние датчиков в квартире или доме и проверяет, соответствует ли оно условиям какого-либо сценария для этого дома. Если состояние датчиков соответствует сценарию, то он запускается на выполнение, и `Analyzer` отправляет команды в `Hub router`. `Hub router`, в свою очередь, отправляет в нужный хаб указания выполнить конкретные действия.


# Интернет-магазин (модуль commerce)

Реализация интернет-магазина умных устройств

Функциональность сервиса `Shopping store`:

- создание нового товара;
- обновление товара в ассортименте, например возможность уточнить описание или характеристики продукта;
- получение списка товаров по типу с пагинацией;
- удаление товара из ассортимента магазина — функция для менеджеров;
- получение информации о товаре из БД.

Функциональность сервиса `Shopping cart`:

- отображение актуального состава корзины для авторизованного пользователя;
- добавление товара в корзину;
- удаление товара из корзины;
- изменение количества товаров в корзине;
- деактивация корзины для пользователя.

Функциональность сервиса `Warehouse`:

- принять товар на склад;
- добавить новый товар на склад;
- проверить доступность товаров по корзине;
- получить адрес склада.
- собор товаров для заказа
- передача товаров в доставку
- возврат товара

Функциональность сервиса `Order`:

- создание нового заказа;
- оплата заказа;
- расчёт стоимости доставки;
- расчёт итоговой стоимости заказа;
- получение списка заказов клиента;
- сборка заказа;
- отгрузка товаров;
- ошибка оплаты;
- ошибка сборки;
- ошибка доставки;
- возврат заказа.

Функциональность сервиса `payment`:

- эмулятор платежного шлюза

Функциональность сервиса `delivery`:
- эмулятор взамен интеграции с существующим сервисом доставки
- Сохранение сведения о доставке в БД и возвращение идентификатора созданного объекта
- Расчет стоимость доставки заказа.
- Принятие товара в доставку

# Инфраструктурный модуль (модуль commerce)

Инфраструктурные компоненты: реализация API gateway, конфигурационного сервиса и сервиса обнаружения с помощью Spring Cloud.
