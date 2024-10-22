INSERT INTO SP_SENSOR(id, name, sensor_value, sensor_type) VALUES(-10, 'Temperature room 2', 21.3, 'TEMPERATURE');
INSERT INTO SP_SENSOR(id, name, sensor_value, sensor_type) VALUES(-20, 'Target Temperature for Room 2', 20.0, 'TEMPERATURE');
INSERT INTO SP_SENSOR(id, name, sensor_value, sensor_type) VALUES(-9, 'Window 1 status room 1', 1.0, 'STATUS');
INSERT INTO SP_SENSOR(id, name, sensor_value, sensor_type) VALUES(-8, 'Window 2 status room 1', 0.0, 'STATUS');
INSERT INTO SP_SENSOR(id, name, sensor_value, sensor_type) VALUES(-7, 'Window 1 status room 2', 0.0, 'STATUS');
INSERT INTO SP_SENSOR(id, name, sensor_value, sensor_type) VALUES(-6, 'Window 2 status room 2', 0.0, 'STATUS');

INSERT INTO SP_ROOM(id, name, floor) VALUES(-10, 'Room1', 1);
INSERT INTO SP_ROOM(id, name, floor, current_temperature_id, target_temperature_id) VALUES(-9, 'Room2', 1, -10, -20);

INSERT INTO SP_WINDOW(id, window_status_id, name, room_id) VALUES(-10, -9, 'Window 1', -10);
INSERT INTO SP_WINDOW(id, window_status_id, name, room_id) VALUES(-9, -8, 'Window 2', -10);
INSERT INTO SP_WINDOW(id, window_status_id, name, room_id) VALUES(-8, -7, 'Window 1', -9);
INSERT INTO SP_WINDOW(id, window_status_id, name, room_id) VALUES(-7, -6, 'Window 2', -9);