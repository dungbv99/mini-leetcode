create table contest_problem
(
    problem_id varchar(60) not null,
    problem_name varchar(200),
    problem_statement text,
    created_by_user_login_id varchar(60),
    time_limit  int,
    memory_limit int,
    level_id varchar(60),
    category_id varchar(60),
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_contest_problem primary key (problem_id),
    constraint fk_contest_problem foreign key (created_by_user_login_id) references user_login(user_login_id)
);

create table contest_problem
(
    problem_id varchar(60) not null,
    problem_name varchar(200),
    problem_statement text,
    created_by_user_login_id varchar(60),
    time_limit  int,
    memory_limit int,
    level_id varchar(60),
    category_id varchar(60),
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_contest_problem primary key (problem_id),
    constraint fk_contest_problem foreign key (created_by_user_login_id) references user_login(user_login_id)
);

create table contest_problem_test(
    problem_test_id uuid not null default uuid_generate_v1(),
    problem_id varchar(60),
    problem_test_filename varchar(200),
    problem_test_point int,

    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_contest_problem_test primary key (problem_test_id),
    constraint fk_contest_problem_test_problem_id foreign key (problem_id) references contest_problem(problem_id)

);

create table programming_contest(
    contest_id varchar(60),
    contest_name varchar(200),
    created_by_user_login_id varchar(60),
    contest_type_id varchar(60),
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_contest_id primary key (contest_id),
    constraint fk_contest_create_by_user_login_id foreign key (created_by_user_login_id) references user_login(user_login_id)
);
create table programming_contest_problem(
    contest_id varchar(60),
    problem_id varchar(60),
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_programming_contest_problem primary key(contest_id,problem_id),
    constraint fk_programming_contest_problem_contest_id foreign key(contest_id) references programming_contest(contest_id),
    constraint fk_programming_contest_problem_problem_id foreign key(problem_id) references contest_problem(problem_id)
);

create table programming_contest_user_registration(
    contest_id varchar(60),
    user_login_id varchar(60),
    status_id varchar(60),
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_programming_contest_user_registration primary key(contest_id, user_login_id),
    constraint fk_programming_contest_user_registration_contest_id foreign key(contest_id) references programming_contest(contest_id),
    constraint fk_programming_contest_user_registration_user_login_id foreign key(user_login_id) references user_login(user_login_id)

);
create table programming_contest_user_registration_problem(
    contest_id varchar(60),
    user_login_id varchar(60),
    problem_id varchar(60),
    points int,
    last_points int,
--     body_code varchar,
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_programming_contest_user_registration_problem primary key(contest_id, user_login_id,problem_id),
    constraint fk_programming_contest_user_registration_problem_contest_id foreign key(contest_id) references programming_contest(contest_id),
    constraint fk_programming_contest_user_registration_problem_user_login_id foreign key(user_login_id) references user_login(user_login_id),
    constraint fk_programming_contest_user_registration_problem_problem_id foreign key(problem_id) references contest_problem(problem_id)

);

create table contest_program_submission(
    contest_program_submission_id uuid not null default uuid_generate_v1(),
    contest_id varchar(60),
    problem_id varchar(60),
    submitted_by_user_login_id varchar(60),
    points int,
    full_link_file varchar(1024),
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_contest_program_submission primary key (contest_program_submission_id),
    constraint fk_contest_program_submission_contest_id foreign key(contest_id) references programming_contest(contest_id),
    constraint fk_contest_program_submission_problem_id foreign key(problem_id) references contest_problem(problem_id),
    constraint fk_contest_program_submission_submitted_by_user_login_id foreign key(submitted_by_user_login_id) references user_login(user_login_id)
);


--------------------------------------------------------------------------
-- problemEntity
create table problem_source_code(
                                    problem_source_code_id uuid not null default uuid_generate_v1(),
                                    base_source text,
                                    main_source text,
                                    problem_function_source text,
                                    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
                                    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
                                    constraint pk_source_code primary key(problem_source_code_id)
);

create table contest_problem
(
    problem_id varchar(60) not null,
    problem_source_code_id uuid,
    problem_name varchar(200),
    problem_description text, -- problem_statement
    created_by_user_login_id varchar(60),
    time_limit  int,
    memory_limit int,
    level_id varchar(60),
    category_id varchar(60),
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_contest_problem primary key (problem_id),
    constraint fk_contest_problem foreign key (created_by_user_login_id) references user_login(user_login_id),
    constraint fk_contest_problem_source_code foreign key(problem_source_code_id) references problem_source_code(problem_source_code_id)
);



create table contest_problem_test_case(
    problem_test_case_id uuid not null default uuid_generate_v1(),
    problem_test_case_point int,
    correct_answer text,
    contest_problem_id varchar(60),
    problem_source_code_id uuid,
    last_updated_stamp         timestamp DEFAULT CURRENT_TIMESTAMP,
    created_stamp              timestamp DEFAULT CURRENT_TIMESTAMP,
    constraint pk_contest_problem_test_case primary key (problem_test_case_id),
    constraint fk_contest_problem_test_case_problem_id foreign key (contest_problem_id) references contest_problem(problem_id),
    constraint fk_contest_problem_test_case_problem_source_code_id foreign key (problem_source_code_id) references problem_source_code(problem_source_code_id)
);

create table test
(
    id uuid not null default uuid_generate_v1(),
    user_name varchar (20),
    problem_name varchar (20),
    point int,
    constraint pk_test_id primary key (id)
);

insert into test(user_name, problem_name, point) values
    ('1','1', 10),('1','1', 5),('1','1', 7),('1','2', 5),('1','2', 10),('1','2', 3), ('1','3', 3),('1','3', 5),('1','3', 7),
    ('2','1', 0),('2','1', 10),('2','2', 7),('2','2', 8),('2','3', 10),('3','1', 3), ('3','2', 7),('3','3', 5),('3','3', 7);