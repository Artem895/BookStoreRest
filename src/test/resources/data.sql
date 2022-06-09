
--Customers
insert into customer(customer_id,last_name,arial,discount) values (1,'LastName1','Arial1',5.5);
insert into customer(customer_id,last_name,arial,discount) values (2,'LastName2','Arial2',11.5);
insert into customer(customer_id,last_name,arial,discount) values (3,'LastName3','Arial3',7.5);
insert into customer(customer_id,last_name,arial,discount) values (4,'LastName4','Arial4',30);
insert into customer(customer_id,last_name,arial,discount) values (5,'LastName5','Arial4',30);
--Store
insert into store (store_id,logo,store_arial,profit) values (1,'StoreLogo1','Arial1',10);
insert into store (store_id,logo,store_arial,profit) values (2,'StoreLogo2','Arial2',6);
insert into store (store_id,logo,store_arial,profit) values (3,'StoreLogo3','Arial3',15);
insert into store (store_id,logo,store_arial,profit) values (4,'StoreLogo4','Arial4',25);

--Books
insert into books (book_id,book_name,price,anount,in_store) values (1,'BookName1',300.99,20,1);
insert into books (book_id,book_name,price,anount,in_store) values (2,'BookName2',200.99,7,1);
insert into books (book_id,book_name,price,anount,in_store) values (3,'BookName3',409.99,20,2);
insert into books (book_id,book_name,price,anount,in_store) values (4,'BookName4',300.99,5,2);
insert into books (book_id,book_name,price,anount,in_store) values (5,'BookName5',100.99,20,3);
insert into books (book_id,book_name,price,anount,in_store) values (6,'BookName6',200.99,8,3);
insert into books (book_id,book_name,price,anount,in_store) values (7,'BookName7',340.99,30,4);
insert into books (book_id,book_name,price,anount,in_store) values (8,'BookName8',500.99,6,4);
insert into books (book_id,book_name,price,anount,in_store) values (9,'BookName1',500.99,6,4);

insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (1,1,1,6,22200,1,11111,'2022-06-07');
insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (2,2,1,16,7200,1,22222,'2022-08-07');
insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (3,3,2,2,700,2,33333,'2022-09-07');
insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (4,4,3,11,5200,3,44444,'2022-10-07');
insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (5,1,4,10,5000,4,55555,'2022-11-07');
insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (6,2,5,18,8200,2,66666,'2022-12-07');
insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (7,3,6,3,1200,3,77777,'2022-06-07');
insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (8,4,7,4,1400,4,88888,'2022-06-09');
insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (9,1,8,8,3200,1,99999,'2022-06-11');
insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (10,2,2,7,3000,3,123456,'2022-04-17');
insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (11,3,3,2,600,4,123457,'2022-05-17');
insert into purchase (purchase_id,customer,book,amount,total,store,order_number,purchase_date) values (12,4,5,1,100,2,123458,'2022-03-07');