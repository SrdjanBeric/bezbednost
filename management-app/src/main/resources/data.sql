INSERT INTO role(id, name) VALUES (1, 'ADMIN');
INSERT INTO role(id, name) VALUES (2, 'SOFTWARE_ENGINEER');
INSERT INTO role(id, name) VALUES (3, 'PROJECT_MANAGER');
INSERT INTO role(id, name) VALUES (4, 'HUMAN_RESOURCES_MANAGER');

-- PASSWORD: password123
INSERT INTO user_app(id, username, email, password, role_id) VALUES (nextval('user_app_seq'), 'admin', 'admin@email.com', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', 1);
