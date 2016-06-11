package com.styshak.controllers;

import com.styshak.dao.DaoFactory;
import com.styshak.entity.User;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

    private String login;
    private String password;
    private Boolean isUserIsAdmin;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsUserIsAdmin() {
        return isUserIsAdmin;
    }

    public void setIsUserIsAdmin(Boolean isUserIsAdmin) {
        this.isUserIsAdmin = isUserIsAdmin;
    }
    
    public String login() {

        User user = DaoFactory.getInstance().getUserDao().getUserFromDB(login, password);
        if (user != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("user", user);
            return "books";
        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("com.styshak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(bundle.getString("incorrect_login_or_password"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("formLogin", message);
            return "index";
        }
    }

    public String logout() {
        String result = "/index.xhtml?faces-redirect=true";
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return result;
    }
    
    public boolean isUserInRole(String role, User user) {
        if(isUserIsAdmin == null) {
            isUserIsAdmin = DaoFactory.getInstance().getUserDao().isUserInRole(role, user);
        }
        return isUserIsAdmin;
    }
}
