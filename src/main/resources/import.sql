INSERT INTO usuario(id, tipo_usuario, nombre, username, email, password, role, tipo) VALUES (1, 'personal', 'Pablo', 'pablo_martinez123', 'pablo.martinez23@triana.salesianos.edu', '123456', '', 'PAS');

ALTER SEQUENCE usuario_seq RESTART WITH 51;

INSERT INTO categoria(id, nombre) VALUES(1, 'Ordenadores');

ALTER SEQUENCE categoria_seq RESTART WITH 51;

INSERT INTO ubicacion(id, nombre) VALUES(1, 'Aula 1');

ALTER SEQUENCE ubicacion_seq RESTART WITH 51;

INSERT INTO equipo(id, nombre, caracteristicas, deleted) VALUES (1, 'Ordenador', 'Un ordenador to wapo', FALSE);
ALTER SEQUENCE equipo_seq RESTART WITH 51;

INSERT INTO incidencia(id, fecha, titulo, descripcion, estado, urgencia, usuario_id, categoria_id, ubicacion_id, equipo_id) VALUES (1, '2025-01-28', 'Ordenador ardiendo', 'No sé, el ordenador está ardiendo socorro ayuda ya porfavor', 'ABIERTA', 5, 1, 1, 1, 1);