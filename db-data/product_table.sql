use springdb;

CREATE TABLE product(
 id INT AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(255),
 description VARCHAR(255),
 price DOUBLE
);


INSERT INTO product (name, description, price) VALUES 
('Apple iPhone 14', 'Latest model with 256GB storage A15 Bionic chip, and improved camera system', 999.99),
('Samsung Galaxy S21', 'Flagship phone with 128GB storage, Exynos 2100, and triple camera setup', 799.99),
('Sony HW-1000XM4', 'Wireless noise-cancelling headphones with up to 30 hours of battery life', 3499.99);


******************OneToOne******************
CREATE TABLE address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255)
);

CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    address_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES address(id)
);

INSERT INTO address (street, city, state) VALUES ('123 Main St', 'Cityville', 'CA');

INSERT INTO customer (first_name, last_name, address_id) VALUES ('John', 'Doe', 1);



******************OneToMany******************
ALTER TABLE address
ADD COLUMN customer_id BIGINT,
ADD CONSTRAINT fk_customer
FOREIGN KEY (customer_id)
REFERENCES customer (id);

UPDATE address
SET customer_id = (SELECT id FROM customer WHERE id = address.id);


INSERT INTO customer (first_name, last_name) VALUES ('Jane', 'Smith');

INSERT INTO address (street, city, state, customer_id) VALUES
('789 Pine St', 'CityC', 'SC', 1),
('101 Elm St', 'CityD', 'SC', 3);



ALTER TABLE customer
DROP FOREIGN KEY customer_ibfk_1;

ALTER TABLE customer
DROP COLUMN address_id;


*****************ManytoMany******************
CREATE TABLE customer_address (
    customer_id BIGINT,
    address_id BIGINT,
    PRIMARY KEY (customer_id, address_id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);

INSERT INTO customer_address (customer_id, address_id)
SELECT customer_id, id as address_id
FROM address;

ALTER TABLE address
DROP FOREIGN KEY fk_customer;

ALTER TABLE address 
DROP COLUMN customer_id;


****************AUTH*************
CREATE TABLE custom_user (
 username VARCHAR(50) PRIMARY KEY,
 password VARCHAR(255) NOT NULL
);




