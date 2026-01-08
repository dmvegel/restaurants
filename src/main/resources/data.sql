INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (name, enabled)
VALUES ('Italian Bistro', true),
       ('Sushi Place', true),
       ('Burger House', true),
       ('Disabled Restaurant', false);

INSERT INTO menu (date, restaurant_id)
VALUES ('2026-01-07', 1),
       ('2026-01-08', 1),
       ('2026-01-07', 2),
       ('2026-01-07', 3),
       ('2026-01-07', 4);

INSERT INTO dish (name, fraction_price, currency, menu_id)
VALUES ('Pasta Carbonara', 85000, 'RUB', 1),
       ('Pizza Margherita', 75000, 'RUB', 1),
       ('Juice', 25000, 'RUB',2),
       ('Salmon Sushi', 120000, 'RUB', 3),
       ('Tuna Roll', 95000, 'RUB', 3),
       ('Cheeseburger', 65000, 'RUB', 4),
       ('French Fries', 30000, 'RUB', 4),
       ('Dish', 30000, 'RUB', 5);

INSERT INTO vote (user_id, restaurant_id, date)
VALUES (1, 1, '2026-01-07'),
       (2, 2, '2026-01-07');
