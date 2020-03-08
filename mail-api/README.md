# a simple implementation of a MailStore

Stack:
* Java
* SpringBoot
* Keycloak
* Swagger OpenApi


Bibliography:
* https://www.baeldung.com/spring-rest-openapi-documentation
* https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-springdoc
* https://medium.com/keycloak/secure-spring-boot-2-using-keycloak-f755bc255b68
* https://auth0.com/docs/tokens/guides/store-tokens
* https://ordina-jworks.github.io/security/2019/08/22/Securing-Web-Applications-With-Keycloak.html

Build:
* mvn clean install

Configure Keycloak:
* start: standalone.bat -Djboss.socket.binding.port-offset=1000
* create admin accout
* create realms: mail-realm
* create client: mail-app
* create user: user1

Test:
* mvn clean spring-boot:run
* open http://localhost:8080/swagger-ui.html
