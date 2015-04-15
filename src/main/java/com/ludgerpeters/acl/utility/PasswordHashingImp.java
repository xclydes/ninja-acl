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

package com.ludgerpeters.acl.utility;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Wrapper class to wrap the password hashing function
 * Created by Ludger on 2015-04-13.
 */
@Singleton
public class PasswordHashingImp implements PasswordHashing {

    Logger logger = LoggerFactory.getLogger(PasswordHashingImp.class);

    @Override
    public boolean checkPassword(String password, String correctHash) {
        try {
            return PasswordHash.validatePassword(password,correctHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Could not run hashing algorithm",e);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Invalid Key specification",e);
            return false;
        }
    }

    @Override
    public String hashPassword(String password) {
        try {
            return PasswordHash.createHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException("Could not run hashing algorithm",e);
        }
    }
}
