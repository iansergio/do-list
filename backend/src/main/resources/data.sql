INSERT INTO tasks (id, title, description, priority, status, due_date) VALUES (RANDOM_UUID(), 'Comprar mantimentos', 'Comprar leite, ovos e pão no supermercado', 'MEDIUM', 'PENDING', TIMESTAMP '2025-12-18 10:00:00');
INSERT INTO tasks (id, title, description, priority, status, due_date) VALUES (RANDOM_UUID(), 'Lavar roupa', 'Lavar roupas brancas e coloridas separadamente', 'HIGH', 'PENDING', TIMESTAMP '2025-12-18 14:00:00');
INSERT INTO tasks (id, title, description, priority, status, due_date) VALUES (RANDOM_UUID(), 'Enviar e-mails', 'Responder e-mails urgentes da equipe', 'LOW', 'FINISHED', TIMESTAMP '2025-12-17 16:00:00');
INSERT INTO tasks (id, title, description, priority, status, due_date) VALUES (RANDOM_UUID(), 'Reunião de equipe', 'Participar da reunião semanal do projeto', 'MEDIUM', 'PENDING', TIMESTAMP '2025-12-19 09:00:00');
INSERT INTO tasks (id, title, description, priority, status, due_date) VALUES (RANDOM_UUID(), 'Exercícios', 'Fazer 30 minutos de caminhada', 'HIGH', 'PENDING', TIMESTAMP '2025-12-18 07:30:00');
INSERT INTO tasks (id, title, description, priority, status, due_date) VALUES (RANDOM_UUID(), 'Limpar casa', 'Limpar a sala, cozinha e banheiro', 'MEDIUM', 'PENDING', TIMESTAMP '2025-12-18 11:00:00');
INSERT INTO tasks (id, title, description, priority, status, due_date) VALUES (RANDOM_UUID(), 'Pagar contas', 'Pagar a conta de luz e internet', 'LOW', 'FINISHED', TIMESTAMP '2025-12-16 15:00:00');
INSERT INTO tasks (id, title, description, priority, status, due_date) VALUES (RANDOM_UUID(), 'Preparar almoço', 'Cozinhar arroz, feijão e frango', 'MEDIUM', 'PENDING', TIMESTAMP '2025-12-18 12:30:00');
INSERT INTO tasks (id, title, description, priority, status, due_date) VALUES (RANDOM_UUID(), 'Comprar presentes', 'Comprar presentes de Natal para família', 'LOW', 'PENDING', TIMESTAMP '2025-12-20 14:00:00');
INSERT INTO tasks (id, title, description, priority, status, due_date) VALUES (RANDOM_UUID(), 'Organizar garagem', 'Arrumar ferramentas e caixas na garagem', 'HIGH', 'PENDING', TIMESTAMP '2025-12-21 10:00:00');


INSERT INTO users (id) VALUES ( RANDOM_UUID() )