package com.ludgerpeters.acl;

import com.google.inject.AbstractModule;
import com.ludgerpeters.acl.utility.PasswordHashing;
import com.ludgerpeters.acl.utility.PasswordHashingImp;

/**
 * Nice Name
 * <p>
 * Description
 * <p>
 * Created by Ludger Peters
 * Created on 2015/04/15
 */
public class AclModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AuthenticationFilter.class).to(AuthenticationFilterImp.class);
        bind(UserAclManager.class).to(UserAclManagerImp.class);
        bind(PasswordHashing.class).to(PasswordHashingImp.class);
    }
}
