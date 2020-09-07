package drkeller.mail.mailapi.controller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.http.ResponseEntity.ok;

import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@CrossOrigin("*")
@RestController()
@RequestMapping("/api/mail/document")
@RequiredArgsConstructor
public class MultipartDocumentController {
	
	@Autowired
	private final ReactiveGridFsTemplate gridFsTemplate = null;

	@RequestMapping(value = "{mailId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.POST) 
    public Mono<Object> upload(
    		@PathVariable String mailId, 
    		@RequestPart("file") Mono<FilePart> file, 
    		@RequestPart("metadata") String metadataString) {

		return file
        	.log()
            .flatMap(part -> {
        		Document md = null;
        		if (metadataString == null) {
        			md = new Document();
        		} else {
        			md = Document.parse(metadataString);
        		}
    			md.put("mailId", mailId);
            	return this.gridFsTemplate.store(part.content(), part.filename(), md);	
            })
            .map((id) -> ok().body(Map.of("id", id.toHexString())));
    }


    @GetMapping("{mailId}/{id}/raw")
    public Flux<Void> read(
    		@PathVariable String mailId, 
    		@PathVariable String id, 
    		ServerWebExchange exchange) {
    	HttpHeaders responseHeaders = exchange.getResponse().getHeaders();
    	
        return this.gridFsTemplate.findOne(query(where("_id").is(id)))
            .flatMap(gridFsTemplate::getResource)
            .flatMapMany(r -> {
            	responseHeaders.set("Content-Disposition", "attachment; filename=\"" + r.getFilename() + "\"");
            	return exchange.getResponse().writeWith(r.getDownloadStream());
            });
    }

    @GetMapping("{mailId}/{id}/metadata")
    public Mono<Document> getMetaData(
    		@PathVariable String mailId, 
    		@PathVariable String id) {
    	return this.gridFsTemplate.findOne(query(where("_id").is(id)))
	        .log()
            .map(r -> {
            	return r.getMetadata();
            });
    }

	@RequestMapping(value = "{mailId}/{id}/metadata", produces = MediaType.APPLICATION_JSON_VALUE, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.POST) 
//    @PostMapping("{mailId}/{id}/metadata")
    public Mono<Document> setMetaData(
    		@PathVariable String mailId, 
    		@PathVariable String id, 
    		@RequestBody Map<String,Object> metadata) {
    	return this.gridFsTemplate.findOne(query(where("_id").is(id)))
	        .log()
            .map(r -> {
            	r.getMetadata().putAll(metadata);
            	return r.getMetadata();
            });
    }

    
    @GetMapping("{mailId}")
    public Flux<Document> getFiles(@PathVariable String mailId) {
    	return this.gridFsTemplate.find(query(where("mailId").is(mailId)))
    	        .log()
                .map(r -> {
                	return r.getMetadata();
                });
    	
    }

}
