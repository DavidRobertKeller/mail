# Abstract

Objectives:
* provides a definition of Mail in OpenAPI format
* provides a simple implementation of a MailStore

Some definitions:

# Electronic mail (email)
Actor : 
* sender
* recipient
* in copy
* in copy invisible
Actions 
* create
* read
* update
* delete
* send
* forward


# Certified mail (cmail)
Actor : 
* sender
* recipient
* in copy
* in copy invisible
Actions 
* create
* read
* update
* delete
* send
* forward


# Registered mail (rmail)
Actor : 
* sender
* recipient
* in copy
* in copy invisible
Actions 
* create
* read
* update
* delete
* send
* forward
* refuse
* abandon (when time is up)


# Business mail (bmail)
Actor : 
* sender
* recipient
* in copy
* in copy invisible
Actions 
* create
* read
* update
* delete
* send
* forward


# Physical mail (pmail)
type : letter, parcel
Actor : 
* sender
* recipient
Actions 
* create
* read
* update
* delete
* send
* forward
Attachment:
* physical items that cannot be digitalized : DVD, Plan, Token, etc
* physical items that can be digitalized : letter, delivery slip, etc.

# Install and run
Keycloak:
* download https://downloads.jboss.org/keycloak/9.0.0/keycloak-9.0.0.zip
* mkdir D:\application 
* unzip keycloak-9.0.0.zip
* cd D:\application\keycloak-9.0.0\bin
* standalone.sh -Djboss.socket.binding.port-offset=1000

Resource Server:
* git clone https://github.com/DavidRobertKeller/mail.git
* mvn clean install
* mvn clean spring-boot:run
* open http://localhost:8080/swagger-ui.html

# Bibliography

Standart:
* SMTP: https://tools.ietf.org/html/rfc5321
* POP3: https://tools.ietf.org/html/rfc1939
* IMAP: https://tools.ietf.org/html/rfc3501
* Certified Mail: https://www.itu.int/rec/T-REC-X.1341/fr
* Registered Mail: https://www.itu.int/md/T13-SG17-150408-TD-PLEN-1686/fr

Spring and Keycloak:
* https://ordina-jworks.github.io/security/2019/08/22/Securing-Web-Applications-With-Keycloak.html
* https://www.baeldung.com/spring-rest-openapi-documentation
* https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-springdoc
* https://github.com/springdoc/springdoc-openapi/issues/133
* https://github.com/springdoc/springdoc-openapi/issues/275
* https://github.com/springfox/springfox/issues/1891
* https://stackoverflow.com/questions/49105290/how-to-get-userinfo-in-springboot-using-keycloak
* https://logging.apache.org/log4j/2.x/manual/configuration.html
* https://logging.apache.org/log4j/2.x/manual/markers.html

About CORS :
* https://stackoverflow.com/questions/50486314/how-to-solve-403-error-in-spring-boot-post-request
* https://stackoverflow.com/questions/23262168/error-creating-bean-with-name-securityconfig-injection-of-autowired-dependenc
* https://mkyong.com/spring-boot/spring-rest-spring-security-example/
* https://www.baeldung.com/spring-security-csrf
* https://www.baeldung.com/csrf-thymeleaf-with-spring-security
* https://docs.spring.io/spring-security/site/docs/3.2.0.CI-SNAPSHOT/reference/html/csrf.html

