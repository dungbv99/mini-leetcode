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

create TABLE person
(
    person_id          UUID NOT NULL DEFAULT uuid_generate_v1(),
    status_id          varchar (20),
    first_name         VARCHAR(100),
    middle_name        VARCHAR(100),
    last_name          VARCHAR(100),
    gender             CHARACTER(1),
    birth_date         DATE,
    last_updated_stamp TIMESTAMP NULL,
    created_stamp      TIMESTAMP DEFAULT current_date,
    constraint pk_person_id primary key (person_id),
    constraint fk_status_id_person foreign key(status_id) references status(status_id)

);


create TABLE user_login
(
    user_login_id            VARCHAR(255)        NOT NULL,
    person_id                UUID,
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
    UNIQUE (person_id),
    CONSTRAINT pk_user_login PRIMARY KEY (user_login_id),
    CONSTRAINT pk_person_id_user_login FOREIGN KEY (person_id) references person(person_id)
);

CREATE TABLE security_group
(
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


create TABLE application_type
(
    application_type_id VARCHAR(60) NOT NULL,
    description         TEXT,
    last_updated_stamp  TIMESTAMP,
    created_stamp       TIMESTAMP DEFAULT current_date ,
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
    created_stamp       TIMESTAMP DEFAULT current_date ,
    CONSTRAINT pk_application PRIMARY KEY (application_id),
    CONSTRAINT application_application_type FOREIGN KEY (application_type_id) REFERENCES application_type (application_type_id),
    CONSTRAINT application_application_module FOREIGN KEY (module_id) REFERENCES application (application_id),
    CONSTRAINT application_permission FOREIGN KEY (permission_id) REFERENCES security_permission (permission_id)
);

create TABLE notification_type
(
    notification_type_id varchar(100) NOT NULL,
    notification_type_name varchar(200) NULL,
    last_updated_stamp timestamp NULL,
    created_stamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_notification_type_id PRIMARY KEY (notification_type_id)
);

create TABLE notifications
(
    id uuid NOT NULL DEFAULT uuid_generate_v1(),
    "content" varchar(500) NULL,
--     notification_type_id varchar(100) NULL,
    from_user varchar(60) NULL,
    to_user varchar(60) NULL,
    url varchar(200) NULL,
    status_id varchar(60) NULL,
    last_updated_stamp timestamp NULL,
    created_stamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_notification_id PRIMARY KEY (id),
--     CONSTRAINT fk_notification_notification_type_id FOREIGN KEY (notification_type_id) REFERENCES notification_type(notification_type_id),
    CONSTRAINT fk_notification_user_login_id FOREIGN KEY (to_user) REFERENCES user_login(user_login_id)
);

create TABLE status_item
(
    status_id          VARCHAR(60) NOT NULL,
    status_code        VARCHAR(60),
    description        TEXT,
    last_updated_stamp TIMESTAMP,
    created_stamp      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_status_id PRIMARY KEY (status_id)
);


create TABLE user_register
(
    user_login_id varchar(60) NOT NULL,
    password varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    first_name varchar(100) NOT NULL,
    middle_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    status_id varchar(60) NULL,
    registered_roles text NOT NULL,
    affiliations text,
    last_updated_stamp timestamp NULL,
    created_stamp timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_register__login PRIMARY KEY (user_login_id),
    CONSTRAINT fk_register_status FOREIGN KEY (status_id) REFERENCES status_item(status_id)
);
-- contestEntity defind


create table contest_problem_new
(
    problem_id varchar(100) not null,
    problem_name varchar(100) unique ,
    problem_description text, -- problem_statement
    created_by_user_login_id varchar(60),
    time_limit  int,
    memory_limit int,
    level_id varchar(60),
    category_id varchar(60),
    last_updated_stamp         timestamp DEFAULT current_date ,
    created_stamp              timestamp DEFAULT current_date ,
    solution text,
    level_order int,
    correct_solution_source_code text,
    correct_solution_language varchar(10),
    constraint pk_contest_problem primary key (problem_id),
    constraint fk_contest_problem foreign key (created_by_user_login_id) references user_login(user_login_id)
);

create table problem_source_code_new
(
    problem_source_code_id varchar (70),
    base_source text,
    main_source text,
    problem_function_default_source text,
    problem_function_solution text,
    language varchar (10),
    contest_problem_id varchar(60),
    last_updated_stamp         timestamp DEFAULT current_date ,
    created_stamp              timestamp DEFAULT current_date ,
    constraint pk_source_code primary key(problem_source_code_id),
    constraint fk_contest_problem foreign key (contest_problem_id) references contest_problem_new(problem_id)
);


create table test_case_new
(
    test_case_id  UUID NOT NULL default uuid_generate_v1(),
    test_case_point int,
    test_case text,
    correct_answer text,
    contest_problem_id varchar(60),
    last_updated_stamp         timestamp DEFAULT current_date ,
    created_stamp              timestamp DEFAULT current_date ,
    constraint pk_contest_problem_test_case primary key (test_case_id),
    constraint fk_contest_problem_test_case_problem_id foreign key (contest_problem_id) references contest_problem_new(problem_id)
);

create table problem_submission_new
(
    problem_submission_id UUID NOT NULL default uuid_generate_v1(),
    problem_id  varchar(100) not null,
    submitted_by_user_login_id varchar(60),
    source_code text,
    source_code_language varchar (10),
    status varchar(20),
    score int,
    runtime varchar(10),
    memory_usage float ,
    test_case_pass varchar (10),
    created_stamp              varchar (25),
    constraint fk_problem_submission_id primary key(problem_submission_id),
    constraint fk_problem_id foreign key (problem_id) references contest_problem_new(problem_id),
    constraint fk_user_login_id foreign key (submitted_by_user_login_id) references user_login(user_login_id)
);

-- drop table problem_submission,  test_case,  contest_problem, problem_source_code;

create table contest_new
(
    contest_id varchar (100) not null ,
    contest_name varchar (100),
    contest_solving_time int,
    user_create_id varchar (60),
    try_again BOOLEAN,
    public BOOLEAN,
    last_updated_stamp         timestamp DEFAULT current_date ,
    created_stamp              timestamp DEFAULT current_date ,
    constraint pk_contest_id primary key (contest_id),
    constraint fk_user_create_contest foreign key (user_create_id) references user_login(user_login_id)
);

create table contest_contest_problem_new
(
    contest_id varchar (100) not null ,
    problem_id varchar (100) not null ,
    last_updated_stamp         timestamp DEFAULT current_date ,
    created_stamp              timestamp DEFAULT current_date ,
    constraint fk_contest_id_contest_contest_problem foreign key (contest_id) references contest_new(contest_id),
    constraint fk_problem_id_contest_contest_problem foreign key (problem_id) references contest_problem_new(problem_id)
);

create table contest_submission_new
(
    contest_submission_id  UUID NOT NULL default uuid_generate_v1(),
    contest_id varchar (100) not null ,
    problem_id varchar (100) not null ,
    user_submission_id varchar (100) not null ,
--     problem_submission_id UUID,
    status varchar (20),
    point int,
    test_case_pass varchar (20),
    source_code text,
    source_code_language varchar (10),
    runtime float ,
    memory_usage float ,
    last_updated_stamp         date default current_date ,
    created_stamp              date default current_date ,
    constraint pk_contest_submission_id_contest_submission primary key (contest_submission_id),
    constraint fk_contest_id_contest_submission foreign key (contest_id) references contest_new(contest_id),
    constraint fk_problem_id_contest_submission foreign key (problem_id) references contest_problem_new(problem_id),
    constraint fk_user_submission_id_contest_submission foreign key (user_submission_id) references user_login(user_login_id)
--     constraint fk_problem_submission_id_contest_submission foreign key(problem_submission_id) references problem_submission_new(problem_submission_id)
);

create table user_submission_contest_result_new
(
    user_submission_contest_result_id UUID NOT NULL default uuid_generate_v1(),
    contest_id varchar (100) not null ,
    user_id varchar (100) not null,
    point int not null,
    last_updated_stamp         date default current_date ,
    created_stamp              date default current_date ,
    constraint pk_user_submission_result_id_user_submission_result primary key (user_submission_contest_result_id),
    constraint fk_contest_id_user_submission_result foreign key (contest_id) references contest_new(contest_id),
    constraint fk_user_id_user_submission_result foreign key (user_id) references user_login(user_login_id)
);

create table user_registration_contest_new
(
    user_registration_contest_id UUID NOT NULL default uuid_generate_v1(),
    user_id varchar (100) not null ,
    contest_id varchar (100) not null ,
    status varchar (20) not null,
    constraint fk_user_id_user_registration_contest foreign key (user_id) references user_login(user_login_id),
    constraint fk_contest_id_user_registration_contest foreign key (contest_id) references contest_new(contest_id)
);

drop table user_submission_contest_result, contest_contest_problem, contest_submission, contest_contest_problem, contest, problem_submission, test_case, problem_source_code, contest_problem;