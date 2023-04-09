INSERT INTO role(id, name) VALUES (1, 'ADMIN');
INSERT INTO role(id, name) VALUES (2, 'CA');
INSERT INTO role(id, name) VALUES (3, 'END_ENTITY');

INSERT INTO user_app(id, username, email, password, role_id) VALUES (1, 'admin', 'admin@email.com', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', 1);