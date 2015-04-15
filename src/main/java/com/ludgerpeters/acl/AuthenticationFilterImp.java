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
import ninja.Context;
import ninja.FilterChain;
import ninja.Result;
import ninja.session.Session;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ludger on 2015-04-13.
 */
public class AuthenticationFilterImp implements AuthenticationFilter {
    protected Logger logger = LoggerFactory.getLogger(AuthenticationFilterImp.class);

    @Inject
    protected UserAclManager userManager;

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        logger.info("Filter hit with url: "+context.getRequestPath());
        Session session = context.getSession();
        AuthenticationAcl aclAnnotation = context.getRoute().getControllerMethod().getAnnotation(AuthenticationAcl.class);
        String userId = session.get("user.id");
        if(StringUtils.isEmpty(userId))
        {
            logger.info("User ID is empty");
        }
        else
        {
            logger.info("User ID is not empty it is : " + userId);
            if(aclAnnotation!= null)
            {
                if(validatePermissions(userId,aclAnnotation,context))
                {
                    logger.info("User Passes the login Test");
                }
                else
                {
                    logger.info("User Does not have sufficient ");
                }
            }
        }
        return filterChain.next(context);
    }

    private boolean validatePermissions(String userId, AuthenticationAcl aclAnnotation, Context context) {
        if(aclAnnotation.fromManager())
        {
            String methodReference = context.getRoute().getControllerClass().toString() + "@" + context.getRoute().getControllerMethod().getName();
            return userManager.checkUserPermissions(userId,methodReference);
        }
        else
        {
            return userManager.checkUserPermissions(userId,aclAnnotation.groups(),aclAnnotation.permissions());
        }
    }
}
