INSERT INTO status_type (status_type_id, parent_type_id, description, last_updated_stamp, created_stamp)
VALUES ('PERSON_STATUS', NULL, 'Person status', NOW(), NOW());

INSERT INTO status (status_id, status_type_id, status_code, sequence_id, description, last_updated_stamp, created_stamp)
VALUES ('PERSON_ENABLED', 'PERSON_STATUS', 'ENABLED', 0, 'Đã kích hoạt', NOW(), NOW());

INSERT INTO status (status_id, status_type_id, status_code, sequence_id, description, last_updated_stamp, created_stamp)
VALUES ('PERSON_DISABLED', 'PERSON_STATUS', 'DISABLED', 0, 'Chua kích hoạt', NOW(), NOW());

insert into person(person_id, first_name, middle_name, last_name, status_id) values ('f265ff04-4d90-11ec-9f6c-0242ac110002', 'Bui', 'Viet', 'Dung', 'PERSON_ENABLED');

insert into user_login(user_login_id, person_id, current_password) values ('admin', 'f265ff04-4d90-11ec-9f6c-0242ac110002', '$2a$10$0IOYLdfBGy5whZGnBaqmK.KYvFGcLZPIgtexl8YR9f7FZ79loFk36');


insert into security_group(group_id,description, group_name) values ('ROLE_ADMIN', 'ADMIN','ADMIN'), ('ROLE_TEACHER', 'TEACHER', 'TEACHER'), ('ROLE_STUDENT', 'STUDENT','STUDENT');

insert into security_permission(permission_id, description) values ('ADMIN', 'ADMIN'), ('TEACHER', 'TEACHER'), ('STUDENT', 'STUDENT');

insert into security_group_permission(group_id, permission_id) values ('ROLE_ADMIN', 'ADMIN'), ('ROLE_TEACHER', 'TEACHER'), ('ROLE_STUDENT', 'STUDENT');

insert into user_login_security_group(user_login_id, group_id) values ('admin', 'ROLE_ADMIN'),('admin', 'ROLE_TEACHER'), ('admin', 'ROLE_STUDENT');


INSERT INTO application_type(application_type_id, description, last_updated_stamp, created_stamp)
VALUES ('MENU', 'Menu application type', NOW(), NOW());
INSERT INTO application_type(application_type_id, description, last_updated_stamp, created_stamp)
VALUES ('SCREEN', 'Screen application type', NOW(), NOW());
INSERT INTO application_type(application_type_id, description, last_updated_stamp, created_stamp)
VALUES ('MODULE', 'Module application type', NOW(), NOW());
INSERT INTO application_type(application_type_id, description, last_updated_stamp, created_stamp)
VALUES ('SERVICE', 'Service application type', NOW(), NOW());
INSERT INTO application_type(application_type_id, description, last_updated_stamp, created_stamp)
VALUES ('ENTITY', 'Entity application type', NOW(), NOW());


insert into application(application_id, application_type_id, module_id, permission_id, description)
values ('MENU_ADMIN', 'MENU',NULL,NULL,'MENU ADMIN PRIMARY'),
       ('MENU_ADMIN_CHILDREN', 'MENU','MENU_ADMIN', 'ADMIN', 'MENU ADMIN CHILDREN'),
       ('MENU_TEACHER', 'MENU',NULL,NULL,'MENU TEACHER PRIMARY'),
       ('MENU_TEACHER_CHILDREN', 'MENU','MENU_TEACHER', 'TEACHER', 'MENU TEACHER CHILDREN'),
       ('MENU_STUDENT', 'MENU',NULL,NULL,'MENU STUDENT PRIMARY'),
       ('MENU_STUDENT_CHILDREN', 'MENU','MENU_STUDENT', 'STUDENT', 'MENU STUDENT CHILDREN');

insert into status_item(status_id, status_code) values ('USER_REGISTERED', 'REGISTERED'), ('USER_APPROVED', 'APPROVED'), ('USER_DISABLED', 'DISABLED');
update user_login set (user_login_id, current_password, password_hint, is_system, enabled, has_logged_out,
                          require_password_change, disabled_date_time, successive_failed_logins, last_updated_stamp,
                          created_stamp)
                          = ('admin', '$2a$10$0IOYLdfBGy5whZGnBaqmK.KYvFGcLZPIgtexl8YR9f7FZ79loFk36', NULL, FALSE, TRUE, FALSE, FALSE, NULL,
                             NULL, NOW(), NOW());