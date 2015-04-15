package com.ludgerpeters.acl.utility;

/**
 * Created by Ludger on 2015-04-13.
 */
public interface PasswordHashing  {

    boolean checkPassword(String password, String correctHash);

    String hashPassword(String password);
}