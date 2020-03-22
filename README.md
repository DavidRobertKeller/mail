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
* physical that cannot be digitalized : DVD, Plan, Token, etc
* physical that can be digitalized : letter, delivery slip, etc.



Bibliography:
* SMTP: https://tools.ietf.org/html/rfc5321
* POP3: https://tools.ietf.org/html/rfc1939
* IMAP: https://tools.ietf.org/html/rfc3501
* Certified Mail: https://www.itu.int/rec/T-REC-X.1341/fr
* Registered Mail: https://www.itu.int/md/T13-SG17-150408-TD-PLEN-1686/fr
