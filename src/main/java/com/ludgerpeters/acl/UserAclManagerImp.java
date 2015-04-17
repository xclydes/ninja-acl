/*
 * Copyright 2015 Ludger Peters
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import java.util.Set;
import java.util.stream.IntStream;

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

    public boolean checkUserPermissions(String userId,String permissions[])
    {
        Set<String> userPermissions = userRepository.getPermissions(userId);
        return Arrays.stream(permissions).anyMatch(userPermissions::contains);
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
