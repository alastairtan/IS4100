/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.UserEntityControllerLocal;
import entity.UserEntity;
import java.io.IOException;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author shawn
 */
@Named(value = "loginManagedBean")
@RequestScoped

public class LoginManagedBean {

    @EJB
    private UserEntityControllerLocal userEntityControllerLocal;
    
    private String username;
    private String password;
    
    public LoginManagedBean() {
        System.out.println("HELLO");
    }
    
    
    public void login(ActionEvent event) throws IOException
    {
        System.out.println("LOGIN");
        
        UserEntity currentUserEntity = userEntityControllerLocal.userLogin(username, password);
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUserEntity", currentUserEntity);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/dashboard.xhtml");

        
    }
     
    public void logout(ActionEvent event) throws IOException
    {
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
