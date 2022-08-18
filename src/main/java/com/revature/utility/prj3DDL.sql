DROP TABLE IF EXISTS users_with_accounts;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS transaction_descriptions;
DROP TABLE IF EXISTS status_types;
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
	email VARCHAR(30) NOT NULL UNIQUE,
	pass BYTEA NOT NULL,
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
	main BOOLEAN NOT NULL,
	CONSTRAINT fk_account_type_id
  		FOREIGN KEY (type_id) REFERENCES "account_types" (id)
);

CREATE TABLE users_with_accounts(
	account_id INT NOT NULL,
	user_id INT NOT NULL,
	PRIMARY KEY (account_id, user_id),
	CONSTRAINT fk_account1_id
  		FOREIGN KEY (account_id) REFERENCES "accounts" (id),
  	CONSTRAINT fk_user_id
  		FOREIGN KEY (user_id) REFERENCES "users" (id)
);

CREATE TABLE transaction_descriptions (
	id SERIAL PRIMARY KEY,
	description VARCHAR(30)
);

CREATE TABLE status_types(
	id SERIAL PRIMARY KEY,
	type_name VARCHAR(30)
);

CREATE TABLE transactions(
	id SERIAL PRIMARY KEY,
	requester_id INT NOT NULL,
	sending_id INT NOT NULL,
	receiving_id INT NOT NULL,
	req_time TIMESTAMP NOT NULL DEFAULT Now(),
	res_time TIMESTAMP,
	approved BOOLEAN,
	amount BIGINT NOT NULL,
	status_id INT NOT NULL,
	desc_id INT NOT NULL,
	receiving_email VARCHAR(30) NOT NULL,
    CONSTRAINT fk_trx_email
    	FOREIGN KEY (receiving_email) REFERENCES "users" (email),
	CONSTRAINT fk_trx_sending_id
  		FOREIGN KEY (sending_id) REFERENCES "accounts" (id),
  	CONSTRAINT fk_trx_receiving_id
  		FOREIGN KEY (receiving_id) REFERENCES "accounts" (id),
  	CONSTRAINT fk_description_id
  		FOREIGN KEY (desc_id) REFERENCES "transaction_descriptions" (id),
  	CONSTRAINT fk_requester_id
  		FOREIGN KEY (requester_id) REFERENCES "users" (id),
  	CONSTRAINT fk_status_id
  		FOREIGN KEY (status_id) REFERENCES "status_types" (id)
);


  ----------------------------------------INSERTS-------------------------------------------------
INSERT INTO roles (role_name) VALUES ('USER'),('EMPLOYEE'),('ATM');

INSERT INTO users (first_name, last_name, email, pass, phone, role_id) VALUES
	('John', 'Doe', 'jd80@a.ca', 'Password123!', '555-555-5000', 1),
	('Jane', 'Doe', 'jd81@a.ca', 'Password123!', '555-555-5001', 1),
	('Johny', 'Doe', 'jd05@a.ca', 'Password123!', '555-555-5002', 1),
	('Valentin', 'Vlad', 'vv@a.ca', 'Password123!', '555-555-5555', 1);

INSERT INTO users (id,first_name, last_name, email, pass, phone, role_id) VALUES
	(999999,'ATM', '001', 'rvm001@a.ca', '-----------------', '555-555-5555', 3),
	(888888,'ATM', '002', 'rvm002@a.ca', '-----------------', '555-555-5555', 3);


INSERT INTO account_types (type_name) VALUES ('CHEQUING'), ('SAVINGS');

INSERT INTO accounts (type_id, balance,main) VALUES
	(1, 5000, True),(1, 5000000, False), (1, 70000, False),(1, 5000, False),(1, 70000, False),(1, 200000, False),
	(2, 50000, True),(2, 500000, False), (2, 50000, False),(2, 50000, False),(2, 700000, False),(2, 2000000, False),
	(1, 50000, True),(2, 500000, False), (1, 50000, False),(2, 50000, False),(1, 700000, False),(2, 2000000, False);

INSERT INTO users_with_accounts (account_id, user_id) VALUES
	(1,1),(7,1),(2,1), (8,1),(3,1),(9,1),
	(4,2),(5,2),(6,2), (10,2),(11,2),(12,2),
	(13,3),(14,3),(15,3), (16,3),(17,3),(18,3);

INSERT INTO transaction_descriptions (description) VALUES ('Salary'), ('Payment'),('Refund');

INSERT INTO status_types (type_name) VALUES ('PENDING'), ('APPROVED'), ('DECLINED');

--Insert transfers ---
INSERT INTO transactions (requester_id, sending_id, receiving_id, req_time, status_id, amount, desc_id, receiving_email) VALUES
	(1,2,4,Now(),2,500, 2, 'jd80@a.ca'),(2,7,5,Now(),1,500, 2, 'jd81@a.ca'),
	(3,2,13,Now(),2,500, 2, 'jd05@a.ca'),(2,7,5,Now(),1,500, 1, 'jd81@a.ca');

SELECT * FROM transactions;

--Select Jon's accounts --
SELECT  act.type_name, a.type_id, a.balance/100 as amount_in_dollars, a.id as acc_id, uwa.user_id
	FROM account_types act
	JOIN accounts a ON a.type_id = act.id
	JOIN users_with_accounts uwa ON a.id = uwa.account_id
	JOIN users u ON u.id = uwa.user_id
	WHERE u.email = 'jd80@a.ca';

---select getAll trx
SELECT t.id, t.requester_id, t.sending_id, t.receiving_id, t.req_time, t.res_time, t.approved, t.amount, t.receiving_email,  concat_ws(' ', u.first_name,u.last_name) as initiated_by, st.type_name, td.description
	FROM transactions t
	JOIN users u ON t.requester_id = u.id
	JOIN status_types st ON t.status_id  = st.id
	JOIN transaction_descriptions td ON t.desc_id  = td.id;

SELECT a.*, at2.type_name
	FROM accounts a
	Join account_types at2 ON a.type_id = at2.id
	WHERE a.id = 2;



