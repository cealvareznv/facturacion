--Datos a ingresar a la tabla clientes
INSERT INTO clients(name,lastname,docnumber,deleted) VALUES ('JUAN','GALINDEZ','35.895.560',FALSE);
INSERT INTO clients(name,lastname,docnumber,deleted) VALUES ('AQUILES','LINARES','23.235.423',FALSE);
INSERT INTO clients(name,lastname,docnumber,deleted) VALUES ('PEDRO','BAZAN','40.952.650',FALSE);
INSERT INTO clients(name,lastname,docnumber,deleted) VALUES ('EMMANUEL','LOPEZ','58.621.471',FALSE);
INSERT INTO clients(name,lastname,docnumber,deleted) VALUES ('RAFAEL','RODRIGUEZ','93.561.234',FALSE);
INSERT INTO clients(name,lastname,docnumber,deleted) VALUES ('CARLOS','BRITO','96.568.231',FALSE);
INSERT INTO clients(name,lastname,docnumber,deleted) VALUES ('JULIETA','GARCIA','23.512.654',FALSE);
INSERT INTO clients(name,lastname,docnumber,deleted) VALUES ('LAURA','MARTINEZ','81.544.670',FALSE);
INSERT INTO clients(name,lastname,docnumber,deleted) VALUES ('MARIA','VILLAREAL','88.888.891',FALSE);
INSERT INTO clients(name,lastname,docnumber,deleted) VALUES ('FRANCISCA','GORZETTI','81.544.671',FALSE);

--Datos a ingresar a la tabla productos
INSERT INTO products(description,code,stock,price) VALUES ('Mate Acero Termico + Bombilla Inoxidable','K10001',10,8250.00);
INSERT INTO products(description,code,stock,price) VALUES ('Mate Imperial Uruguayo','M20001',10,10080.00);
INSERT INTO products(description,code,stock,price) VALUES ('Mate De Vidrio','M20002',10,2850.00);
INSERT INTO products(description,code,stock,price) VALUES ('Mate Calabaza Y Cuero','M20003',20,2000.00);
INSERT INTO products(description,code,stock,price) VALUES ('Bombilla Acero Inoxidable','B30001',10,2700.00);
INSERT INTO products(description,code,stock,price) VALUES ('Bombilla Alpaca Pico De Loro','B30002',20,2200.00);
INSERT INTO products(description,code,stock,price) VALUES ('Termo Acero Con Manija 1L','T40001',10,10560.00);
INSERT INTO products(description,code,stock,price) VALUES ('Termo Journey 1L Tapon Cebador','T40002',10,15800.00);
INSERT INTO products(description,code,stock,price) VALUES ('Set Matero Kit Completo Termo 1L','K10002',5,12000.00);
INSERT INTO products(description,code,stock,price) VALUES ('Mochila Matera Bolso Porta Notebook','K10003',5,28500.00);
INSERT INTO products(description,code,stock,price) VALUES ('Mate Acero Termico','M20004',20,3400.00);

--Datos a ingresar a la tabla invoice
INSERT INTO invoice(client_id,created_at,total) VALUES (5,TIMESTAMP'2023-02-15 15:23:00',36750.00);
INSERT INTO invoice(client_id,created_at,total) VALUES (7,TIMESTAMP'2023-02-20 08:30:00',12280.00);
INSERT INTO invoice(client_id,created_at,total) VALUES (4,TIMESTAMP'2023-03-13 18:45:00',24000.00);
INSERT INTO invoice(client_id,created_at,total) VALUES (10,TIMESTAMP'2023-03-13 14:30:00',28500.00);

--Datos a ingresar a la tabla invoice_details
INSERT INTO invoice_details(invoice_id,amoun,product_id,price) VALUES (1,1,10,28500.00);
INSERT INTO invoice_details(invoice_id,amoun,product_id,price) VALUES(1,1,5,8250.00);
INSERT INTO invoice_details(invoice_id,amoun,product_id,price) VALUES(2,1,2,10080.00);
INSERT INTO invoice_details(invoice_id,amoun,product_id,price) VALUES(2,1,6,2200.00);
INSERT INTO invoice_details(invoice_id,amoun,product_id,price) VALUES(3,2,9,24000.00);
INSERT INTO invoice_details(invoice_id,amoun,product_id,price) VALUES(4,1,10,28500.00);