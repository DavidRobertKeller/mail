package drkeller.mail.mailapi.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import drkeller.mail.mailapi.dto.Mail;
import drkeller.mail.mailapi.model.DbMail;
import drkeller.mail.mailapi.repository.MailTemplateOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/mail")
public class MailController {
 
    @Autowired
    private MailTemplateOperations mailTemplateOperation;
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mail findById(@PathVariable String id) {
        DbMail dbmail = mailTemplateOperation.findById(id).block();
    	Mail mail = new Mail();
		mail.setSubject(dbmail.getSubject());
	    return mail;
    }
 
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Mail> findMails() {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
    	Jwt jwt = (Jwt) oauthToken.getPrincipal();
    	System.out.println("username: " + jwt.getClaim(StandardClaimNames.PREFERRED_USERNAME));
    	
    	Collection<GrantedAuthority> grantedAuthorities = oauthToken.getAuthorities();
    	for (GrantedAuthority grantedAuthority : grantedAuthorities) {
        	System.out.println("grantedAuthority: " + grantedAuthority.getAuthority());
		}
    	
		Flux<DbMail> flux = mailTemplateOperation.findAll();

		List<DbMail> dbMails = flux.collectList().block();
		List<Mail> mails = new ArrayList<Mail>();
		System.out.println("DbMail count: " + dbMails.size());
		for (DbMail dbmail : dbMails) {
			System.out.println("mail:" + dbmail.getId() + "-" + dbmail.getSubject());
			Mail mail = new Mail();
			mail.setId(dbmail.getId());
			mail.setSubject(dbmail.getSubject());
			mail.setCreator(dbmail.getCreator());
			mail.convertCreationDate(dbmail.getCreationDate());
			mail.convertLastModificationDate(dbmail.getLastModificationDate());
			mail.setType(dbmail.getType());
			mails.add(mail);
		}

		return mails;
    }
 
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mail updateMail(@PathVariable("id") final String id, @RequestBody final Mail mail) {
        return mail;
    }
    
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mail patchMail(@PathVariable("id") final String id, @RequestBody final Mail mail) {
        return mail;
    }
    
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mail postMail(@NotNull @Valid @RequestBody final Mail mail) {
    	DbMail dbmail = new DbMail(mail.getSubject(), mail.getCreator(), mail.getType());
    	dbmail.init();
    	mailTemplateOperation.save(Mono.just(dbmail)).subscribe();
        return mail;
    }


    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String deleteMail(@PathVariable final String id) {
    	Mono<DbMail> m = mailTemplateOperation.findById(id);
    	mailTemplateOperation.remove(m);
    	return id;
    }
}