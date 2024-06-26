package com.trackis.trackisapi.entity;

import com.trackis.trackisapi.entity.audit.AuditMetadata;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "issue")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Issue extends AuditMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = 849328404329398L;

    private Long id;
    private String title;
    private String description;
    private User author;
    private Project project;
    private Long modifiedBy;
    private Set<Comment> comments = new HashSet<>();

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    public User getAuthor() {
        return author;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    public Project getProject() {
        return project;
    }

    @LastModifiedBy
    @Column(name = "modified_by")
    public Long getModifiedBy() {
        return modifiedBy;
    }

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> getComments() {
        return comments;
    }

    @Transient
    public Set<Comment> getCommentsView() {
        return Collections.unmodifiableSet(comments);
    }

}
