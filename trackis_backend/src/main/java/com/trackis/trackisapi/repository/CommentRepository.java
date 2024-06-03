package com.trackis.trackisapi.repository;

import com.trackis.trackisapi.entity.Issue;
import com.trackis.trackisapi.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByIssue(Issue issue, Pageable pageable);
}
