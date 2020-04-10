package drkeller.mail.mailapi.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Mail {

	private String id;

	@NotNull
	private String subject;

	private MailActor issuer;

	@NotNull
	private MailType type;

	@JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private LocalDateTime creationDatetime;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public MailActor getIssuer() {
		return issuer;
	}
	
	public void setIssuer(MailActor issuer) {
		this.issuer = issuer;
	}
	
	public MailType getType() {
		return type;
	}
	public void setType(MailType type) {
		this.type = type;
	}
	
	public LocalDateTime getCreationDatetime() {
		return creationDatetime;
	}
	
	public void setCreationDatetime(LocalDateTime creationDatetime) {
		this.creationDatetime = creationDatetime;
	}
	// TODO date creation, date modification


}