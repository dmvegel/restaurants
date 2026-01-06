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

INSERT INTO menu (date, restaurant_id)
VALUES (CURRENT_DATE, 1),
       (CURRENT_DATE, 2),
       (CURRENT_DATE, 3);

INSERT INTO dish (name, fraction_price, currency, menu_id)
VALUES ('Pasta Carbonara', 85000, 'RUB', 1),
       ('Pizza Margherita', 75000, 'RUB', 1),
       ('Salmon Sushi', 120000, 'RUB', 2),
       ('Tuna Roll', 95000, 'RUB', 2),
       ('Cheeseburger', 65000, 'RUB', 3),
       ('French Fries', 30000, 'RUB', 3);

INSERT INTO vote (user_id, restaurant_id, date)
VALUES (1, 1, '2025-01-01'),
       (2, 2, '2025-01-01'),
       (3, 3, '2025-01-01');
