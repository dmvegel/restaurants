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

INSERT INTO menu (menu_date, restaurant_id)
VALUES ('2026-01-07', 1),
       ('2026-01-08', 1),
       ('2026-01-07', 2),
       ('2026-01-07', 3),
       ('2026-01-07', 4),
       (CURRENT_DATE, 1),
       (CURRENT_DATE, 2);

INSERT INTO dish (name, fraction_price, currency)
VALUES ('Pasta Carbonara', 85000, 'RUB'),
       ('Pizza Margherita', 75000, 'RUB'),
       ('Juice', 25000, 'RUB'),
       ('Salmon Sushi', 120000, 'RUB'),
       ('Tuna Roll', 95000, 'RUB'),
       ('Cheeseburger', 65000, 'RUB'),
       ('French Fries', 30000, 'RUB'),
       ('Dish', 30000, 'RUB'),
       ('Another Dish', 30000, 'RUB');

INSERT INTO menu_dishes (menu_id, dishes_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 4),
       (3, 5),
       (4, 6),
       (4, 7),
       (5, 8),
       (6, 9);

INSERT INTO vote (user_id, restaurant_id, vote_date)
VALUES (1, 1, '2026-01-07'),
       (2, 2, '2026-01-07');
