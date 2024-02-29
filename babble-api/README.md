# Babble Chat Application

## Babble API
This is spring boot application and provide HTTP endpoints to manage various resources of the chat application such as User, Message, ChatRoom etc.

As of 29-Feb-2024 release, APIs to manage `User` data model with signUp, login, getLoggedInUser, deleteUser and getAllUsers operations has been implemented.

### Dependencies:
* Spring Boot 3.2.3
  * Spring Web
  * Spring Data MongoDB
  * Spring Security
  * Spring DevTools
* Hibernate Validator
* Jjwt 0.11.2

### Build
Execute maven command `mvn clean install` in terminal.

### Run
Execute maven command `mvn spring-boot:run` in terminal.
