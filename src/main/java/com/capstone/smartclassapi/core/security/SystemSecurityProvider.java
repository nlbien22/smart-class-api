package com.capstone.smartclassapi.core.security;

import com.capstone.smartclassapi.domain.service.ClassServiceImp;
import com.capstone.smartclassapi.domain.exception.NotAllowAction;
import com.capstone.smartclassapi.domain.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component("systemSecurityProvider")
@RequiredArgsConstructor
public class SystemSecurityProvider {
    private final ClassServiceImp classServiceImp;
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isADM() {
        return this.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()));
    }

    public boolean isTeacher() {
        return this.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.TEACHER.name()));
    }
    
    public boolean isStudent() {
        return this.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.STUDENT.name()));
    }

    @Before("@annotation(com.capstone.smartclassapi.core.security.Security.Classes.allowAllActionInClass) && args(classId)")
    public void canManageClass(Long classId) {
        if (!isADM() && !classServiceImp.isTeacherOfClass(classId)) {
            throw new NotAllowAction("You are not allowed to perform this action");
        }
    }

}
