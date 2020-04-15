package drkeller.mail.mailapi.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Mail {
	DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSSSSS Z");

	private String id;

	@NotNull
	private String subject;

	private MailActor issuer;

	@NotNull
	private MailType type;

//	@JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//	private LocalDateTime creationDate;
	private ZonedDateTime creationDate;

//	@JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//	private LocalDateTime lastModificationDate;
	private ZonedDateTime lastModificationDate;

	public static Mail build(String subject, MailType type) {
		Mail mail = new Mail();
		mail.init();
		mail.subject = subject;
		mail.type = type;
		return mail;
	}

	public void init() {
		id = UUID.randomUUID().toString();
//        creationDate = LocalDateTime.now();
		creationDate = ZonedDateTime.now();
		lastModificationDate = creationDate;
	}

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

	public ZonedDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(ZonedDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public ZonedDateTime getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(ZonedDateTime lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

//	@PrePersist
//	public void prePersist() {
//		this.creationDate = ZonedDateTime.now();
//		this.lastModificationDate = ZonedDateTime.now();
//	}
//
//	@PreUpdate
//	public void preUpdate() {
//		this.lastModificationDate = ZonedDateTime.now();
//	}
}