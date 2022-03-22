CREATE TABLE products(
    id VARCHAR(36) PRIMARY KEY,
    pID INTEGER NOT NULL,
    average_user_review FLOAT NOT NULL,
    in_stock TEXT NOT NULL,
    ean VARCHAR(20) NOT NULL,
    price REAL NOT NULL,
    published_date VARCHAR(50) NOT NULL,
    expiration_date VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    weight REAL,
    size REAL,
    clockspeed REAL
);

CREATE TABLE brands_and_products(
	product_id VARCHAR(36) REFERENCES products(id), 
	brand_id SERIAL REFERENCES brands(id)
);