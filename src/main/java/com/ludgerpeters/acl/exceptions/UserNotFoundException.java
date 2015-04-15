package com.ludgerpeters.acl.exceptions;

/**
 * Created by Ludger on 2015-04-13.
 */
public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException() {
        super("The User was not found in the repository");
    }
}
