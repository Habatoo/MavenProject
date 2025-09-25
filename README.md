# Ставим wrapper:
mvn -N io.takari:maven:wrapper -Dmaven="3.9.6"

./mvnw clean install

https://github.com/johan974/maven-multi-module-unittest-integrationtest-jacoco

https://habr.com/ru/articles/264505/
./mvnw versions:display-plugin-updates

# Покажет, что плагины используют версии из BOM
./mvnw dependency:resolve-plugins

# Запуск enforcer:
./mvnw enforcer:enforce

# Запуск с подробным выводом:
./mvnw clean test -X | wsl grep -i "bootclasspath\|sharing"

# Активация prov профиля
./mvnw clean install -Pprod
./mvnw clean install -Denv=prod 

# Кастомная стадия clean - Просмотр что будет очищено (dry-run)
./mvnw clean:clean "-Dclean.verbose=true" "-Dclean.skip=true"

## Запуск только фазы initialize
mvn initialize

## Пропуск очистки
mvn initialize "-Dclean.skip=true"
