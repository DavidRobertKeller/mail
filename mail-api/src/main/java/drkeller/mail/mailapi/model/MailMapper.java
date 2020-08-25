package drkeller.mail.mailapi.model;

import org.springframework.stereotype.Component;

import drkeller.mail.mailapi.dto.Mail;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MailMapper {
	
	public static Mail buildMail(DbMail dbmail) {
		Mail mail = new Mail();
		mail.setId(dbmail.getId().toString());
		mail.setSubject(dbmail.getSubject());
		mail.setCreator(dbmail.getCreator());
		mail.convertCreationDate(dbmail.getCreationDate());
		mail.convertLastModificationDate(dbmail.getLastModificationDate());
		mail.setType(dbmail.getType());
		mail.setState(dbmail.getState());

		return mail;
	}
	

	public Flux<Mail> toDTO(Flux<DbMail> dbmail) {
		return dbmail.map(this::toDTO);
	}

	public Flux<DbMail> toEntity(Flux<Mail> mail) {
		return mail.map(this::toEntity);
	}

	public Mono<Mail> toDTO(Mono<DbMail> dbmail) {
		return dbmail.map(this::toDTO);
	}

	public Mono<DbMail> toEntity(Mono<Mail> mail) {
		return mail.map(this::toEntity);
	}
	
	public Mail toDTO(DbMail dbmail) {
//		Mail mail = new Mail();
//		BeanUtils.copyProperties(teTweetDTO, tweet);
		return buildMail(dbmail);
	}

	public DbMail toEntity(Mail mail) {
		DbMail dbmail = new DbMail(
				mail.getSubject(), 
				mail.getCreator(), 
				mail.getType());
//		BeanUtils.copyProperties(new Tweet(), tweetDTO);
		return dbmail;
	}

}
