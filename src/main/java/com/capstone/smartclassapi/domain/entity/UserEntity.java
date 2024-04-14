package com.capstone.smartclassapi.domain.entity;

import com.capstone.smartclassapi.domain.entity.enums.Provider;
import com.capstone.smartclassapi.domain.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "public")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "user_code")
    private Long userCode;

    @Column(name = "image_key")
    private Long imageKey;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<ClassEntity> classes;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<ExamEntity> exams;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<SubjectEntity> subjects;

    @PrePersist
    public void init() {
        this.createdAt = LocalDateTime.now();
        this.role = Role.TEACHER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
