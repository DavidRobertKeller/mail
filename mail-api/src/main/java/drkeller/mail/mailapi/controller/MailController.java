package drkeller.mail.mailapi.controller;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import drkeller.mail.mailapi.exception.MailNotFoundException;
import drkeller.mail.mailapi.model.Mail;
import drkeller.mail.mailapi.repository.MailRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/mail")
public class MailController {
 
    @Autowired
    private MailRepository repository;
 
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mail findById(@PathVariable String id) {
        return repository.findById(id)
            .orElseThrow(() -> new MailNotFoundException());
    }
 
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Mail> findMails() {
        return repository.getMails();
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
    	repository.add(mail);
        return mail;
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mail headMail() {
        return new Mail();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String deleteMail(@PathVariable final String id) {
    	Mail mail 
    		= repository.findById(id)
    			.orElseThrow(() -> new MailNotFoundException());
    	
    	repository.getMails().remove(mail);
    	
    	return id;
    }
}