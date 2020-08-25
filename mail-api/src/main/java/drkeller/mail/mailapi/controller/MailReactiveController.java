package drkeller.mail.mailapi.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import drkeller.mail.mailapi.dto.Mail;
import drkeller.mail.mailapi.model.DbMail;
import drkeller.mail.mailapi.model.MailMapper;
import drkeller.mail.mailapi.repository.MailRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/mail")
public class MailReactiveController {
	
    @Autowired
    private MailRepository mailRepository;

    @Autowired
	private MailMapper mailMapper;

	@GetMapping("/")
    public Flux<Mail> getMails() {
		Flux<DbMail> flux = mailRepository.findAll();
		return mailMapper.toDTO(flux).log();
    }

	@PostMapping("/")
	public Mono<Mail> createMail(@Valid @RequestBody Mono<Mail> mail) {
		return mailMapper.toDTO(mailRepository.save(mailMapper.toEntity(mail)));
	}
	
	@PatchMapping("/{id}")
	public Mono<Mail> updateMail(@PathVariable(value = "id") String mailId,
			@Valid @RequestBody Mail mail) {
		return mailMapper.toDTO(update(mailId, mail));
	}
	
	public Mono<DbMail> update(String id, Mail mail) {
	    return mailRepository.findById(id)
	        .map(existingMail -> {
	        	existingMail.setSubject(mail.getSubject());
		    	if (mail.getType() != null) {
		    		existingMail.setType(mail.getType());
		    	}
		    	if (mail.getState() != null) {
		    		existingMail.setState(mail.getState());
		    	}
		    	existingMail.setLastModificationDate(new Date());
	        	return Mono.just(existingMail);
	        })
	        .flatMap(mailRepository::save);
	  }
}
