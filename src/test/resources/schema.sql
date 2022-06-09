CREATE TABLE IF NOT EXISTS customer
(
    customer_id integer NOT NULL auto_increment,
    last_name text NOT NULL default '',
    arial text NOT NULL default '',
    discount double precision,
    PRIMARY KEY (customer_id)
    );
CREATE TABLE IF NOT EXISTS store
(
    store_id integer NOT NULL auto_increment,
    logo text NOT NULL default '',
    store_arial text NOT NULL default '',
    profit double precision,
    PRIMARY KEY (store_id)
    );


CREATE TABLE IF NOT EXISTS books
(
    book_id integer NOT NULL auto_increment,
    book_name text NOT NULL default '',
    price double precision,
    anount integer,
    in_store integer,
    PRIMARY KEY (book_id),
    FOREIGN KEY (in_store) REFERENCES store (store_id)
    );

CREATE TABLE IF NOT EXISTS purchase
(
    purchase_id integer not null auto_increment ,
    customer integer NOT NULL,
    book integer NOT NULL,
    amount integer NOT NULL,
    total double precision,
    store integer,
    order_number bigint,
    purchase_date date,
    PRIMARY KEY (purchase_id),
    FOREIGN KEY (book) REFERENCES books (book_id) ,
    FOREIGN KEY (customer) REFERENCES customer (customer_id),
    FOREIGN KEY (store) REFERENCES store (store_id)
    );