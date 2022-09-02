/*
CREATE TABLE Products (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                price float
);

 */
truncate table Products;

INSERT INTO Products(`id`, `name`, `price`)
values (1, 'User no 1',1),
       (2, 'User no 2', 2),
       (3, 'User no 3', 3);