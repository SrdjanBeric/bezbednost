INSERT INTO role(id, name) VALUES (1, 'ADMIN');
INSERT INTO role(id, name) VALUES (2, 'INTERMEDIARY');
INSERT INTO role(id, name) VALUES (3, 'END_ENTITY');

-- PASSWORD: password123
INSERT INTO user_app(id, username, password, role_id) VALUES (1, 'admin', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', 1);
INSERT INTO user_app(id, username, password, role_id) VALUES (2, 'intermediary', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', 2);
INSERT INTO user_app(id, username, password, role_id) VALUES (3, 'ee', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', 3);

INSERT INTO user_app(id, username, password, role_id) VALUES (4, 'admin1', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', 1);
INSERT INTO user_app(id, username, password, role_id) VALUES (5, 'intermediary1', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', 2);
INSERT INTO user_app(id, username, password, role_id) VALUES (6, 'ee1', '$2a$10$SFp508WvAPKDbemvKcYdd.wLahgUcoBDOPjBRXgNMzDBe3ot/ElwG', 3);
