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


Bibliography:
* SMTP: https://tools.ietf.org/html/rfc5321
* POP3: https://tools.ietf.org/html/rfc1939
* IMAP: https://tools.ietf.org/html/rfc3501
* Certified Mail: https://www.itu.int/rec/T-REC-X.1341/fr
* Registered Mail: https://www.itu.int/md/T13-SG17-150408-TD-PLEN-1686/fr
