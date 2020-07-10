package drkeller.mail.mailapi.model;

import drkeller.mail.mailapi.dto.Mail;

public class MailTransformer {
	
	public static Mail buildMail(DbMail dbmail) {
		Mail mail = new Mail();
		mail.setId(dbmail.getId().toString());
		mail.setSubject(dbmail.getSubject());
		mail.setCreator(dbmail.getCreator());
		mail.convertCreationDate(dbmail.getCreationDate());
		mail.convertLastModificationDate(dbmail.getLastModificationDate());
		mail.setType(dbmail.getType());
		mail.setState(dbmail.getState());

		return mail;
	}
}
