# MySQL 5.7 init script for milkroad e-shop.
DROP SCHEMA IF EXISTS milkroad;
CREATE DATABASE milkroad
  CHARACTER SET 'UTF8';
USE milkroad;

# prefix 'mr' - milkroad, to avoid conflict with reserved keywords

CREATE TABLE mr_user (
  id         BIGINT                       NOT NULL AUTO_INCREMENT,
  user_type  ENUM('ADMIN', 'SIMPLE_USER') NOT NULL DEFAULT 'SIMPLE_USER',
  first_name VARCHAR(45)                  NOT NULL,
  last_name  VARCHAR(45)                  NOT NULL,
  birthday   DATE                         NOT NULL,
  email      VARCHAR(130)                 NOT NULL,
  pass_hash  VARCHAR(32)                  NOT NULL,
  pass_salt  VARCHAR(32)                  NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (email)
)
  ENGINE = INNODB
  CHARACTER SET = UTF8;

CREATE TABLE mr_country (
  id           BIGINT      NOT NULL AUTO_INCREMENT,
  country_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (country_name)
)
  ENGINE = INNODB
  CHARACTER SET = UTF8;

CREATE TABLE mr_city (
  id         BIGINT      NOT NULL AUTO_INCREMENT,
  country_id BIGINT      NOT NULL,
  city_name  VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (country_id) REFERENCES mr_country (id),
  UNIQUE (city_name)
)
  ENGINE = INNODB
  CHARACTER SET = UTF8;

CREATE TABLE mr_address (
  id         BIGINT      NOT NULL AUTO_INCREMENT,
  user_id    BIGINT      NOT NULL,
  country_id BIGINT      NOT NULL,
  city_id    BIGINT      NOT NULL,
  postcode   INT         NOT NULL,
  street     VARCHAR(45) NOT NULL,
  building   VARCHAR(45) NOT NULL,
  apartment  VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES mr_user (id),
  FOREIGN KEY (country_id) REFERENCES mr_country (id),
  FOREIGN KEY (city_id) REFERENCES mr_city (id)
)
  ENGINE = INNODB
  CHARACTER SET = UTF8;

CREATE TABLE mr_product_category (
  id            BIGINT      NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(45) NOT NULL,
  description   TEXT,
  PRIMARY KEY (id),
  UNIQUE (category_name)
)
  ENGINE = INNODB
  CHARACTER SET = UTF8;

# TODO Think about product image
# Product can have no catergory
CREATE TABLE mr_product (
  id            BIGINT         NOT NULL AUTO_INCREMENT,
  seller_id     BIGINT         NOT NULL,
  category_id   BIGINT,
  product_name  VARCHAR(45)    NOT NULL,
  product_price DECIMAL(10, 2) NOT NULL,
  remain_count  INT            NOT NULL,
  description   TEXT,
  PRIMARY KEY (id),
  FOREIGN KEY (seller_id) REFERENCES mr_user (id),
  FOREIGN KEY (category_id) REFERENCES mr_product_category (id)
)
  ENGINE = INNODB
  CHARACTER SET = UTF8;

CREATE TABLE mr_product_attribute (
  id             BIGINT      NOT NULL AUTO_INCREMENT,
  attribute_name VARCHAR(45) NOT NULL,
  description    TEXT,
  PRIMARY KEY (id),
  UNIQUE (attribute_name)
)
  ENGINE = INNODB
  CHARACTER SET = UTF8;

CREATE TABLE mr_product_parameter (
  product_id      BIGINT NOT NULL,
  attribute_id    BIGINT NOT NULL,
  attribute_value VARCHAR(45),
  PRIMARY KEY (product_id, attribute_id),
  FOREIGN KEY (product_id) REFERENCES mr_product (id),
  FOREIGN KEY (attribute_id) REFERENCES mr_product_attribute (id),
  UNIQUE (product_id, attribute_id)
)
  ENGINE = INNODB
  CHARACTER SET = UTF8;

# We need to store delivery address here, because user can have more then one address
CREATE TABLE mr_order (
  id              BIGINT                      NOT NULL AUTO_INCREMENT,
  customer_id     BIGINT                      NOT NULL,
  address_id      BIGINT                      NOT NULL,
  price_total     DECIMAL(10, 2)              NOT NULL,
  payment_method  ENUM('CASH', 'ONLINE')      NOT NULL DEFAULT 'ONLINE',
  shipping_method ENUM('PICKUP', 'POST')      NOT NULL DEFAULT 'POST',
  payment_status  ENUM('AWAITING', 'PAID')    NOT NULL DEFAULT 'AWAITING',
  shipping_status ENUM('AWAITING', 'SHIPPED') NOT NULL DEFAULT 'AWAITING',
  note            TEXT,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES mr_user (id),
  FOREIGN KEY (address_id) REFERENCES mr_address (id)
)
  ENGINE = INNODB
  CHARACTER SET = UTF8;

# We need to store product price here, because:
# - product price can be changed in future
# - where are may be products from different sellers in the order
CREATE TABLE mr_order_detail (
  order_id      BIGINT         NOT NULL,
  product_id    BIGINT         NOT NULL,
  product_count INT            NOT NULL,
  price_total   DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (order_id, product_id),
  FOREIGN KEY (order_id) REFERENCES mr_order (id),
  FOREIGN KEY (product_id) REFERENCES mr_product (id),
  UNIQUE (order_id, product_id)
)
  ENGINE = INNODB
  CHARACTER SET = UTF8;