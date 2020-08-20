package drkeller.mail.mailapi.controller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.http.ResponseEntity.ok;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Callable;

import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@CrossOrigin("*")
@RestController()
@RequestMapping("/api/document/multipart")
@RequiredArgsConstructor
public class MultipartDocumentController {
	
	@Autowired
	private final ReactiveGridFsTemplate gridFsTemplate = null;

	@PostMapping("noreactive")
	public Mono<ResponseEntity> uploadFile(@RequestParam("file") MultipartFile file) {
		int bufferSize = 1;
		Callable<InputStream> inputStreamSupplier = new Callable<InputStream>() {
			@Override
			public InputStream call() throws Exception {
				return file.getInputStream();
			}
		}; 
		
		Publisher<DataBuffer> p = DataBufferUtils.readInputStream(inputStreamSupplier, new DefaultDataBufferFactory(), bufferSize);
		System.out.println("p: " + p);
		System.out.println("gridFsTemplate: " + gridFsTemplate);
		Mono<ObjectId> monoId = this.gridFsTemplate.store(p, file.getName());
		System.out.println("monoId = " + monoId);
		return monoId.map((id) -> ok().body(Map.of("id", id.toHexString())));
	}

	
	@RequestMapping(value = "reactive", produces = MediaType.APPLICATION_JSON_VALUE, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.POST) 
    public Mono<ResponseEntity> upload(@RequestPart("file") Mono<FilePart> file) {
        return file
        	.log()
            .flatMap(part -> this.gridFsTemplate.store(part.content(), part.filename()))
            .map((id) -> ok().body(Map.of("id", id.toHexString())));
    }


    @GetMapping("{id}")
    public Flux<Void> read(@PathVariable String id, ServerWebExchange exchange) {
        return this.gridFsTemplate.findOne(query(where("_id").is(id)))
            .log()
            .flatMap(gridFsTemplate::getResource)
            .flatMapMany(r -> exchange.getResponse().writeWith(r.getDownloadStream()));
    }
}
