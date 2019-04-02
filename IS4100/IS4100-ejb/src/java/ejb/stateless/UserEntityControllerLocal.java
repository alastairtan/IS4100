/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.UserEntity;
import exception.UserNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author Alastair
 */
@Local
public interface UserEntityControllerLocal {

    public UserEntity userLogin(String username, String password);

    public UserEntity retrieveUserByUsername(String username)throws UserNotFoundException;

    public UserEntity createNewUser(UserEntity userEntity);

    public UserEntity retrieveUserByUserId(Long id);
    
}
