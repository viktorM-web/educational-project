INSERT INTO users (id, email, password, role)
VALUES (1, 'ivan@mail.ru', 123, 'CLIENT'),
       (2, 'sveta@mail.ru', 345, 'ADMIN'),
       (3, 'nastya@mail.ru', 542, 'ADMIN'),
       (4, 'petr@mail.ru', 456, 'CLIENT');

SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO client_data (birthday, date_expiry, driver_experience, driver_licence_no, firstname, lastname, user_id)
VALUES ('2000-01-03', '2030-01-01', 3, '123456AB', 'Ivan', 'Ivanov',
        (SELECT id FROM users WHERE email = 'ivan@mail.ru'));


INSERT INTO car_category(category, day_price)
VALUES ('economy', 40.5),
       ('premium', 60.7),
       ('business', 120.0);

INSERT INTO car(vin_code, brand, model, year_issue, colour, image, seats_quantity, car_category_id)
VALUES ('11111AA', 'Volkswagen', 'Polo', 2020, 'white', '', 5,
        (SELECT id FROM car_category WHERE category = 'economy')),
       ('22222BB', 'Volkswagen', 'Golf', 2021, 'red', '', 5,
        (SELECT id FROM car_category WHERE category = 'economy')),
       ('33333SS', 'Mazda', '3', 2022, 'black', '', 5,
        (SELECT id FROM car_category WHERE category = 'economy')),
       ('35689QQ', 'BMW', '530', 2020, 'white', '', 5,
        (SELECT id FROM car_category WHERE category = 'premium')),
       ('QW89564', 'Mercedes', 'E', 2021, 'black', '', 5,
        (SELECT id FROM car_category WHERE category = 'premium')),
       ('RT48968', 'Audi', 'A5', 2022, 'blue', '', 5,
        (SELECT id FROM car_category WHERE category = 'premium')),
       ('LK25674', 'Mercedes', 'Maybach', 2022, 'black', '', 5,
        (SELECT id FROM car_category WHERE category = 'business')),
       ('PO65324', 'Rolls-Royce', 'Ghost', 2021, 'black', '', 5,
        (SELECT id FROM car_category WHERE category = 'business')),
       ('UY45873', 'Mercedes', 'V', 2020, 'white', '', 6,
        (SELECT id FROM car_category WHERE category = 'business'));

INSERT INTO orders(start_date_use, expiration_date, status, car_id, user_id)
VALUES ('2024-01-01 10:00', '2024-02-01 10:00', 'ACCEPTED',
        (SELECT id FROM car WHERE vin_code = 'UY45873'), (SELECT id FROM users WHERE email = 'ivan@mail.ru')),
       ('2023-01-01 10:00', '2023-02-01 10:00', 'ACCEPTED',
        (SELECT id FROM car WHERE vin_code = 'UY45873'), (SELECT id FROM users WHERE email = 'ivan@mail.ru'));


INSERT INTO extra_payment(description, price, order_id)
VALUES ('speeding fine 2023.1.4 14:00 50.00$', 50.00,
        (SELECT id FROM orders WHERE start_date_use = '2024-01-01 10:00'));