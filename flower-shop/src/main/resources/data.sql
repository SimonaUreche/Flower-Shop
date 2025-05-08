DROP DATABASE flower_shop;
CREATE DATABASE flower_shop;

INSERT INTO category (id, name) VALUES (1, 'Trandafiri');
INSERT INTO category (id, name) VALUES (2, 'Buchete');
INSERT INTO category (id, name) VALUES (3, 'Aranjamente');

INSERT INTO product (id, name, price, description, image, category_id, stock)
VALUES
    (1, 'Trandafiri roșii', 25.0, 'Trandafiri roșii proaspeți, ideali pentru ocazii speciale.', 'images/1.jpg', 1, 50),
    (2, 'Buchet mixt de primăvară', 120.0, 'Buchet colorat cu lalele, frezii și narcise.', 'images/2.jpg', 2, 20),
    (3, 'Aranjament în cutie', 150.0, 'Aranjament elegant cu flori variate în cutie rotundă.', 'images/3.jpg', 3, 10),
    (4, 'Trandafiri albi', 30.0, 'Trandafiri albi puri, simbolul eleganței.', 'images/4.jpg', 1, 25),
    (5, 'Buchet de lalele', 90.0, 'Buchet simplu și proaspăt de lalele galbene.', 'images/5.jpg', 2, 35);
