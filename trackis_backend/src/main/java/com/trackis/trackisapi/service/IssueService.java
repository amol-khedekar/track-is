package com.trackis.trackisapi.service;

import com.trackis.trackisapi.dto.issue.IssueDtoMapper;
import com.trackis.trackisapi.dto.issue.IssueRequestDto;
import com.trackis.trackisapi.entity.Issue;
import com.trackis.trackisapi.entity.Project;
import com.trackis.trackisapi.entity.User;
import com.trackis.trackisapi.exception.ResourceNotFoundException;
import com.trackis.trackisapi.repository.IssueRepository;
import com.trackis.trackisapi.security.acl.AclPermissionService;
import com.trackis.trackisapi.security.expressions.permissions.issue.IssueAuthorPermission;
import com.trackis.trackisapi.security.expressions.permissions.issue.IssueCreatePermission;
import com.trackis.trackisapi.security.expressions.permissions.issue.IssueReadByIdPermission;
import com.trackis.trackisapi.security.expressions.permissions.project.ProjectReadPermission;
import com.trackis.trackisapi.security.expressions.permissions.role.IsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueDtoMapper issueDtoMapper;
    private final AclPermissionService aclPermissionService;
    private final Permission[] issueAuthorPermissions = {BasePermission.ADMINISTRATION};

    @IsUser
    @Transactional(readOnly = true)
    public Page<Issue> findAllIssues(Pageable pageable) {
        Page<Issue> issues = issueRepository.findAll(pageable);
        if (issues.isEmpty()) throw new ResourceNotFoundException("No issues found");
        return issues;
    }

    @ProjectReadPermission
    @Transactional(readOnly = true)
    public Page<Issue> findAllIssuesByProject(Project project, Pageable pageable) {
        Page<Issue> issues = issueRepository.findByProject(project, pageable);
        if (issues.isEmpty())
            throw new ResourceNotFoundException("No issues for project with id=" + project.getId() + " found");
        return issues;
    }

    @IsUser
    @IssueReadByIdPermission
    @Transactional(readOnly = true)
    public Issue findIssueById(Long issueId) {
        return issueRepository.findById(issueId).
                orElseThrow(() -> new ResourceNotFoundException("Issue with id=" + issueId + " not found"));
    }

    @IssueCreatePermission
    public Issue createIssue(Project project, IssueRequestDto issueRequestDto, User user) {
        Issue issue = issueDtoMapper.toEntity(issueRequestDto);
        issue.setProject(project);
        issue.setAuthor(user);
        issueRepository.saveAndFlush(issue);
        aclPermissionService.grantPermissions(issue, issue.getId(), user.getUsername(), issueAuthorPermissions);
        return issue;
    }

    @IssueAuthorPermission
    public void updateIssue(Issue issue, IssueRequestDto issueRequestDto) {
        issueRepository.save(issueDtoMapper.toEntity(issueRequestDto));
    }

    @IssueAuthorPermission
    public void deleteIssue(Issue issue) {
        issueRepository.deleteById(issue.getId());
    }
}
