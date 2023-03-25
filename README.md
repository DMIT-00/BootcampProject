# BootcampProject

- [About](#about)
- [Technology stack](#technology-stack)
- [How to build and run](#how-to-build-and-run)
- [How to use](#how-to-use)

## About
BootcampProject is a simple REST application.

## Technology stack
**Java: 11**

**Build:** Maven

**Core:** Spring Boot, Spring Data JPA, Hibernate, Log4j2, Mysql

**Additional:** Lombok, Mockito

## How to build and run
There are two ways to run application:
1) Without docker (requires installed Mysql):
```sh
mvn clean install
mvn spring-boot:run
```
Application properties can be changed with system environment variables:
- MYSQLDB_HOST - host for MySql
- MYSQLDB_PORT - port for MySql
- MYSQLDB_DATABASE - MySql database name
- MYSQLDB_USER - MySql user
- MYSQLDB_PASSWORD - MySql password

2) Using docker
```sh
docker-compose up
```

## How to use
Application has 1 endpoint for managing users. POST to add user. GET to retrieve user list. Endpoint uses pagination.
By default, page size is 10. Default user roles: 'Administrator', 'Sale User', 'Customer User', 'Secure API User'.

Endpoint address: http://localhost:8080/api/v1/users

Endpoint arguments: 
- page=x - page number
- size=y - page size

Example: http://localhost:8080/api/v1/users?page=0&size=10