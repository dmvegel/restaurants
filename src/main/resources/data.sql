INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (name)
VALUES ('Italian Bistro'),
       ('Sushi Place'),
       ('Burger House');

INSERT INTO dish (name, fraction_price, currency)
VALUES ('Pasta Carbonara', 850, 'RUB'),
       ('Pizza Margherita', 750, 'RUB'),
       ('Salmon Sushi', 1200, 'RUB'),
       ('Tuna Roll', 950, 'RUB'),
       ('Cheeseburger', 650, 'RUB'),
       ('French Fries', 300, 'RUB');

INSERT INTO menu (date, restaurant_id)
VALUES (CURRENT_DATE, 1),
       (CURRENT_DATE, 2),
       (CURRENT_DATE, 3);

INSERT INTO menu_dishes (menu_id, dishes_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6);

INSERT INTO vote (user_id, restaurant_id, date)
VALUES (1, 1, '2025-01-01'),
       (2, 2, '2025-01-01'),
       (3, 3, '2025-01-01');

