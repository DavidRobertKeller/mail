package drkeller.mail.mailapi.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class DbMail {
	@Id
	private String id;
	private String subject;

	public DbMail(String subject) {
		this.subject = subject;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getId() {
		return id;
	}
}
