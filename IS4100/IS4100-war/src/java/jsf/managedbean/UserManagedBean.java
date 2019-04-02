/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.UserEntityControllerLocal;
import entity.ProjectEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Alastair
 */
@Named(value = "userManagedBean")
@RequestScoped
public class UserManagedBean {

    @EJB
    private UserEntityControllerLocal userEntityControllerLocal;
    
    private List<ProjectEntity> projectEntities;
    
    public UserManagedBean() {
        
    }
    
  
    
    
    
}
