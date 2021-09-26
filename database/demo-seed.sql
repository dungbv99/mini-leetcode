INSERT INTO party_type (party_type_id, parent_type_id, has_table, description, last_updated_stamp, created_stamp)
VALUES ('PERSON', NULL, TRUE, 'Person', NOW(), NOW());

INSERT INTO status_type (status_type_id, parent_type_id, description, last_updated_stamp, created_stamp)
VALUES ('PARTY_STATUS', NULL, 'Party status', NOW(), NOW());

INSERT INTO status (status_id, status_type_id, status_code, sequence_id, description, last_updated_stamp, created_stamp)
VALUES ('PARTY_ENABLED', 'PARTY_STATUS', 'ENABLED', 0, 'Đã kích hoạt', NOW(), NOW());

INSERT INTO user_login (user_login_id, current_password, password_hint, is_system, enabled, has_logged_out,
                        require_password_change, disabled_date_time, successive_failed_logins, last_updated_stamp,
                        created_stamp, party_id)
VALUES ('admin', '$2a$10$Y4FXX6TalapgQ3rJoe.QHe9.RutM4l81pAm2S1XzDuUR83qLvDxyO', NULL, FALSE, TRUE, FALSE, FALSE, NULL,
        NULL, NOW(), NOW(), 'bd6322f2-2121-11ea-81a8-979e2f76b5a4');

INSERT INTO user_login_security_group (user_login_id, group_id, last_updated_stamp, created_stamp)
VALUES ('admin', 'ROLE_FULL_ADMIN', NOW(), NOW());

INSERT INTO person (party_id, first_name, middle_name, last_name, gender, birth_date, last_updated_stamp, created_stamp)
VALUES ('bd6322f2-2121-11ea-81a8-979e2f76b5a4', 'admin', ',', ',', 'M', NOW(), null, NOW());