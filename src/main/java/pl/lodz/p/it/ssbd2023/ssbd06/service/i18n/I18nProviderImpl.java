package pl.lodz.p.it.ssbd2023.ssbd06.service.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class I18nProviderImpl implements I18nProvider {

    public static final String VERIFICATION_MAIL_TOPIC = "mail.verification.topic";
    public static final String VERIFICATION_MAIL_BODY = "mail.verification.body";

    public static final String RESET_PASSWORD_MAIL_TOPIC = "mail.reset-password.topic";
    public static final String RESET_PASSWORD_MAIL_BODY = "mail.reset-password.body";

    public static final String ACCOUNT_DETAILS_ACCEPT_MAIL_TOPIC = "mail.accept-account-details.topic";
    public static final String ACCOUNT_DETAILS_ACCEPT_MAIL_BODY = "mail.accept-account-details.body";

    public String getMessage(final String key, final Locale locale) {
        return getResourceBundle(locale).getString(key);
    }

    private ResourceBundle getResourceBundle(final Locale locale) {
        return ResourceBundle.getBundle("i18n/messages", locale);
    }

}
