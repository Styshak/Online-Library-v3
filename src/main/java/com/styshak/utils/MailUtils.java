package com.styshak.utils;

import com.styshak.entity.User;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MailUtils {

    public static MailUtils mailUtils;
    private final ResourceBundle bundle = ResourceBundle.getBundle("com.styshak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    public static MailUtils getInstance() {
        if (mailUtils == null) {
            mailUtils = new MailUtils();
        }
        return mailUtils;
    }

    private MailUtils() {
    }

    public boolean isNullOrEmpty(Object obj) {
        return obj == null || obj.toString().equals("");
    }

    public void failValidation(String message_key) {
        FacesContext.getCurrentInstance().validationFailed();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString(message_key), bundle.getString("error")));
    }

    public String getBundleMessage(String message_key) {
        return bundle.getString(message_key);
    }

    public void showMessage(String message_key) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString(message_key)));

    }

    public String getUserName() {
        FacesContext context = FacesContext.getCurrentInstance();
        return ((User) context.getExternalContext().getSessionMap().get("user")).getLogin();
    }
}
