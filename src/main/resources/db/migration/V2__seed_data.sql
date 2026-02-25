-- Seed data for initial roles only

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_MANAGER'),
       ('ROLE_USER')
ON DUPLICATE KEY UPDATE name = VALUES(name);
