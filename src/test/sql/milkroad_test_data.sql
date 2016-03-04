# This script clears all tables (!!!) in milkroad DB
# and fills them with test data
USE milkroad;

# clear tables
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE address;
# TRUNCATE TABLE city;
# TRUNCATE TABLE country;
TRUNCATE TABLE `order`;
TRUNCATE TABLE order_detail;
TRUNCATE TABLE product;
TRUNCATE TABLE product_attribute;
TRUNCATE TABLE product_category;
TRUNCATE TABLE product_parameter;
TRUNCATE TABLE user;
SET FOREIGN_KEY_CHECKS = 1;

# TODO Optimize requests with foreign keys (INSERT ... SELECT)

# fill countries table
# INSERT INTO country (country_name)
# VALUES
#   ('Australia'),
#   ('Austria'),
#   ('Germany'),
#   ('Russian Federation');

# fill cities table
# @formatter:off (for IntelliJIDEA)
# INSERT INTO city (country_id, city_name)
# VALUES
#   ((SELECT id FROM country WHERE country_name = 'Australia'), 'Canberra'),
#   ((SELECT id FROM country WHERE country_name = 'Australia'), 'Bendigo'),
#   ((SELECT id FROM country WHERE country_name = 'Australia'), 'Perth'),
#   ((SELECT id FROM country WHERE country_name = 'Australia'), 'Leonora'),
#   ((SELECT id FROM country WHERE country_name = 'Austria'), 'Vienna'),
#   ((SELECT id FROM country WHERE country_name = 'Austria'), 'Graz'),
#   ((SELECT id FROM country WHERE country_name = 'Austria'), 'Linz'),
#   ((SELECT id FROM country WHERE country_name = 'Austria'), 'Enns'),
#   ((SELECT id FROM country WHERE country_name = 'Germany'), 'Berlin'),
#   ((SELECT id FROM country WHERE country_name = 'Germany'), 'Bamberg'),
#   ((SELECT id FROM country WHERE country_name = 'Germany'), 'Hamburg'),
#   ((SELECT id FROM country WHERE country_name = 'Germany'), 'Magdala'),
#   ((SELECT id FROM country WHERE country_name = 'Russian Federation'), 'Saint Petersburg'),
#   ((SELECT id FROM country WHERE country_name = 'Russian Federation'), 'Moskow'),
#   ((SELECT id FROM country WHERE country_name = 'Russian Federation'), 'Tula'),
#   ((SELECT id FROM country WHERE country_name = 'Russian Federation'), 'Kursk');
# @formatter:on (for IntelliJIDEA)

# fill users table
# @formatter:off (for IntelliJIDEA)
INSERT INTO user (user_type, first_name, last_name, birthday, email, pass_hash, pass_salt)
VALUES
  ('ADMIN', 'Admin', 'Admin', '1993-03-08', 'admin@mail.ru', '1c76c47e1e9de8f8fe07b353bde4fe47', '3241530acd90b347ee79c5596746fcb4');

INSERT INTO user (first_name, last_name, birthday, email, pass_hash, pass_salt)
VALUES
  ('Sergey', 'Fedorov', '1993-03-08', 'user@mail.ru', '1c76c47e1e9de8f8fe07b353bde4fe47', '3241530acd90b347ee79c5596746fcb4'),
  ('Vasya', 'Ivanov', '1993-03-08', 'serezhka@xakep.ru', '12345678123456781234567812345678', '12345678123456781234567812345678'),
  ('Anton', 'Laletin', '1989-02-07', 'anton.laletin@mail.ru', '12345678123456781234567812345678', '12345678123456781234567812345678'),
  ('Alex', 'Petrov', '2000-05-17', 'alex.petrov@mail.ru', '12345678123456781234567812345678', '12345678123456781234567812345678'),
  ('Ivan', 'Smirnov', '1999-12-22', 'ivan.smirnov@mail.ru', '12345678123456781234567812345678', '12345678123456781234567812345678');
# @formatter:on (for IntelliJIDEA)

# fill addresses table
# @formatter:off (for IntelliJIDEA)
INSERT INTO address (user_id, country_id, city_id, postcode, street, building, apartment)
VALUES
  ((SELECT id FROM user WHERE first_name = 'Sergey'),
#    (SELECT id FROM country WHERE country_name = 'Russian Federation'),
#    (SELECT id FROM city WHERE city_name = 'Saint Petersburg'),
      'Russian Federation', 'Saint Petersburg',
  123456, 'Kondratievsky pr.', '55', '555'),
  ((SELECT id FROM user WHERE first_name = 'Sergey'),
#    (SELECT id FROM country WHERE country_name = 'Russian Federation'),
#    (SELECT id FROM city WHERE city_name = 'Moskow'),
      'Russian Federation', 'Moscow',
  123456, 'Moskovskay str.', '44', '444'),
  ((SELECT id FROM user WHERE first_name = 'Anton'),
#    (SELECT id FROM country WHERE country_name = 'Australia'),
#    (SELECT id FROM city WHERE city_name = 'Leonora'),
      'Australia', 'Leonora',
  123456, 'First str', '55', '555'),
  ((SELECT id FROM user WHERE first_name = 'Alex'),
#    (SELECT id FROM country WHERE country_name = 'Austria'),
#    (SELECT id FROM city WHERE city_name = 'Enns'),
      'Austria', 'Enns',
  123456, 'Second street', '55', '555'),
  ((SELECT id FROM user WHERE first_name = 'Ivan'),
#    (SELECT id FROM country WHERE country_name = 'Germany'),
#    (SELECT id FROM city WHERE city_name = 'Berlin'),
      'Germany', 'Berlin',
  123456, 'Ahtung strasse', '55', '555');
# @formatter:on (for IntelliJIDEA)

# fill product categories table
INSERT INTO product_category (category_name, description)
VALUES
  ('Food', 'Food & dining'),
  ('Media', 'Media & entertainment'),
  ('Sport', 'Sport wear, equipment'),
  ('Health', 'Drugs & healthcare');

# fill product attributes table
INSERT INTO product_attribute (attribute_name, description)
VALUES
  ('weight', 'in kilograms'),
  ('height', 'in meters'),
  ('volume', 'in liters'),
  ('power', 'in watts'),
  ('color', '');

# fill products table
# @formatter:off (for IntelliJIDEA)
INSERT INTO product (seller_id, category_id, product_name, product_price, remain_count, description)
VALUES
  ((SELECT id FROM user WHERE first_name = 'Sergey'),
   (SELECT id FROM product_category WHERE category_name = 'Food'),
  'Pepsi', 10.90, 3, NULL),
  ((SELECT id FROM user WHERE first_name = 'Sergey'),
   (SELECT id FROM product_category WHERE category_name = 'Food'),
  'Coca cola', 15.90, 2, 'Vanila'),
  ((SELECT id FROM user WHERE first_name = 'Ivan'),
   (SELECT id FROM product_category WHERE category_name = 'Food'),
  'Sprite', 13.45, 7, 'Caffeine free'),
  ((SELECT id FROM user WHERE first_name = 'Ivan'),
   (SELECT id FROM product_category WHERE category_name = 'Food'),
  'Lays', 7.45, 7, 'Classic'),
  ((SELECT id FROM user WHERE first_name = 'Ivan'),
   (SELECT id FROM product_category WHERE category_name = 'Food'),
  'Lays', 7.45, 7, 'Sour cream & Onion'),
  ((SELECT id FROM user WHERE first_name = 'Ivan'),
   (SELECT id FROM product_category WHERE category_name = 'Health'),
  'Bandage', 1.90, 7, 'Simple bandage'),
  ((SELECT id FROM user WHERE first_name = 'Ivan'),
   (SELECT id FROM product_category WHERE category_name = 'Health'),
  'Bandage', 5.90, 7, 'Wide bandage'),
  ((SELECT id FROM user WHERE first_name = 'Alex'),
   (SELECT id FROM product_category WHERE category_name = 'Sport'),
  'Helmet', 50.45, 1, NULL),
  ((SELECT id FROM user WHERE first_name = 'Alex'),
   (SELECT id FROM product_category WHERE category_name = 'Sport'),
  'Bike', 100.45, 1, 'BMX'),
  ((SELECT id FROM user WHERE first_name = 'Alex'),
   (SELECT id FROM product_category WHERE category_name = 'Media'),
  'Deadpool', 20.45, 1, 'Deadpool movie');
# @formatter:on (for IntelliJIDEA)

# fill product parameters table
# @formatter:off (for IntelliJIDEA)
INSERT INTO product_parameter (product_id, attribute_id, attribute_value)
VALUES
  ((SELECT id FROM product WHERE product_name = 'Pepsi'),
   (SELECT id FROM product_attribute WHERE attribute_name = 'volume'),
  '0.3L'),
  ((SELECT id FROM product WHERE product_name = 'Pepsi'),
   (SELECT id FROM product_attribute WHERE attribute_name = 'color'),
  'Blue'),
  ((SELECT id FROM product WHERE product_name = 'Sprite'),
   (SELECT id FROM product_attribute WHERE attribute_name = 'volume'),
  '0.3L'),
  ((SELECT id FROM product WHERE product_name = 'Sprite'),
   (SELECT id FROM product_attribute WHERE attribute_name = 'color'),
  'Green'),
  ((SELECT id FROM product WHERE product_name = 'Coca cola'),
   (SELECT id FROM product_attribute WHERE attribute_name = 'volume'),
  '0.3L'),
  ((SELECT id FROM product WHERE product_name = 'Coca cola'),
   (SELECT id FROM product_attribute WHERE attribute_name = 'color'),
  'Red'),
  ((SELECT id FROM product WHERE product_name = 'Bike'),
   (SELECT id FROM product_attribute WHERE attribute_name = 'color'),
  'Blue'),
  ((SELECT id FROM product WHERE product_name = 'Bike'),
   (SELECT id FROM product_attribute WHERE attribute_name = 'weight'),
  '10Kg'),
  ((SELECT id FROM product WHERE product_name = 'Bandage' AND description = 'Simple bandage'),
   (SELECT id FROM product_attribute WHERE attribute_name = 'color'),
  'White'),
  ((SELECT id FROM product WHERE product_name = 'Bandage' AND description = 'Wide bandage'),
   (SELECT id FROM product_attribute WHERE attribute_name = 'color'),
  'White'),
  ((SELECT id FROM product WHERE product_name = 'Helmet'),
   (SELECT id FROM product_attribute WHERE attribute_name = 'color'),
  'Black');
# @formatter:on (for IntelliJIDEA)

# fill orders table
# @formatter:off (for IntelliJIDEA)
# TODO WTF ?!?!? Commented code breaks down mysql server
# INSERT INTO `order` (id, customer_id, address_id, price_total, payment_method, shipping_method, payment_status, shipping_status, note)
# VALUES
#   (NULL,
#    (SELECT id FROM user WHERE first_name = 'Anton'),
#    (SELECT address.id FROM address JOIN user ON user_id = user.id WHERE user.first_name = 'Anton' LIMIT 1),
#   2 * 10.90 + 15.90, 'CASH', 'PICKUP', 'AWAITING', 'AWAITING', 'o1'),
#   (NULL,
#    (SELECT id FROM user WHERE first_name = 'Alex'),
#    (SELECT address.id FROM address JOIN user ON user_id = user.id WHERE user.first_name = 'Alex' LIMIT 1),
#   15.90, 'ONLINE', 'PICKUP', 'PAID', 'AWAITING', 'o2'),
#   (NULL,
#    (SELECT id FROM user WHERE first_name = 'Ivan'),
#    (SELECT address.id FROM address JOIN user ON user_id = user.id WHERE user.first_name = 'Ivan' LIMIT 1),
#   10.90, 'ONLINE', 'POST', 'PAID', 'SHIPPED', 'o3'),
#   (NULL,
#    (SELECT id FROM user WHERE first_name = 'Sergey'),
#    (SELECT address.id FROM address JOIN user ON user_id = user.id WHERE user.first_name = 'Sergey' LIMIT 1),
#   111.35, 'CASH', 'PICKUP', 'AWAITING', 'AWAITING', 'o4');
INSERT INTO `order` (customer_id, address_id, price_total, payment_method, shipping_method, payment_status, shipping_status, note)
VALUES
  ((SELECT id FROM user WHERE first_name = 'Anton'),
   (SELECT address.id FROM address JOIN user ON user_id = user.id WHERE user.first_name = 'Anton' LIMIT 1),
  2 * 10.90 + 15.90, 'CASH', 'PICKUP', 'AWAITING', 'AWAITING', 'o1');
INSERT INTO `order` (customer_id, address_id, price_total, payment_method, shipping_method, payment_status, shipping_status, note)
VALUES
  ((SELECT id FROM user WHERE first_name = 'Alex'),
   (SELECT address.id FROM address JOIN user ON user_id = user.id WHERE user.first_name = 'Alex' LIMIT 1),
  15.90, 'ONLINE', 'PICKUP', 'PAID', 'AWAITING', 'o2');
INSERT INTO `order` (customer_id, address_id, price_total, payment_method, shipping_method, payment_status, shipping_status, note)
VALUES
  ((SELECT id FROM user WHERE first_name = 'Ivan'),
   (SELECT address.id FROM address JOIN user ON user_id = user.id WHERE user.first_name = 'Ivan' LIMIT 1),
  10.90, 'ONLINE', 'POST', 'PAID', 'SHIPPED', 'o3');
INSERT INTO `order` (customer_id, address_id, price_total, payment_method, shipping_method, payment_status, shipping_status, note)
VALUES
   ((SELECT id FROM user WHERE first_name = 'Sergey'),
   (SELECT address.id FROM address JOIN user ON user_id = user.id WHERE user.first_name = 'Sergey' LIMIT 1),
  111.35, 'CASH', 'PICKUP', 'AWAITING', 'AWAITING', 'o4');
# @formatter:on (for IntelliJIDEA)

# fill order details table
# @formatter:off (for IntelliJIDEA)
INSERT INTO order_detail (order_id, product_id, product_count, price_total)
VALUES
  ((SELECT id FROM `order` WHERE note = 'o1'),
   (SELECT id FROM product WHERE product_name = 'Pepsi'),
  2, (SELECT product_price FROM product WHERE product_name = 'Pepsi')),
  ((SELECT id FROM `order` WHERE note = 'o1'),
   (SELECT id FROM product WHERE product_name = 'Coca cola'),
  1, (SELECT product_price FROM product WHERE product_name = 'Coca cola')),
  ((SELECT id FROM `order` WHERE note = 'o2'),
   (SELECT id FROM product WHERE product_name = 'Coca cola'),
  1, (SELECT product_price FROM product WHERE product_name = 'Coca cola')),
  ((SELECT id FROM `order` WHERE note = 'o3'),
   (SELECT id FROM product WHERE product_name = 'Pepsi'),
  1, (SELECT product_price FROM product WHERE product_name = 'Pepsi')),
  ((SELECT id FROM `order` WHERE note = 'o4'),
   (SELECT id FROM product WHERE product_name = 'Bike'),
  1, (SELECT product_price FROM product WHERE product_name = 'Bike')),
  ((SELECT id FROM `order` WHERE note = 'o4'),
   (SELECT id FROM product WHERE product_name = 'Pepsi'),
  1, (SELECT product_price FROM product WHERE product_name = 'Pepsi'));
# @formatter:on (for IntelliJIDEA)
