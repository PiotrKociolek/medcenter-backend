# medcenter-backend

przy pierwszym uruchomieniu dodajemy dependency w pom.xml
"
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-docker-compose</artifactId>
    <version>3.1.1</version>
</dependency>
"
zeby powstał kontener dockerowy z baza danych, potem usuwamy z pliku pom.xml bo wywali bład przy odplaniu

kontener mozna odpalac i zarzadzac nim w docker desktop 
do podpiecia sie do bd uzyc mongo compass
