/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.singleton;

import ejb.stateless.UserEntityControllerLocal;
import entity.UserEntity;
import exception.UserNotFoundException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;

/**
 *
 * @author Alastair
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private UserEntityControllerLocal userEntityControllerLocal;
    
    public DataInitSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            userEntityControllerLocal.retrieveUserByUsername("manager");
        } catch (UserNotFoundException ex) {
            initializeData();
        }
    }

    private void initializeData() {
        userEntityControllerLocal.createNewUser(new UserEntity("manager", "password"));
    }
}
