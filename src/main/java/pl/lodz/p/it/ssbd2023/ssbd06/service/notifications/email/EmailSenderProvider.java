package pl.lodz.p.it.ssbd2023.ssbd06.service.notifications.email;

public interface EmailSenderProvider {

    void sendEmail(String to, String subject, String body);

}
