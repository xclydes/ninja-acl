package com.ludgerpeters.acl;

import com.ludgerpeters.acl.exceptions.AuthenticationException;

/**
 * Created by Ludger on 2015-04-13.
 */
public interface UserAclManager {
    String SESSION_LOGIN_ID = "userID";
    boolean checkUserPermissions(String userId, String[] groups, String[] permissions);

    boolean checkUserPermissions(String userId, String methodReference);

    /**
     * Function to try and login the user
     *
     * @param username unique username to look up the user from the repository
     * @param password the unhashed password
     * @return true if password is correct, false if it is incorrect
     * @throws UserNotFoundException exception when no user is found
     */
    boolean loginUser(String username, String password) throws AuthenticationException;

    boolean checkUserPassword(String username, String password) throws AuthenticationException;

    void logoutUser();

    String getPasswordHash(String password);
}
