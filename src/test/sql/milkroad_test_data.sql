# This script clears all tables (!!!) in milkroad DB
# and fills them with test data
USE milkroad;

# clear tables
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE mr_address;
TRUNCATE TABLE mr_city;
TRUNCATE TABLE mr_country;
TRUNCATE TABLE mr_order;
TRUNCATE TABLE mr_order_detail;
TRUNCATE TABLE mr_product;
TRUNCATE TABLE mr_product_attribute;
TRUNCATE TABLE mr_product_category;
TRUNCATE TABLE mr_product_parameter;
TRUNCATE TABLE mr_user;
SET FOREIGN_KEY_CHECKS = 1;

# TODO Optimize requests with foreign keys (INSERT ... SELECT)

# fill countries table
INSERT INTO mr_country (country_name)
VALUES
  ('Australia'),
  ('Austria'),
  ('Germany'),
  ('Russian Federation');

# fill cities table
# @formatter:off (for IntelliJIDEA)
INSERT INTO mr_city (country_id, city_name)
VALUES
  ((SELECT id FROM mr_country WHERE country_name = 'Australia'), 'Canberra'),
  ((SELECT id FROM mr_country WHERE country_name = 'Australia'), 'Bendigo'),
  ((SELECT id FROM mr_country WHERE country_name = 'Australia'), 'Perth'),
  ((SELECT id FROM mr_country WHERE country_name = 'Australia'), 'Leonora'),
  ((SELECT id FROM mr_country WHERE country_name = 'Austria'), 'Vienna'),
  ((SELECT id FROM mr_country WHERE country_name = 'Austria'), 'Graz'),
  ((SELECT id FROM mr_country WHERE country_name = 'Austria'), 'Linz'),
  ((SELECT id FROM mr_country WHERE country_name = 'Austria'), 'Enns'),
  ((SELECT id FROM mr_country WHERE country_name = 'Germany'), 'Berlin'),
  ((SELECT id FROM mr_country WHERE country_name = 'Germany'), 'Bamberg'),
  ((SELECT id FROM mr_country WHERE country_name = 'Germany'), 'Hamburg'),
  ((SELECT id FROM mr_country WHERE country_name = 'Germany'), 'Magdala'),
  ((SELECT id FROM mr_country WHERE country_name = 'Russian Federation'), 'Saint Petersburg'),
  ((SELECT id FROM mr_country WHERE country_name = 'Russian Federation'), 'Moskow'),
  ((SELECT id FROM mr_country WHERE country_name = 'Russian Federation'), 'Tula'),
  ((SELECT id FROM mr_country WHERE country_name = 'Russian Federation'), 'Kursk');
# @formatter:on (for IntelliJIDEA)

# fill users table
# @formatter:off (for IntelliJIDEA)
INSERT INTO mr_user (first_name, last_name, birthday, email, pass_hash, pass_salt)
VALUES
  ('Sergey', 'Fedorov', '1993-03-08', 'serezhka@xakep.ru', '12345678123456781234567812345678', '12345678123456781234567812345678'),
  ('Anton', 'Laletin', '1989-02-07', 'anton.laletin@mail.ru', '12345678123456781234567812345678', '12345678123456781234567812345678'),
  ('Alex', 'Petrov', '2000-05-17', 'alex.petrov@mail.ru', '12345678123456781234567812345678', '12345678123456781234567812345678'),
  ('Ivan', 'Smirnov', '1999-12-22', 'ivan.smirnov@mail.ru', '12345678123456781234567812345678', '12345678123456781234567812345678');
# @formatter:on (for IntelliJIDEA)

# fill addresses table
# @formatter:off (for IntelliJIDEA)
INSERT INTO mr_address (user_id, country_id, city_id, postcode, street, building, apartment)
VALUES
  ((SELECT id FROM mr_user WHERE first_name = 'Sergey'),
   (SELECT id FROM mr_country WHERE country_name = 'Russian Federation'),
   (SELECT id FROM mr_city WHERE city_name = 'Saint Petersburg'),
  123456, 'Kondratievsky pr.', '55', '555'),
  ((SELECT id FROM mr_user WHERE first_name = 'Sergey'),
   (SELECT id FROM mr_country WHERE country_name = 'Russian Federation'),
   (SELECT id FROM mr_city WHERE city_name = 'Moskow'),
  123456, 'Moskovskay str.', '44', '444'),
  ((SELECT id FROM mr_user WHERE first_name = 'Anton'),
   (SELECT id FROM mr_country WHERE country_name = 'Australia'),
   (SELECT id FROM mr_city WHERE city_name = 'Leonora'),
  123456, 'First str', '55', '555'),
  ((SELECT id FROM mr_user WHERE first_name = 'Alex'),
   (SELECT id FROM mr_country WHERE country_name = 'Austria'),
   (SELECT id FROM mr_city WHERE city_name = 'Enns'),
  123456, 'Second street', '55', '555'),
  ((SELECT id FROM mr_user WHERE first_name = 'Ivan'),
   (SELECT id FROM mr_country WHERE country_name = 'Germany'),
   (SELECT id FROM mr_city WHERE city_name = 'Berlin'),
  123456, 'Ahtung strasse', '55', '555');
# @formatter:on (for IntelliJIDEA)

# fill product categories table
INSERT INTO mr_product_category (category_name, description)
VALUES
  ('Food', 'Food & dining'),
  ('Media', 'Media & entertainment'),
  ('Sport', 'Sport wear, equipment'),
  ('Health', 'Drugs & healthcare');

# fill product attributes table
INSERT INTO mr_product_attribute (attribute_name, description)
VALUES
  ('weight', 'in kilograms'),
  ('height', 'in meters'),
  ('volume', 'in liters'),
  ('power', 'in watts'),
  ('color', '');

# fill products table
# @formatter:off (for IntelliJIDEA)
INSERT INTO mr_product (seller_id, category_id, product_name, product_price, remain_count, description)
VALUES
  ((SELECT id FROM mr_user WHERE first_name = 'Sergey'),
   (SELECT id FROM mr_product_category WHERE category_name = 'Food'),
  'Pepsi', 10.90, 3, 'Pepsi zero'),
  ((SELECT id FROM mr_user WHERE first_name = 'Sergey'),
   (SELECT id FROM mr_product_category WHERE category_name = 'Food'),
  'Coca cola', 15.90, 2, 'Vanila'),
  ((SELECT id FROM mr_user WHERE first_name = 'Alex'),
   (SELECT id FROM mr_product_category WHERE category_name = 'Sport'),
  'Bike', 100.45, 1, 'BMX');
# @formatter:on (for IntelliJIDEA)

# fill product parameters table
# @formatter:off (for IntelliJIDEA)
INSERT INTO mr_product_parameter (product_id, attribute_id, attribute_value)
VALUES
  ((SELECT id FROM mr_product WHERE product_name = 'Pepsi'),
   (SELECT id FROM mr_product_attribute WHERE attribute_name = 'volume'),
  '0.3L'),
  ((SELECT id FROM mr_product WHERE product_name = 'Coca cola'),
   (SELECT id FROM mr_product_attribute WHERE attribute_name = 'volume'),
  '0.3L'),
  ((SELECT id FROM mr_product WHERE product_name = 'Bike'),
   (SELECT id FROM mr_product_attribute WHERE attribute_name = 'color'),
  'Green'),
  ((SELECT id FROM mr_product WHERE product_name = 'Bike'),
   (SELECT id FROM mr_product_attribute WHERE attribute_name = 'weight'),
  '10Kg');
# @formatter:on (for IntelliJIDEA)

# fill orders table
# @formatter:off (for IntelliJIDEA)
# TODO WTF ?!?!? Commented code breaks down mysql server
# INSERT INTO mr_order (id, customer_id, address_id, price_total, payment_method, shipping_method, payment_status, shipping_status, note)
# VALUES
#   (NULL,
#    (SELECT id FROM mr_user WHERE first_name = 'Anton'),
#    (SELECT mr_address.id FROM mr_address JOIN mr_user ON user_id = mr_user.id WHERE mr_user.first_name = 'Anton' LIMIT 1),
#   2 * 10.90 + 15.90, 'CASH', 'PICKUP', 'AWAITING', 'AWAITING', 'o1'),
#   (NULL,
#    (SELECT id FROM mr_user WHERE first_name = 'Alex'),
#    (SELECT mr_address.id FROM mr_address JOIN mr_user ON user_id = mr_user.id WHERE mr_user.first_name = 'Alex' LIMIT 1),
#   15.90, 'ONLINE', 'PICKUP', 'PAID', 'AWAITING', 'o2'),
#   (NULL,
#    (SELECT id FROM mr_user WHERE first_name = 'Ivan'),
#    (SELECT mr_address.id FROM mr_address JOIN mr_user ON user_id = mr_user.id WHERE mr_user.first_name = 'Ivan' LIMIT 1),
#   10.90, 'ONLINE', 'POST', 'PAID', 'SHIPPED', 'o3'),
#   (NULL,
#    (SELECT id FROM mr_user WHERE first_name = 'Sergey'),
#    (SELECT mr_address.id FROM mr_address JOIN mr_user ON user_id = mr_user.id WHERE mr_user.first_name = 'Sergey' LIMIT 1),
#   111.35, 'CASH', 'PICKUP', 'AWAITING', 'AWAITING', 'o4');
INSERT INTO mr_order (customer_id, address_id, price_total, payment_method, shipping_method, payment_status, shipping_status, note)
VALUES
  ((SELECT id FROM mr_user WHERE first_name = 'Anton'),
   (SELECT mr_address.id FROM mr_address JOIN mr_user ON user_id = mr_user.id WHERE mr_user.first_name = 'Anton' LIMIT 1),
  2 * 10.90 + 15.90, 'CASH', 'PICKUP', 'AWAITING', 'AWAITING', 'o1');
INSERT INTO mr_order (customer_id, address_id, price_total, payment_method, shipping_method, payment_status, shipping_status, note)
VALUES
  ((SELECT id FROM mr_user WHERE first_name = 'Alex'),
   (SELECT mr_address.id FROM mr_address JOIN mr_user ON user_id = mr_user.id WHERE mr_user.first_name = 'Alex' LIMIT 1),
  15.90, 'ONLINE', 'PICKUP', 'PAID', 'AWAITING', 'o2');
INSERT INTO mr_order (customer_id, address_id, price_total, payment_method, shipping_method, payment_status, shipping_status, note)
VALUES
  ((SELECT id FROM mr_user WHERE first_name = 'Ivan'),
   (SELECT mr_address.id FROM mr_address JOIN mr_user ON user_id = mr_user.id WHERE mr_user.first_name = 'Ivan' LIMIT 1),
  10.90, 'ONLINE', 'POST', 'PAID', 'SHIPPED', 'o3');
INSERT INTO mr_order (customer_id, address_id, price_total, payment_method, shipping_method, payment_status, shipping_status, note)
VALUES
   ((SELECT id FROM mr_user WHERE first_name = 'Sergey'),
   (SELECT mr_address.id FROM mr_address JOIN mr_user ON user_id = mr_user.id WHERE mr_user.first_name = 'Sergey' LIMIT 1),
  111.35, 'CASH', 'PICKUP', 'AWAITING', 'AWAITING', 'o4');
# @formatter:on (for IntelliJIDEA)

# fill order details table
# @formatter:off (for IntelliJIDEA)
INSERT INTO mr_order_detail (order_id, product_id, product_count, price_total)
VALUES
  ((SELECT id FROM mr_order WHERE note = 'o1'),
   (SELECT id FROM mr_product WHERE product_name = 'Pepsi'),
  2, (SELECT product_price FROM mr_product WHERE product_name = 'Pepsi')),
  ((SELECT id FROM mr_order WHERE note = 'o1'),
   (SELECT id FROM mr_product WHERE product_name = 'Coca cola'),
  1, (SELECT product_price FROM mr_product WHERE product_name = 'Coca cola')),
  ((SELECT id FROM mr_order WHERE note = 'o2'),
   (SELECT id FROM mr_product WHERE product_name = 'Coca cola'),
  1, (SELECT product_price FROM mr_product WHERE product_name = 'Coca cola')),
  ((SELECT id FROM mr_order WHERE note = 'o3'),
   (SELECT id FROM mr_product WHERE product_name = 'Pepsi'),
  1, (SELECT product_price FROM mr_product WHERE product_name = 'Pepsi')),
  ((SELECT id FROM mr_order WHERE note = 'o4'),
   (SELECT id FROM mr_product WHERE product_name = 'Bike'),
  1, (SELECT product_price FROM mr_product WHERE product_name = 'Bike')),
  ((SELECT id FROM mr_order WHERE note = 'o4'),
   (SELECT id FROM mr_product WHERE product_name = 'Pepsi'),
  1, (SELECT product_price FROM mr_product WHERE product_name = 'Pepsi'));
# @formatter:on (for IntelliJIDEA)
