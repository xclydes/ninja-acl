package com.ludgerpeters.acl;

import com.google.inject.Inject;
import com.ludgerpeters.acl.exceptions.AuthenticationException;
import com.ludgerpeters.acl.exceptions.UserNotFoundException;
import com.ludgerpeters.acl.utility.PasswordHashing;
import ninja.session.Session;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by Ludger on 2015-04-13.
 */
public class UserAclManagerImp implements UserAclManager {

    protected Logger logger = LoggerFactory.getLogger(UserAclManagerImp.class);

    @Inject
    protected PasswordHashing passwordHashing;

    @Inject
    protected UserAuthenticationRepository userRepository;

    @Inject
    protected Session session;

    @Override
    public boolean checkUserPermissions(String userId, String[] groups, String[] permissions) {
        logger.info("Checking user permissions for "+userId+" with Groups "+Arrays.toString(groups)+" and with permissions"+Arrays.toString(permissions));
        return checkUserPermissions(userId,permissions) || checkUserGroups(userId,groups);
    }

    public boolean checkUserPermissions(String userId,String ... permissions)
    {
        return false;
    }

    public boolean checkUserGroups(String userId,String ... groups)
    {
        return false;
    }

    @Override
    public boolean checkUserPermissions(String userId, String methodReference) {
        logger.info("Checking user permissions for "+userId+" with Method Reference "+methodReference);
        return false;
    }

    @Override
    public boolean loginUser(String username, String password) throws AuthenticationException {
        boolean success = checkUserPassword(username,password);
        if(success)
        {
            String userId = userRepository.getUserId(username);
            session.put(SESSION_LOGIN_ID,userId);
        }
        return success;
    }

    @Override
    public boolean checkUserPassword(String username, String password) throws AuthenticationException {
        String passwordHash = userRepository.getPasswordHash(username);
        if(passwordHash == null)
        {
            throw new UserNotFoundException();
        }
        if(StringUtils.isNoneEmpty(password))
        {
            return passwordHashing.checkPassword(password,passwordHash);
        }
        return false;
    }

    @Override
    public void logoutUser() {
        session.clear();
    }

    @Override
    public String getPasswordHash(String password) {
        return passwordHashing.hashPassword(password);
    }

}
