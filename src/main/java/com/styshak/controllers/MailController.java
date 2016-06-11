package com.styshak.controllers;

import com.styshak.utils.MailUtils;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;

@RequestScoped
@ManagedBean
public class MailController {

    private final MailUtils mailUtils = MailUtils.getInstance();
    private final String MAIL_SUPPORT_PARAM = "mail.SUPPORT";
    private String message;
    private String subject;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void sendMessage() {
        try {

            if (mailUtils.isNullOrEmpty(message) || mailUtils.isNullOrEmpty(subject)) {
                mailUtils.failValidation("required_fields");
                return;
            }

            String mailRecipient = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(MAIL_SUPPORT_PARAM);

            Message msg = new MimeMessage(getSession());

            InternetAddress address = new InternetAddress(mailRecipient);

            Multipart multipart = new MimeMultipart();

            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(getMessage());

            multipart.addBodyPart(bodyPart);

            msg.setSubject(mailUtils.getBundleMessage("message_from") + mailUtils.getUserName() + ": \"" + getSubject() + "\"");
            msg.setRecipient(RecipientType.TO, address);
            msg.setContent(multipart);

            Transport.send(msg);

            hideMailForm();

            mailUtils.showMessage("mail_sended");

        } catch (Exception ex) {

        }
    }

    private Session getSession() throws NamingException {
        InitialContext ic = new InitialContext();
        return (javax.mail.Session) ic.lookup("mail/GMAIL");
    }

    public void showMailForm() {
        RequestContext.getCurrentInstance().execute("PF('dlgSendMail').show()");
    }

    public void hideMailForm() {
        RequestContext.getCurrentInstance().execute("PF('dlgSendMail').hide()");
    }
}
