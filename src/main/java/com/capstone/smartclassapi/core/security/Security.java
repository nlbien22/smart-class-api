package com.capstone.smartclassapi.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface Security {

    @interface Classes {

        @Retention(RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@systemSecurityProvider.canManageClass(#classId)")
        @interface allowAllActionInClass {}
    }
}
