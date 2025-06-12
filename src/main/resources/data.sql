
INSERT INTO "users" (id, username, password, role) VALUES (1, 'admin', '$2a$10$E9dVG2G.qcwEyCV9Y5UG0.tXRIKPKTOhdm4I3C5fJcZT9x9tIB2zK', 'ADMIN')
    ON CONFLICT (username) DO NOTHING;
