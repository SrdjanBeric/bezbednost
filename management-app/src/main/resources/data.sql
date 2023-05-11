INSERT INTO role(id, name) VALUES (1, 'ADMIN');
INSERT INTO role(id, name) VALUES (2, 'SOFTWARE_ENGINEER');
INSERT INTO role(id, name) VALUES (3, 'PROJECT_MANAGER');
INSERT INTO role(id, name) VALUES (4, 'HUMAN_RESOURCES_MANAGER');

-- PASSWORD: password123
--Admins
INSERT INTO user_app(id, username, email, password, active, role_id) VALUES (nextval('user_app_seq'), 'admin', 'admin@email.com', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', true, 1);
INSERT INTO admin(id) values (1);
--Software engineers
INSERT INTO user_app(id, username, email, password, active, role_id) VALUES (nextval('user_app_seq'), 'engineer1', 'engineer1@email.com', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', false, 2);
INSERT INTO software_engineer(id) values (2);
INSERT INTO user_app(id, username, email, password, active, role_id) VALUES (nextval('user_app_seq'), 'engineer2', 'engineer2@email.com', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', true, 2);
INSERT INTO software_engineer(id) values (3);
--Project managers
INSERT INTO user_app(id, username, email, password, active, role_id) VALUES (nextval('user_app_seq'), 'manager1', 'manager1@email.com', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', true, 3);
INSERT INTO project_manager(id) values (4);
INSERT INTO user_app(id, username, email, password, active, role_id) VALUES (nextval('user_app_seq'), 'manager2', 'manager2@email.com', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', true, 3);
INSERT INTO project_manager(id) values (5);


--Projects
INSERT INTO project(id, name, start_date, end_date, project_manager_id) VALUES (nextval('project_seq_gen'), 'project1', '01-01-2022', '01-01-2023', 4);
INSERT INTO project(id, name, start_date, end_date, project_manager_id) VALUES (nextval('project_seq_gen'), 'project2', '01-01-2023', '01-01-2024', 5);

--Software engineer projects
INSERT INTO software_engineer_project(id, software_engineer_id, project_id, work_description, start_date, end_date) VALUES (nextval('software_engineer_project_seq_gen'), 3, 1, 'Development', '01-01-2022', '01-01-2023');
