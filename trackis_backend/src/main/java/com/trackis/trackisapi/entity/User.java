package com.trackis.trackisapi.entity;

import com.trackis.trackisapi.entity.role.Role;
import com.trackis.trackisapi.entity.audit.AuditMetadata;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = 32183128387521L;

    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean accountLocked;
    private Boolean accountEnabled;
    private Boolean accountExpired;
    private Boolean credentialsExpired;
    private Set<Role> roles = new HashSet<>();
    private Set<Project> ownedProjects = new HashSet<>();
    private Set<Project> sharedProjects = new HashSet<>();
    private Set<Issue> issues = new HashSet<>();

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    @Column(name = "account_locked")
    public Boolean getAccountLocked() {
        return accountLocked;
    }

    @Column(name = "account_enabled")
    public Boolean getAccountEnabled() {
        return accountEnabled;
    }

    @Column(name = "account_expired")
    public Boolean getAccountExpired() {
        return accountExpired;
    }

    @Column(name = "credentials_expired")
    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> getRoles() {
        return roles;
    }

    @Transient
    public Set<Role> getRolesView() {
        return Collections.unmodifiableSet(roles);
    }

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Project> getOwnedProjects() {
        return ownedProjects;
    }

    @Transient
    public Set<Project> getOwnedProjectsView() {
        return Collections.unmodifiableSet(ownedProjects);
    }

    @ManyToMany(mappedBy = "collaborators", fetch = FetchType.LAZY)
    private Set<Project> getSharedProjects() {
        return sharedProjects;
    }

    @Transient
    public Set<Project> getSharedProjectsView() {
        return Collections.unmodifiableSet(sharedProjects);
    }

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Issue> getIssues() {
        return issues;
    }

    @Transient
    public Set<Issue> getIssuesView() {
        return Collections.unmodifiableSet(issues);
    }

}
