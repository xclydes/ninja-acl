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
