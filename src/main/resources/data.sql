-- Crear tabla users
CREATE TABLE IF NOT EXISTS "users" (
                                       id UUID PRIMARY KEY,
                                       first_name VARCHAR(255),
    last_name VARCHAR(255),
    birth_date DATE,
    dui VARCHAR(20),
    phone_number VARCHAR(20),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(50)
    );

-- Insertar usuario admin
INSERT INTO "users" (
    id,
    first_name,
    last_name,
    birth_date,
    dui,
    phone_number,
    email,
    password,
    role
) VALUES (
             '0f3b5ef2-27e7-4c8b-8f2d-9b09fa309c67',
             'Admin',
             'Root',
             '1990-01-01',
             '00000000-0',
             '7000-0000',
             'admin@example.com',
             '$2a$10$E9dVG2G.qcwEyCV9Y5UG0.tXRIKPKTOhdm4I3C5fJcZT9x9tIB2zK',
             'ADMIN'
         ) ON CONFLICT (email) DO NOTHING;