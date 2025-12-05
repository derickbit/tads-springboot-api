DELETE FROM users_perfis;
DELETE FROM users;
DELETE FROM perfis;

ALTER TABLE users ALTER COLUMN id RESTART WITH 100;
ALTER TABLE perfis ALTER COLUMN id RESTART WITH 100;

INSERT INTO perfis(id, nome) VALUES (10, 'ROLE_ADMIN');
INSERT INTO perfis(id, nome) VALUES (20, 'ROLE_USER');

INSERT INTO users (id, name, email, password_hash, email_verified_at) VALUES (10, 'Admin Teste', 'admin@email.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00dmxs.AQiy3.a', CURRENT_TIMESTAMP);
INSERT INTO users (id, name, email, password_hash, email_verified_at) VALUES (20, 'User Teste', 'user@email.com', '$2a$10$Bz6jpv_3rf-Nl5UVXBVDTEqr1DYhWQF0que0IFrurls', CURRENT_TIMESTAMP);

INSERT INTO users_perfis(user_id, perfil_id) VALUES (10, 10);
INSERT INTO users_perfis(user_id, perfil_id) VALUES (20, 20);