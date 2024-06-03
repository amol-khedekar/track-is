DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS project CASCADE;
DROP TABLE IF EXISTS project_user CASCADE;
DROP TABLE IF EXISTS issue CASCADE;
DROP TABLE IF EXISTS comment CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS user_role CASCADE;

CREATE TABLE "user"
(
    id                  BIGSERIAL,
    username            varchar(255) NOT NULL,
    password            varchar(255) NOT NULL,
    email               varchar(255) NOT NULL,
    account_locked      BOOLEAN,
    account_enabled     BOOLEAN,
    account_expired     BOOLEAN,
    credentials_expired BOOLEAN,
    created_date        TIMESTAMP    NOT NULL,
    modified_date       TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE project
(
    id            BIGSERIAL,
    title         varchar(255) NOT NULL,
    description   TEXT,
    private       BOOLEAN      NOT NULL,
    user_id       BIGINT       NOT NULL,
    created_date  TIMESTAMP    NOT NULL,
    modified_date TIMESTAMP    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_project FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE project_user
(
    project_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (project_id) REFERENCES project (id),
    PRIMARY KEY (project_id, user_id),
    CONSTRAINT fk_project_project_user FOREIGN KEY (project_id) REFERENCES project (id),
    CONSTRAINT fk_user_project_user FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE issue
(
    id            BIGSERIAL,
    title         varchar(255) NOT NULL,
    description   TEXT,
    user_id       BIGINT       NOT NULL,
    project_id    BIGINT       NOT NULL,
    created_date  TIMESTAMP    NOT NULL,
    modified_date TIMESTAMP    NOT NULL,
    modified_by   BIGINT       NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_issue FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT fk_project_issue FOREIGN KEY (project_id) REFERENCES project (id)
);

CREATE TABLE comment
(
    id            BIGSERIAL,
    body          TEXT      NOT NULL,
    user_id       BIGINT    NOT NULL,
    issue_id      BIGINT    NOT NULL,
    created_date  TIMESTAMP NOT NULL,
    modified_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_comment FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT fk_issue_comment FOREIGN KEY (issue_id) REFERENCES issue (id)
);

CREATE TABLE role
(
    id   BIGSERIAL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    id      BIGSERIAL,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "user" (id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    PRIMARY KEY (role_id, user_id),
    CONSTRAINT fk_user_user_role FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT fk_role_user_role FOREIGN KEY (role_id) REFERENCES role (id)
);


CREATE TABLE acl_sid
(
    id        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    principal BOOLEAN      NOT NULL,
    sid       VARCHAR(100) NOT NULL,
    UNIQUE KEY unique_acl_sid (sid, principal)
) ENGINE=InnoDB;

CREATE TABLE acl_class
(
    id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    class VARCHAR(100) NOT NULL,
    UNIQUE KEY uk_acl_class (class)
) ENGINE=InnoDB;

CREATE TABLE acl_object_identity
(
    id                 BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    object_id_class    BIGINT UNSIGNED NOT NULL,
    object_id_identity VARCHAR(36) NOT NULL,
    parent_object      BIGINT UNSIGNED,
    owner_sid          BIGINT UNSIGNED,
    entries_inheriting BOOLEAN     NOT NULL,
    UNIQUE KEY uk_acl_object_identity (object_id_class, object_id_identity),
    CONSTRAINT fk_acl_object_identity_parent FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_object_identity_class FOREIGN KEY (object_id_class) REFERENCES acl_class (id),
    CONSTRAINT fk_acl_object_identity_owner FOREIGN KEY (owner_sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;

CREATE TABLE acl_entry
(
    id                  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    acl_object_identity BIGINT UNSIGNED NOT NULL,
    ace_order           INTEGER NOT NULL,
    sid                 BIGINT UNSIGNED NOT NULL,
    mask                INTEGER UNSIGNED NOT NULL,
    granting            BOOLEAN NOT NULL,
    audit_success       BOOLEAN NOT NULL,
    audit_failure       BOOLEAN NOT NULL,
    UNIQUE KEY unique_acl_entry (acl_object_identity, ace_order),
    CONSTRAINT fk_acl_entry_object FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id),
    CONSTRAINT fk_acl_entry_acl FOREIGN KEY (sid) REFERENCES acl_sid (id)
) ENGINE=InnoDB;