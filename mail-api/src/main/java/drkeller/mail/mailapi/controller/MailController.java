package drkeller.mail.mailapi.controller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.gridfs.model.GridFSFile;

import drkeller.mail.mailapi.dto.Mail;
import drkeller.mail.mailapi.dto.MailDocument;
import drkeller.mail.mailapi.model.DbMail;
import drkeller.mail.mailapi.model.MailMapper;
import drkeller.mail.mailapi.repository.MailRepository;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/mail")
public class MailController {
	
    @Autowired
    private MailRepository mailRepository;

    @Autowired
	private MailMapper mailMapper;
    
	@Autowired
	private final ReactiveGridFsTemplate gridFsTemplate = null;


	@GetMapping("/")
    public Flux<Mail> getMails() {
		Flux<DbMail> flux = mailRepository.findAll();
		return mailMapper.toDTO(flux).log();
    }

	@PostMapping("/")
	public Mono<Mail> createMail(@Valid @RequestBody Mono<Mail> mail) {
		return mailMapper.toDTO(mailRepository.save(mailMapper.toEntity(mail)));
	}

	@GetMapping("/{id}")
    public Mono<Mail> getMail(
			@PathVariable(value = "id") String mailId) {
    	Mono<Mail> mailMono = mailMapper.toDTO(mailRepository.findById(mailId)).log();
    	return mailMono;
    }

	@GetMapping("/{id}/metadata")
    public Mono<Mail> getMailMetadata(
			@PathVariable(value = "id") String mailId) {
		Flux<GridFSFile> flux = gridFsTemplate.find(query(where("metadata.mailId").is(mailId)));
		List<GridFSFile> documentsGridFs = flux.collectList().log().block();
		Mono<DbMail> mono = mailRepository.findById(mailId);
		
    	Mono<Mail> mailMono = mailMapper.toDTO(mono)
    			.log()
    			.map(m -> {
    				List<MailDocument> mailDocuments = new ArrayList<>();
    				for (GridFSFile documentGridFs  : documentsGridFs) {
    					MailDocument mailDocument = new MailDocument();
    					mailDocument.setId(documentGridFs.getObjectId().toHexString());
    					mailDocument.setName(documentGridFs.getFilename());
    					mailDocument.convertCreationDate(documentGridFs.getUploadDate());
    					mailDocument.setMetadata(documentGridFs.getMetadata());
    					mailDocument.setLength(documentGridFs.getLength());
    					mailDocuments.add(mailDocument);
    				}

    				m.setDocuments(mailDocuments);
    				return m;
    			});
		
    	return mailMono;
    }

	
	@PatchMapping("/{id}")
	public Mono<Mail> updateMail(
			@PathVariable(value = "id") String mailId,
			@Valid @RequestBody Mail mail) {
		return mailMapper.toDTO(update(mailId, mail));
	}
	
    @DeleteMapping("/{id}")
    public Mono<DbMail> delete(@PathVariable(value = "id") String id) {
    	this.gridFsTemplate.delete((query(where("metadata.mailId").is(id)))).log().subscribe();
    	return mailRepository.removeById(id);
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
