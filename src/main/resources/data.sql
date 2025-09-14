-- Insert Floors
INSERT INTO parking_floor (id, floor_number) VALUES (1, 0); -- Ground Floor
INSERT INTO parking_floor (id, floor_number) VALUES (2, 1); -- First Floor

-- Insert Parking Slots for Floor 1 (Ground Floor) - Mostly for Trucks and Cars
INSERT INTO parking_slot (id, slot_number, vehicle_type, is_occupied, floor_id) VALUES (1, 101, 'TRUCK', false, 1);
INSERT INTO parking_slot (id, slot_number, vehicle_type, is_occupied, floor_id) VALUES (2, 102, 'TRUCK', false, 1);
INSERT INTO parking_slot (id, slot_number, vehicle_type, is_occupied, floor_id) VALUES (3, 103, 'CAR', false, 1);
INSERT INTO parking_slot (id, slot_number, vehicle_type, is_occupied, floor_id) VALUES (4, 104, 'CAR', false, 1);
INSERT INTO parking_slot (id, slot_number, vehicle_type, is_occupied, floor_id) VALUES (5, 105, 'CAR', false, 1);

-- Insert Parking Slots for Floor 2 (First Floor) - Mostly for Cars and Bikes
INSERT INTO parking_slot (id, slot_number, vehicle_type, is_occupied, floor_id) VALUES (6, 201, 'CAR', false, 2);
INSERT INTO parking_slot (id, slot_number, vehicle_type, is_occupied, floor_id) VALUES (7, 202, 'CAR', false, 2);
INSERT INTO parking_slot (id, slot_number, vehicle_type, is_occupied, floor_id) VALUES (8, 203, 'BIKE', false, 2);
INSERT INTO parking_slot (id, slot_number, vehicle_type, is_occupied, floor_id) VALUES (9, 204, 'BIKE', false, 2);
INSERT INTO parking_slot (id, slot_number, vehicle_type, is_occupied, floor_id) VALUES (10, 205, 'BIKE', false, 2);

-- Insert Initial Pricing Rules (Rate per hour)
INSERT INTO pricing_rule (id, vehicle_type, base_rate, hourly_rate) VALUES (1, 'BIKE', 5.0, 7.0);
INSERT INTO pricing_rule (id, vehicle_type, base_rate, hourly_rate) VALUES (2, 'CAR', 10.0, 15.0);
INSERT INTO pricing_rule (id, vehicle_type, base_rate, hourly_rate) VALUES (3, 'TRUCK', 20.0, 30.0);

-- Insert Admin User
INSERT INTO users (id, email, name, role) VALUES (1L, 'ronakpatel1221@gmail.com', 'Ronak Patel', 'ADMIN');