package drkeller.mail.mailapi.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.ReactiveRemoveOperation;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MailTemplateOperations {
  
    @Autowired
    ReactiveMongoTemplate template;
 
    public Mono<DbMail> findById(String id) {
        return template.findById(id, DbMail.class);
    }
  
    public Flux<DbMail> findAll() {
        return template.findAll(DbMail.class);
    } 
    public Mono<DbMail> save(Mono<DbMail> mail) {
        return template.save(mail);
    }
    public ReactiveRemoveOperation.ReactiveRemove<DbMail> deleteAll() {
        return template.remove(DbMail.class);
    }
}
