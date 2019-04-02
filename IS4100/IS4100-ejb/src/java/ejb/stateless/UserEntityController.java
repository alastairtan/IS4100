/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.UserEntity;
import exception.UserNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Alastair
 */
@Stateless
public class UserEntityController implements UserEntityControllerLocal {

    @PersistenceContext(unitName = "IS4100-ejbPU")
    private EntityManager em;

    @Override
    public UserEntity createNewUser(UserEntity userEntity) {
        em.persist(userEntity);
        em.flush();
        
        return userEntity;
    }
    
    @Override
    public UserEntity retrieveUserByUserId(Long id) {
        UserEntity userEntity = em.find(UserEntity.class, id);
        if(userEntity == null) {
            return null;
        }
        return userEntity;
    }
    
    @Override
    public UserEntity retrieveUserByUsername(String username) throws UserNotFoundException{
        Query query = em.createQuery("SELECT s FROM UserEntity s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);
        try {
            return (UserEntity)query.getSingleResult();
        } catch(NonUniqueResultException | NoResultException ex) {
            throw new UserNotFoundException("Staff Username " + username + " does not exist!");
        }
    }
    
    @Override
    public UserEntity userLogin(String username, String password) {
        UserEntity userEntity;
        try {
            userEntity = retrieveUserByUsername(username);
            if(userEntity.getPassword().equals(password)) {
                return userEntity;
            }
        } catch (Exception ex) {
            Logger.getLogger(UserEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }
    
    
    

    
}
