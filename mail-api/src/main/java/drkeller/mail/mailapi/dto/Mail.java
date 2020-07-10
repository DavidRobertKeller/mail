package drkeller.mail.mailapi.dto;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

public class Mail {
	private String id;

	@NotNull
	private MailType type;

	@NotNull
	private MailState state;

	@NotNull
	private String subject;

	private String creator;

	private MailActor issuer;

	private List<MailActor> actors;

	private ZonedDateTime creationDate;

	private ZonedDateTime lastModificationDate;

	public static Mail build(String subject, MailType type) {
		Mail mail = new Mail();
		mail.subject = subject;
		mail.type = type;
		return mail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MailType getType() {
		return type;
	}

	public void setType(MailType type) {
		this.type = type;
	}
	
	public MailState getState() {
		return state;
	}
	
	public void setState(MailState state) {
		this.state = state;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public MailActor getIssuer() {
		return issuer;
	}

	public void setIssuer(MailActor issuer) {
		this.issuer = issuer;
	}


	public ZonedDateTime getCreationDate() {
		return creationDate;
	}
	
	public void convertCreationDate(Date creationDate) {
		this.creationDate = ZonedDateTime.ofInstant(creationDate.toInstant(), ZoneOffset.UTC);
	}

	public void setCreationDate(ZonedDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public ZonedDateTime getLastModificationDate() {
		return lastModificationDate;
	}

	public void convertLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = ZonedDateTime.ofInstant(lastModificationDate.toInstant(), ZoneOffset.UTC);
	}

	public void setLastModificationDate(ZonedDateTime lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

}