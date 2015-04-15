package com.ludgerpeters.acl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Ludger on 2015-04-13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface AuthenticationAcl {
    String[] permissions() default "";
    String[] groups() default "";
    boolean fromManager() default false;
}
