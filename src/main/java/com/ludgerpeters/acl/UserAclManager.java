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
