--Definición de la tabla clients
CREATE TABLE clients(
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(75) NOT NULL,
  lastname VARCHAR(75) NOT NULL,
  docnumber VARCHAR(11) NOT NULL,
  deleted BIT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT UC_Client UNIQUE(docnumber)
);

--Definición de la tabla invoice
CREATE TABLE invoice(
  id INTEGER NOT NULL AUTO_INCREMENT,
  client_id INTEGER NOT NULL,
  created_at DATETIME NOT NULL,
  total DOUBLE,
  PRIMARY KEY (id),
  CONSTRAINT FK_Clients FOREIGN KEY (client_id)
  REFERENCES clients(id)
);

--Definición de la tabla products
CREATE TABLE products(
  id INTEGER NOT NULL AUTO_INCREMENT,
  description VARCHAR(150) NOT NULL,
  code VARCHAR(50) NOT NULL,
  stock INTEGER NOT NULL,
  price DOUBLE NOT NULL,
  PRIMARY KEY (id)
);

--Definición de la tabla invoice_details
CREATE TABLE invoice_details(
  invoice_detail_id INTEGER NOT NULL AUTO_INCREMENT,
  invoice_id INTEGER NOT NULL,
  amoun INTEGER NOT NULL,
  product_id INTEGER NOT NULL,
  price DOUBLE NOT NULL,
  PRIMARY KEY (invoice_detail_id),
  CONSTRAINT FK_Invoice FOREIGN KEY (invoice_id)
  REFERENCES invoice(id),
  CONSTRAINT FK_Products FOREIGN KEY (product_id)
  REFERENCES products(id)
);