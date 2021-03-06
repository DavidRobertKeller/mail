package drkeller.mail.mailapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.ReactiveRemoveOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;

import drkeller.mail.mailapi.model.DbMail;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MailRepository {
  
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

    public Mono<DbMail> removeById(String id) {
        return template.findAndRemove(Query.query(Criteria.where("_id").is(id)), DbMail.class);
    }

    public Mono<DeleteResult> remove(Mono<DbMail> mail) {
        return template.remove(mail);
    }

    public ReactiveRemoveOperation.ReactiveRemove<DbMail> removeAll() {
        return template.remove(DbMail.class);
    }
}
