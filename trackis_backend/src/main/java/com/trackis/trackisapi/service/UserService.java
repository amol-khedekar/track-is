package com.trackis.trackisapi.service;

import com.trackis.trackisapi.dto.user.UserDtoMapper;
import com.trackis.trackisapi.dto.user.UserRequestDto;
import com.trackis.trackisapi.entity.Issue;
import com.trackis.trackisapi.entity.Project;
import com.trackis.trackisapi.entity.User;
import com.trackis.trackisapi.entity.role.Role;
import com.trackis.trackisapi.entity.role.RoleType;
import com.trackis.trackisapi.exception.ResourceNotFoundException;
import com.trackis.trackisapi.repository.RoleRepository;
import com.trackis.trackisapi.repository.UserRepository;
import com.trackis.trackisapi.security.expressions.permissions.role.IsUser;
import com.trackis.trackisapi.security.expressions.permissions.user.IsAnonymousUser;
import com.trackis.trackisapi.security.expressions.permissions.user.UserAccountPermission;
import com.trackis.trackisapi.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoMapper userDtoMapper;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username=" + username + " not found"));
        return new UserPrincipal(user);
    }

    @IsUser
    @Transactional(readOnly = true)
    public Page<User> findAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        if (users.isEmpty()) throw new ResourceNotFoundException("No users found");
        return users;
    }

    @IsUser
    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username=" + username + " not found"));
    }

    @IsUser
    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id=" + userId + " not found"));
    }

    @IsAnonymousUser
    public User createUser(UserRequestDto userRequestDto) {
        User user = userDtoMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findRoleByName(RoleType.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role with name=" + RoleType.USER + " not found")));
        user.setRoles(roles);
        user.setAccountEnabled(true);
        user.setAccountLocked(false);
        user.setAccountExpired(false);
        user.setCredentialsExpired(false);
        /*
         Overrides an automatic field update by the JPA auditor,
         so no pre-authentication is required
         */
        user.setCreatedDate(Instant.now());
        return userRepository.saveAndFlush(user);
    }

    @UserAccountPermission
    public void updateUser(User user, UserRequestDto userRequestDto) {
        userRepository.save(userDtoMapper.toEntity(userRequestDto));
    }

    @UserAccountPermission
    public void deleteUser(User user) {
        for (Project project : user.getOwnedProjectsView()) {
            project.removeCollaborator(user);
        }

        for (Issue issue : user.getIssuesView()) {
            issue.setAuthor(null);
        }

        userRepository.deleteById(user.getId());
    }
}
