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
