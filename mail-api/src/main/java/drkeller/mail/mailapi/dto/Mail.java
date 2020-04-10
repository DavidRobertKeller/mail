package drkeller.mail.mailapi.dto;

import javax.validation.constraints.NotNull;

public class Mail {

	private String id;

	@NotNull
	private String subject;

	private MailActor issuer;

	@NotNull
	private MailType type;
	
	
	// TODO date creation, date modification

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
	
	public void setType(MailType type) {
		this.type = type;
	}
	
	public MailType getType() {
		return type;
	}

}