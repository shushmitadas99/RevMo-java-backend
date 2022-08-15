DROP TABLE IF EXISTS users_with_accounts;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS transaction_descriptions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS account_types;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;


CREATE TABLE roles(
	id SERIAL PRIMARY KEY,
	role_name VARCHAR(30)
);

CREATE TABLE users(
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(30),
	email VARCHAR(30) NOT NULL,
	pass VARCHAR(60) NOT NULL,
	phone VARCHAR(12) NOT NULL,
	role_id INT NOT NULL,
	CONSTRAINT fk_user_roles_id
  		FOREIGN KEY (role_id) REFERENCES "roles" (id)
);

CREATE TABLE account_types (
	id SERIAL PRIMARY KEY,
	type_name VARCHAR(30)
);

CREATE TABLE accounts (
	id SERIAL PRIMARY KEY,
	type_id INT NOT NULL,
	balance BIGINT NOT NULL,
	CONSTRAINT fk_account_type_id
  		FOREIGN KEY (type_id) REFERENCES "account_types" (id)
);

CREATE TABLE users_with_accounts(
	account_id INT NOT NULL,
	customer_id INT NOT NULL,
	PRIMARY KEY (account_id, customer_id)
);

CREATE TABLE transaction_descriptions (
	id SERIAL PRIMARY KEY,
	description VARCHAR(30)
);

CREATE TABLE transactions(
	id SERIAL PRIMARY KEY,
	sending_id INT NOT NULL,
	receiving_id INT NOT NULL,
	req_time TIMESTAMP NOT NULL DEFAULT Now(),
	res_time TIMESTAMP,
	approved BOOLEAN,
	desc_id INT NOT NULL,
	CONSTRAINT fk_trx_sending_id
  		FOREIGN KEY (sending_id) REFERENCES "accounts" (id),
  	CONSTRAINT fk_trx_receiving_id
  		FOREIGN KEY (receiving_id) REFERENCES "accounts" (id),
  	CONSTRAINT fk_description_id
  		FOREIGN KEY (desc_id) REFERENCES "accounts" (id)
	
);


