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
