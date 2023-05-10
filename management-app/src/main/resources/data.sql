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