-- Adding default user roles
INSERT IGNORE INTO t_role (role_id, role_name) VALUES (1, 'Administrator');
INSERT IGNORE INTO t_role (role_id, role_name) VALUES (2, 'Sale User');
INSERT IGNORE INTO t_role (role_id, role_name) VALUES (3, 'Customer User');
INSERT IGNORE INTO t_role (role_id, role_name) VALUES (4, 'Secure API User');