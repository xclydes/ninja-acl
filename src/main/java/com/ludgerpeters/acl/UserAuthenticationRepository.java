package com.ludgerpeters.acl;

/**
 * Created by Ludger on 2015-04-13.
 */
public interface UserAuthenticationRepository {

    String getPasswordHash(String username);

    String getUserId(String username);
}
