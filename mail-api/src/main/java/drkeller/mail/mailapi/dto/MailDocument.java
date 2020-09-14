package drkeller.mail.mailapi.dto;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.bson.Document;

public class MailDocument {
	
	private String id;
	private String name;
	private String reference;
	private String filename;
	private long length;
	private MailDocumentType type;
	private Document metadata;
	private ZonedDateTime creationDate;
	private ZonedDateTime lastModificationDate;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public void setType(MailDocumentType type) {
		this.type = type;
	}
	
	public MailDocumentType getType() {
		return type;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public ZonedDateTime getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(ZonedDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public void convertCreationDate(Date creationDate) {
		this.creationDate = ZonedDateTime.ofInstant(creationDate.toInstant(), ZoneOffset.UTC);
	}

	public ZonedDateTime getLastModificationDate() {
		return lastModificationDate;
	}
	
	public void setLastModificationDate(ZonedDateTime lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
	
	public Document getMetadata() {
		return metadata;
	}
	
	public void setMetadata(Document metadata) {
		this.metadata = metadata;
	}
	
	public void setLength(long length) {
		this.length = length;
	}
	
	public long getLength() {
		return length;
	}

}
