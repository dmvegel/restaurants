DELETE
FROM user_role;
DELETE
FROM users;
DELETE
FROM vote;
DELETE
FROM dish;
DELETE
FROM menu;
DELETE
FROM restaurant;

ALTER TABLE restaurant
    ALTER COLUMN id RESTART WITH 1;
ALTER TABLE users
    ALTER COLUMN id RESTART WITH 1;
ALTER TABLE menu
    ALTER COLUMN id RESTART WITH 1;
ALTER TABLE dish
    ALTER COLUMN id RESTART WITH 1;
ALTER TABLE vote
    ALTER COLUMN id RESTART WITH 1;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (name, enabled)
VALUES ('Italian Bistro', true),
       ('Sushi Place', true),
       ('Burger House', true),
       ('Disabled Restaurant', false);

INSERT INTO menu (menu_date, restaurant_id, enabled)
VALUES ('2026-01-07', 1, true),
       ('2026-01-08', 1, true),
       ('2026-01-07', 2, true),
       ('2026-01-07', 3, true),
       ('2026-01-07', 4, false),
       (CURRENT_DATE, 1, true),
       (CURRENT_DATE, 2, true);

INSERT INTO dish (name, fraction_price, currency, menu_id)
VALUES ('Pasta Carbonara', 85000, 'RUB', 1),
       ('Pizza Margherita', 75000, 'RUB', 1),
       ('Juice', 25000, 'RUB', 2),
       ('Salmon Sushi', 120000, 'RUB', 3),
       ('Tuna Roll', 95000, 'RUB', 3),
       ('Cheeseburger', 65000, 'RUB', 4),
       ('French Fries', 30000, 'RUB', 4),
       ('Dish', 30000, 'RUB', 5),
       ('Another Dish', 30000, 'RUB', 6);

INSERT INTO vote (user_id, restaurant_id, vote_date)
VALUES (1, 1, '2026-01-07'),
       (2, 2, '2026-01-07'),
       (1, 1, '2026-01-08');
