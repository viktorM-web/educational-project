INSERT INTO users (id, email, password, role)
VALUES (1, 'ivan@mail.ru', '{noop}123', 'ADMIN'),
       (2, 'sveta@mail.ru', '{noop}345', 'ADMIN'),
       (3, 'nastya@mail.ru', '{noop}542', 'ADMIN'),
       (4, 'petr@mail.ru', '{noop}456', 'CLIENT');

SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO client_data (id, birthday, date_expiry, driver_experience, driver_licence_no, firstname, lastname, user_id)
VALUES (1, '2000-01-03', '2030-01-01', 3, '123456AB', 'Ivan', 'Ivanov',
        (SELECT id FROM users WHERE email = 'ivan@mail.ru'));

SELECT SETVAL('client_data_id_seq', (SELECT MAX(id) FROM client_data));

INSERT INTO car_category(id, category, day_price)
VALUES (1, 'economy', 40.5),
       (2, 'premium', 60.7),
       (3, 'business', 120.0);

SELECT SETVAL('car_category_id_seq', (SELECT MAX(id) FROM car_category));

INSERT INTO car(id, vin_code, brand, model, year_issue, colour, image, seats_quantity, car_category_id)
VALUES (1, '11111AA', 'Volkswagen', 'Polo', 2020, 'white', 'polo.jpg', 5,
        (SELECT id FROM car_category WHERE category = 'economy')),
       (2, '22222BB', 'Volkswagen', 'Golf', 2021, 'red', 'golf.jpg', 5,
        (SELECT id FROM car_category WHERE category = 'economy')),
       (3, '33333SS', 'Mazda', '3', 2022, 'black', 'mazda3.jpg', 5,
        (SELECT id FROM car_category WHERE category = 'economy')),
       (4, '35689QQ', 'BMW', '530', 2020, 'white', 'bmw530.jpg', 5,
        (SELECT id FROM car_category WHERE category = 'premium')),
       (5, 'QW89564', 'Mercedes', 'E', 2021, 'black', 'mercedesE.jpg', 5,
        (SELECT id FROM car_category WHERE category = 'premium')),
       (6, 'RT48968', 'Audi', 'A5', 2022, 'blue', 'audiA5.jpg', 5,
        (SELECT id FROM car_category WHERE category = 'premium')),
       (7, 'LK25674', 'Mercedes', 'Maybach', 2022, 'black', 'maybach.jpg', 5,
        (SELECT id FROM car_category WHERE category = 'business')),
       (8, 'PO65324', 'Rolls-Royce', 'Ghost', 2021, 'black', 'RR.jpg', 5,
        (SELECT id FROM car_category WHERE category = 'business')),
       (9, 'UY45873', 'Mercedes', 'V', 2020, 'white', 'mercedesV.jpg', 6,
        (SELECT id FROM car_category WHERE category = 'business'));

SELECT SETVAL('car_id_seq', (SELECT MAX(id) FROM car));

INSERT INTO orders(id, start_date_use, expiration_date, status, car_id, user_id)
VALUES (1, '2024-01-01 10:00', '2024-02-01 10:00', 'ACCEPTED',
        (SELECT id FROM car WHERE vin_code = 'UY45873'), (SELECT id FROM users WHERE email = 'ivan@mail.ru')),
       (2, '2023-01-01 10:00', '2023-02-01 10:00', 'ACCEPTED',
        (SELECT id FROM car WHERE vin_code = 'UY45873'), (SELECT id FROM users WHERE email = 'ivan@mail.ru'));

SELECT SETVAL('orders_id_seq', (SELECT MAX(id) FROM orders));


INSERT INTO extra_payment(id, description, price, order_id)
VALUES (1, 'speeding fine 2023.1.4 14:00 50.00$', 50.00,
        (SELECT id FROM orders WHERE start_date_use = '2024-01-01 10:00'));

SELECT SETVAL('extra_payment_id_seq', (SELECT MAX(id) FROM extra_payment));