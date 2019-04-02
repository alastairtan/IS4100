/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Alastair
 */
public class UserNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewCommunityGoalException</code>
     * without detail message.
     */
    public UserNotFoundException() {
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}