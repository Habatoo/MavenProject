mvn -N io.takari:maven:wrapper -Dmaven="3.9.6"

./mvnw clean install

https://github.com/johan974/maven-multi-module-unittest-integrationtest-jacoco

https://habr.com/ru/articles/264505/
mvn versions:display-plugin-updates


./mvnw dependency:resolve-plugins
# Покажет, что плагины используют версии из BOM


./mvnw enforcer:enforce