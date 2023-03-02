CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    role     VARCHAR(32)  NOT NULL
);

CREATE TABLE client_data
(
    id                SERIAL PRIMARY KEY,
    user_id           INT REFERENCES users (id) ON DELETE CASCADE NOT NULL UNIQUE,
    firstname         VARCHAR(128)                                NOT NULL,
    lastname          VARCHAR(128)                                NOT NULL,
    birthday          DATE                                        NOT NULL,
    driver_licence_no VARCHAR(128) UNIQUE                         NOT NULL,
    date_expiry       DATE                                        NOT NULL,
    driver_experience INT                                         NOT NULL
);

CREATE TABLE car_category
(
    id        SERIAL PRIMARY KEY,
    category  VARCHAR(32) NOT NULL UNIQUE,
    day_price NUMERIC
);

CREATE TABLE car
(
    id              SERIAL PRIMARY KEY,
    vin_code        VARCHAR(128) UNIQUE                                NOT NULL,
    brand           VARCHAR(128)                                       NOT NULL,
    model           VARCHAR(128)                                       NOT NULL,
    year_issue      INT                                                NOT NULL,
    colour          VARCHAR(128)                                       NOT NULL,
    seats_quantity  INT                                                NOT NULL,
    image           VARCHAR(128)                                       NOT NULL,
    car_category_id INT REFERENCES car_category (id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE orders
(
    id              SERIAL PRIMARY KEY,
    user_id         INT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    car_id          INT REFERENCES car (id) ON DELETE CASCADE   NOT NULL,
    start_date_use  TIMESTAMP                                   NOT NULL,
    expiration_date TIMESTAMP                                   NOT NULL,
    status          VARCHAR(128)                                NOT NULL
);

CREATE TABLE extra_payment
(
    id          SERIAL PRIMARY KEY,
    order_id    INT REFERENCES orders (id) ON DELETE CASCADE UNIQUE NOT NULL,
    description TEXT                                                NOT NULL,
    price       NUMERIC                                             NOT NULL
)