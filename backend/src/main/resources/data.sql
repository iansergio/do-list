INSERT INTO users (
    id,
    name,
    email,
    password,
    role,
    created_at,
    updated_at
)
VALUES (
           'f648b5f1-91e2-4818-b99e-81d431ae1263',
           'Administrator',
           'admin@gmail.com',
           '$2a$10$rxKXTdcnnoINAbQZAwB0k.OM9Tag4tbtm4AY8dGwvseI9B1kcwKgW',
           'ADMIN',
           NOW(),
           NOW()
       );
