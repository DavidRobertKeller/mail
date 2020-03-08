package drkeller.mail.mailapi.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import drkeller.mail.mailapi.model.Mail;

@Repository
public class MailRepository {

    private Map<String, Mail> mails = new HashMap<>();

    public Optional<Mail> findById(String id) {
        return Optional.ofNullable(mails.get(id));
    }

    public void add(Mail mail) {
        mails.put(mail.getId(), mail);
    }

    public Collection<Mail> getMails() {
        return mails.values();
    }
}
