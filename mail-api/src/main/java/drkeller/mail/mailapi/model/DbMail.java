package drkeller.mail.mailapi.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import drkeller.mail.mailapi.dto.MailType;


@Document (collection = "mails")
public class DbMail {

	@Id
    private ObjectId _id;
	
	@NotNull
	private String subject;

	@NotNull
	private String creator;

	private Date creationDate;

	private Date lastModificationDate;

	@NotNull
	private MailType type;

	public DbMail(String subject, String creator, MailType type) {
		init();
		this.subject = subject;
		this.creator = creator;
		this.type = type;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public ObjectId getId() {
		return _id;
	}
	
	public void setId(ObjectId id) {
		this._id = id;
	}

	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public MailType getType() {
		return type;
	}
	
	public void setType(MailType type) {
		this.type = type;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
	
	public Date getLastModificationDate() {
		return lastModificationDate;
	}
	
	public void init() {
		creationDate = new Date();
		lastModificationDate = creationDate;
	}

}
