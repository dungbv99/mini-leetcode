CREATE DATABASE "leetcode"
    WITH OWNER "postgres"
    ENCODING 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE template0;
-- user defind
CREATE OR REPLACE FUNCTION public.uuid_generate_v1()
 RETURNS uuid
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/uuid-ossp', $function$uuid_generate_v1$function$
;


create TABLE status_type
(
    status_type_id     VARCHAR(60) NOT NULL,
    parent_type_id     VARCHAR(60),
    description        TEXT,
    last_updated_stamp TIMESTAMP,
    created_stamp      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_status_type PRIMARY KEY (status_type_id),
    CONSTRAINT status_type_parent FOREIGN KEY (parent_type_id) REFERENCES status_type (status_type_id)
);

create TABLE status
(
    status_id          VARCHAR(60) NOT NULL,
    status_type_id     VARCHAR(60),
    status_code        VARCHAR(60),
    sequence_id        VARCHAR(60),
    description        TEXT,
    last_updated_stamp TIMESTAMP,
    created_stamp      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_status PRIMARY KEY (status_id),
    CONSTRAINT status_to_type FOREIGN KEY (status_type_id) REFERENCES status_type (status_type_id)
);

create TABLE party_type
(
    party_type_id      VARCHAR(60) NOT NULL,
    parent_type_id     VARCHAR(60),
    has_table          BOOLEAN,
    description        TEXT,
    last_updated_stamp TIMESTAMP,
    created_stamp      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_party_type PRIMARY KEY (party_type_id),
    CONSTRAINT party_type_par FOREIGN KEY (parent_type_id) REFERENCES party_type (party_type_id)
);

create TABLE party
(
    party_id                    UUID      NOT NULL default uuid_generate_v1(),
    party_type_id               VARCHAR(60),
    name                        varchar(200),
    external_id                 VARCHAR(60),
    description                 TEXT,
    status_id                   VARCHAR(60),
    created_date                TIMESTAMP NULL,
    created_by_user_login       VARCHAR(255),
    last_modified_date          TIMESTAMP NULL,
    last_modified_by_user_login VARCHAR(255),
    is_unread                   BOOLEAN,
    last_updated_stamp          TIMESTAMP,
    created_stamp               TIMESTAMP          DEFAULT CURRENT_TIMESTAMP,
    party_code                  VARCHAR(255),
    CONSTRAINT pk_party PRIMARY KEY (party_id),
    CONSTRAINT party_status_item FOREIGN KEY (status_id) REFERENCES status (status_id),
    CONSTRAINT party_pty_typ FOREIGN KEY (party_type_id) REFERENCES party_type (party_type_id)
--     CONSTRAINT party_m_user_login FOREIGN KEY (last_modified_by_user_login) REFERENCES user_login (user_login_id),
--     CONSTRAINT party_c_user_login FOREIGN KEY (created_by_user_login) REFERENCES user_login (user_login_id)
);



create TABLE user_login
(
    user_login_id            VARCHAR(255)        NOT NULL,
    current_password         VARCHAR(60),
    otp_secret               VARCHAR(60),
    client_token             VARCHAR(512),
    password_hint            TEXT,
    is_system                BOOLEAN,
    enabled                  BOOLEAN,
    has_logged_out           BOOLEAN,
    require_password_change  BOOLEAN,
    disabled_date_time       TIMESTAMP           NULL,
    successive_failed_logins INTEGER,
    last_updated_stamp       TIMESTAMP,
    created_stamp            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    otp_resend_number        INT       DEFAULT 0 NULL,
    party_id                 UUID,
    CONSTRAINT pk_user_login PRIMARY KEY (user_login_id),
    CONSTRAINT user_party FOREIGN KEY (party_id) REFERENCES party (party_id)
);

alter table party
    add CONSTRAINT party_m_user_login FOREIGN KEY (last_modified_by_user_login) REFERENCES user_login (user_login_id);
alter table party
    add CONSTRAINT party_c_user_login FOREIGN KEY (created_by_user_login) REFERENCES user_login (user_login_id);

CREATE TABLE public.security_group (
                                       group_id varchar(60) NOT NULL,
                                       description text NULL,
                                       last_updated_stamp timestamp NULL,
                                       created_stamp timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                       group_name varchar(100) NULL,
                                       CONSTRAINT pk_security_group PRIMARY KEY (group_id)
);



create TABLE security_permission
(
    permission_id      VARCHAR(100) NOT NULL,
    description        TEXT,
    last_updated_stamp TIMESTAMP,
    created_stamp      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_security_permission PRIMARY KEY (permission_id)
);

create TABLE user_login_security_group
(
    user_login_id      VARCHAR(255) NOT NULL,
    group_id           VARCHAR(60)  NOT NULL,
    last_updated_stamp TIMESTAMP,
    created_stamp      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_user_login_security_group PRIMARY KEY (user_login_id, group_id),
    CONSTRAINT user_secgrp_grp FOREIGN KEY (group_id) REFERENCES security_group (group_id),
    CONSTRAINT user_secgrp_user FOREIGN KEY (user_login_id) REFERENCES user_login (user_login_id)
);

create TABLE security_group_permission
(
    group_id           VARCHAR(60)  NOT NULL,
    permission_id      VARCHAR(100) NOT NULL,
    last_updated_stamp TIMESTAMP,
    created_stamp      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_security_group_permission PRIMARY KEY (group_id, permission_id),
    CONSTRAINT sec_grp_perm_grp FOREIGN KEY (group_id) REFERENCES security_group (group_id),
    CONSTRAINT sec_grp_perm_perm FOREIGN KEY (permission_id) REFERENCES security_permission (permission_id)
);

create TABLE person
(
    party_id           UUID      NOT NULL,
    first_name         VARCHAR(100),
    middle_name        VARCHAR(100),
    last_name          VARCHAR(100),
    gender             CHARACTER(1),
    birth_date         DATE,
    last_updated_stamp TIMESTAMP NULL,
    created_stamp      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_person PRIMARY KEY (party_id),
    CONSTRAINT person_party FOREIGN KEY (party_id) REFERENCES party (party_id)
);

create TABLE application_type
(
    application_type_id VARCHAR(60) NOT NULL,
    description         TEXT,
    last_updated_stamp  TIMESTAMP,
    created_stamp       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_application_type PRIMARY KEY (application_type_id)
);

create TABLE application
(
    application_id      VARCHAR(255) NOT NULL,
    application_type_id VARCHAR(255) NOT NULL,
    module_id           VARCHAR(255),
    permission_id       VARCHAR(255),
    description         TEXT,
    last_updated_stamp  TIMESTAMP,
    created_stamp       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_application PRIMARY KEY (application_id),
    CONSTRAINT application_application_type FOREIGN KEY (application_type_id) REFERENCES application_type (application_type_id),
    CONSTRAINT application_application_module FOREIGN KEY (module_id) REFERENCES application (application_id),
    CONSTRAINT application_permission FOREIGN KEY (permission_id) REFERENCES security_permission (permission_id)
);


-- contest defind
create table problem_source_code
(
    problem_source_code_id varchar (70),
    base_source text,
    main_source text,
    problem_function_default_source text,
    problem_function_solution text,
    language varchar (10),
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_source_code primary key(problem_source_code_id)
);

create table contest_problem
(
    problem_id varchar(100) not null,
    problem_name varchar(100) unique ,
    problem_description text, -- problem_statement
    created_by_user_login_id varchar(60),
    time_limit  int,
    memory_limit int,
    level_id varchar(60),
    category_id varchar(60),
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    solution text,
    correct_solution_source_code text,
    correct_solution_language varchar(10),
    constraint pk_contest_problem primary key (problem_id),
    constraint fk_contest_problem foreign key (created_by_user_login_id) references user_login(user_login_id)
);

create table contest_problem_problem_source_code
(
    problem_source_code_id varchar (70),
    problem_id varchar(60) not null,
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint fk_problem_source_code foreign key (problem_source_code_id) references problem_source_code(problem_source_code_id),
    constraint fk_contest_problem foreign key (problem_id) references contest_problem(problem_id)
);

create table test_case
(
    test_case_id varchar (100) not null,
    test_case_point int,
    test_case text,
    correct_answer text,
    contest_problem_id varchar(60),
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_contest_problem_test_case primary key (test_case_id),
    constraint fk_contest_problem_test_case_problem_id foreign key (contest_problem_id) references contest_problem(problem_id)
);

create table problem_submission
(
    problem_id  varchar(100) not null,
    submitted_by_user_login_id varchar(60),
    submission_source_code text,
    submission_source_code_language varchar (10),
    submission_status varchar(20),
    submission_score int,
    submission_runtime float ,
    submission_memory_usage float ,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint fk_problem_id foreign key (problem_id) references contest_problem(problem_id),
    constraint fk_user_login_id foreign key (submitted_by_user_login_id) references user_login(user_login_id)
);

