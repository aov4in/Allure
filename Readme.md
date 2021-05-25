**Project Sample**

[![Build status](https://ci.appveyor.com/api/projects/status/v7lppqs8bxkn8cjt?svg=true)](https://ci.appveyor.com/project/aov4in/carddeliverydatechange)

#### **Интеграция с Report Portal**

Чтобы начать использовать Report Portal с JUnit 5, необходимо создать файл местоположения службы:

1. Создайте папку /META-INF/services в ресурсах( ./src/test/resources )
2. Поместите туда файл с именем org.junit.jupiter.api.extension.Extension
3. Поместите ссылку на реализацию, по умолчанию в виде одной строки в файл: com.epam.reportportal.junit5.ReportPortalExtension
Пример: /META-INF/services/org.junit.jupiter.api.extension.Extension
   ```
   com.epam.reportportal.junit5.ReportPortalExtension
   ```

Для интеграции проекта с Report Portal необходимо выполнить следующие действия:
1. Открыть сайт https://reportportal.io/
2. В верхнем меню сайта выбрать ["Install"](https://reportportal.io/installation)
3. Далее необходимо в разделе "Configure and deploy ReportPortal" нажать ["Deploy-with-Docker" ](https://reportportal.io/docs/Deploy-with-Docker). Откроется страница Deploy with Docker ReportPortal
4. Убедитесь, что Docker Engine и Compose установлены.
5.  Загрузите последнюю версию файла создания ReportPortal Docker [отсюда](https://github.com/reportportal/reportportal/blob/master/docker-compose.yml) . Вы можете сделать это, выполнив следующую команду:
```
curl https://raw.githubusercontent.com/reportportal/reportportal/master/docker-compose.yml -o docker-compose.yml
```
6. Сделайте предварительные условия конфигурации ElasticSearch для службы анализатора.

    а) [Вариант 1](https://www.elastic.co/guide/en/elasticsearch/reference/6.1/docker.html#docker-cli-run-prod-mode)

    б) Вариант 2: 
    Предоставьте права доступа к папке данных ElasticSearch, используя следующие команды:
    ```
    mkdir -p data/elasticsearch
    ```

    ```
    chmod 777 data/elasticsearch
    ```

    ```
    chgrp 1000 data/elasticsearch
    ```
Дополнительные сведения об ElasticSearch см. В [руководстве](https://www.elastic.co/guide/en/elasticsearch/reference/6.1/docker.html#_notes_for_production_use_and_defaults) по ElasticSearch.

7. Запустите приложение с помощью следующей команды:
```
docker-compose -p reportportal up -d --force-recreate
```
Где:

***-p reportportal*** - добавляет префикс проекта reportportal ко всем контейнерам.

***up*** - создает и запускает контейнеры

***-d*** - режим демона

***--force -create*** - Повторно создает контейнеры, если они есть.

Полезные команды:

***docker-compose logs*** -  показывает журналы из всех контейнеров

***docker logs <container_name>*** показывает журналы из выбранного контейнера

***docker ps -a | grep "reportportal_" | awk '{print $1}' | xargs docker rm -f*** - Удаляет все контейнеры ReportPortal.

8. Если при запуске возникает ошибка: 
```
ERROR: for db-scripts  Container "e3c7cff2ab6e" is unhealthy.

ERROR: for api  Container "e3c7cff2ab6e" is unhealthy.
ERROR: Encountered errors while bringing up the project.
```
Необходимо в файле docker-compose.yml внести изменения:

а) Обновить версию docker-compose до актуальной.

б) измените раздел depends_on, чтобы он содержал чистые списки (без дополнительных операторов условий, как это было в версии 2.x)

```
 ...
  db-scripts:
    image: reportportal/migrations:5.3.5
    depends_on:
      - postgres
 ...     
```

```
 ...
 api:
    image: reportportal/service-api:5.3.5
    depends_on:
      - rabbitmq
      - gateway
      - postgres
 ...     
```


9. Перед запуском тестов так же необходимо произвести настройки build.gradle:

```
test {
    testLogging.showStandardStreams = true
    useJUnitPlatform()
    systemProperty 'junit.jupiter.extensions.autodetection.enabled', true
}
```

*Обратите внимание, что при использовании этого метода отчет о тестировании будет сгенерирован только в том случае, если вы запускали тесты с Gradle (например gradle clean test)*

*build.gradle* Пример полного файла:
```
[build.gradle]
apply plugin: 'java'
sourceCompatibility = 1.8
targetCompatibility = 1.8

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    compile 'com.epam.reportportal:logger-java-log4j:5.0.3'
    compile 'org.apache.logging.log4j:log4j-api:2.11.2'
    compile 'org.apache.logging.log4j:log4j-core:2.11.2'
    compile 'com.epam.reportportal:agent-java-junit5:5.0.5'
}

test {
    testLogging.showStandardStreams = true
    useJUnitPlatform()
    systemProperty 'junit.jupiter.extensions.autodetection.enabled', true
}
```

10. Далее запускаем тесты через терминал с помощью команды:
```
gradlew clean test
```
11. Развернутая нами среда будет доступна по адресу 

http://localhost:8080/  или  http://IP_ADDRESS:8080

12. Для доступа необходимо использовать следующий логин\пароль:
```
default\1q2w3e
or
superadmin\erebus
```