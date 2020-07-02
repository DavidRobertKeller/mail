package drkeller.mail.test;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import drkeller.mail.mailapi.database.DbMail;
import reactor.core.publisher.Flux;

@EnableMongoAuditing
@EnableReactiveMongoRepositories
public class TestCase {
	
    @Autowired
    MongoClient mongoClient;
 
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
    	mongoClient = MongoClients.create("mongodb://localhost:27017");
        return new ReactiveMongoTemplate(mongoClient, "mailer");
    }

	
	@Autowired ReactiveMongoTemplate template;

	public static void main(String[] args) {
		System.out.println("TestCase starts...");
		TestCase tc = new TestCase();
		tc.setUp();
		tc.findMailBySubject("");
	}
	
	
	public void setUp() {
		template = reactiveMongoTemplate();
		template.dropCollection(DbMail.class);

		Flux<DbMail> insertAll = template
				.insertAll(Flux.just(new DbMail("Walter"), //
						new DbMail("Skyler"), //
						new DbMail("Saul"), //
						new DbMail("Jesse")).collectList());
		
		
		insertAll.subscribe();

	}

	public void findMailBySubject(String subject) {
		
		Flux<DbMail> flux = template.find(
				Query.query(Criteria.where("subject")
						.regex(Pattern.compile(subject, Pattern.CASE_INSENSITIVE))), 
				DbMail.class);
		
//		Mono<List<Mail>> mails = flux.collectList();
		List<DbMail> mails = flux.collectList().block();
		for (DbMail mail : mails) {
			System.out.println("mail:" + mail.getId() + "-" + mail.getSubject());
		}
		
//		Flux<Mail> flux = template.find(Query.query(Criteria.where("lastname").is("White")), Mail.class);
//		long count = RxReactiveStreams.toObservable(flux).count().toSingle().toBlocking().value();		
	}
	
	 
}