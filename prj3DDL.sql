DROP TABLE IF EXISTS users_with_accounts;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS transaction_descriptions;
<<<<<<< HEAD
DROP TABLE IF EXISTS status_types;
=======
>>>>>>> 61c5aab (skeleton for transactions)
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS account_types;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;


<<<<<<< HEAD

=======
>>>>>>> 61c5aab (skeleton for transactions)
CREATE TABLE roles(
	id SERIAL PRIMARY KEY,
	role_name VARCHAR(30)
);

CREATE TABLE users(
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(30),
<<<<<<< HEAD
	email VARCHAR(30) NOT NULL UNIQUE,
=======
	email VARCHAR(30) NOT NULL,
>>>>>>> 61c5aab (skeleton for transactions)
	pass BYTEA NOT NULL,
	phone VARCHAR(12) NOT NULL,
	role_id INT NOT NULL,
	CONSTRAINT fk_user_roles_id
  		FOREIGN KEY (role_id) REFERENCES "roles" (id)
);

<<<<<<< HEAD

=======
>>>>>>> 61c5aab (skeleton for transactions)
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
	user_id INT NOT NULL,
<<<<<<< HEAD
	PRIMARY KEY (account_id, user_id),
	CONSTRAINT fk_account1_id
  		FOREIGN KEY (account_id) REFERENCES "accounts" (id),
  	CONSTRAINT fk_user_id
  		FOREIGN KEY (user_id) REFERENCES "users" (id)
=======
	PRIMARY KEY (account_id, user_id)
--	CONSTRAINT fk_user_id
--  		FOREIGN KEY (user_id) REFERENCES "users" (id),
--  	CONSTRAINT fk_account_id
--  		FOREIGN KEY (account_id) REFERENCES "accounts" (id)
--	
>>>>>>> 61c5aab (skeleton for transactions)
);

CREATE TABLE transaction_descriptions (
	id SERIAL PRIMARY KEY,
	description VARCHAR(30)
);

<<<<<<< HEAD
CREATE TABLE status_types(
	id SERIAL PRIMARY KEY,
	type_name VARCHAR(30)
);

=======
>>>>>>> 61c5aab (skeleton for transactions)
CREATE TABLE transactions(
	id SERIAL PRIMARY KEY,
	requester_id INT NOT NULL,
	sending_id INT NOT NULL,
	receiving_id INT NOT NULL,
	req_time TIMESTAMP NOT NULL DEFAULT Now(),
	res_time TIMESTAMP,
	approved BOOLEAN,
	amount BIGINT NOT NULL,
<<<<<<< HEAD
	status_id INT NOT NULL,
=======
>>>>>>> 61c5aab (skeleton for transactions)
	desc_id INT NOT NULL,
	CONSTRAINT fk_trx_sending_id
  		FOREIGN KEY (sending_id) REFERENCES "accounts" (id),
  	CONSTRAINT fk_trx_receiving_id
  		FOREIGN KEY (receiving_id) REFERENCES "accounts" (id),
  	CONSTRAINT fk_description_id
<<<<<<< HEAD
  		FOREIGN KEY (desc_id) REFERENCES "transaction_descriptions" (id),
  	CONSTRAINT fk_requester_id
  		FOREIGN KEY (requester_id) REFERENCES "users" (id),
  	CONSTRAINT fk_status_id
  		FOREIGN KEY (status_id) REFERENCES "status_types" (id)
=======
  		FOREIGN KEY (desc_id) REFERENCES "accounts" (id),
  	CONSTRAINT fk_requester_id
  		FOREIGN KEY (requester_id) REFERENCES "users" (id)
>>>>>>> 61c5aab (skeleton for transactions)
);


  ----------------------------------------INSERTS-------------------------------------------------
INSERT INTO roles (role_name) VALUES ('USER'),('EMPLOYEE');

INSERT INTO users (first_name, last_name, email, pass, phone, role_id) VALUES 
	('John', 'Doe', 'jd80@a.ca', 'Password123!', '555-555-5000', 1),
	('Jane', 'Doe', 'jd81@a.ca', 'Password123!', '555-555-5001', 1), 
	('Johny', 'Doe', 'jd05@a.ca', 'Password123!', '555-555-5002', 1),
	('Valentin', 'Vlad', 'vv@a.ca', 'Password123!', '555-555-5555', 1);
	
INSERT INTO account_types (type_name) VALUES ('CHEQUING'), ('SAVINGS');

INSERT INTO accounts (type_id, balance) VALUES 
	(1, 5000),(1, 5000000), (1, 70000),(1, 5000),(1, 70000),(1, 200000),
	(2, 50000),(2, 500000), (2, 50000),(2, 50000),(2, 700000),(2, 2000000);
	
INSERT INTO users_with_accounts (account_id, user_id) VALUES 
	(1,1),(7,1),(2,1), (8,1),(3,1),(9,1),
	(4,2),(5,2),(6,2), (10,2),(11,2),(12,2);

INSERT INTO transaction_descriptions (description) VALUES ('Salary'), ('Payment');

<<<<<<< HEAD
INSERT INTO status_types (type_name) VALUES ('PENDING'), ('APPROVED'), ('DECLINED');

--Insert transfers ---
INSERT INTO transactions (requester_id, sending_id, receiving_id, req_time, status_id, amount, desc_id) VALUES
	(1,2,4,Now(),2,500,2);
=======
--Insert transfers ---
INSERT INTO transactions (requester_id, sending_id, receiving_id, approved ,amount, desc_id) VALUES
	(1,2,4,True,500,2);
>>>>>>> 61c5aab (skeleton for transactions)
	

--Select Jon's accounts -- 
SELECT  act.type_name, a.balance/100 as amount_in_dollars, a.id as acc_id, uwa.user_id 
	FROM account_types act
	JOIN accounts a ON a.type_id = act.id
	JOIN users_with_accounts uwa ON a.id = uwa.account_id
<<<<<<< HEAD
	JOIN users u ON u.id = uwa.user_id
	WHERE u.email = 'jd80@a.ca'; 
=======
	WHERE uwa.user_id = 1; 
>>>>>>> 61c5aab (skeleton for transactions)
