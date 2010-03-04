-- ============================================================
-- tables for janey project
-- ============================================================
-- Copyright 2007, Wade Girard, Brian Bezanson
-- Last updated: March 10, 2007
--
-- First create the database 
-- >>> createdb janey;
--
-- then load the common tables, then
-- use the following command to load these tables
--
-- psql -d janey -f pgsql.janey.tables.sql
--

-- ============================================================
-- Table: product
-- ============================================================
CREATE TABLE product
(
	product_id		VARCHAR(15)		NOT NULL,
	company_id		VARCHAR(15)		NOT NULL,
	owner			VARCHAR(15)		NOT NULL,
	name			VARCHAR(64)		NOT NULL,
	description		TEXT,
	release_date	TIMESTAMP		NOT NULL,
	eos_date		TIMESTAMP,
	platforms		VARCHAR(64)		NOT NULL,
	PRIMARY KEY(product_id),
	FOREIGN KEY(company_id)		REFERENCES companies(company_id),
	FOREIGN KEY(owner)			REFERENCES users(user_id)
);

-- ============================================================
-- Table: version
-- ============================================================
CREATE TABLE version
(
	product_id		VARCHAR(15)	NOT NULL,
	version			VARCHAR(10)	NOT NULL,
	FOREIGN KEY(product_id)		REFERENCES products(product_id)
);

-- ============================================================
-- Table: issue
-- ============================================================
CREATE TABLE issue
(
	issue_id		VARCHAR(15)		NOT NULL,
	product_id		VARCHAR(15)		NOT NULL,
	status			SMALLINT		NOT NULL,
	type			SMALLINT		NOT NULL,
	severity		SMALLINT		NOT NULL,
	platform		SMALLINT		NOT NULL,
	title			VARCHAR(64)		NOT NULL,
	description		TEXT			NOT NULL,
	reported_by		VARCHAR(15)		NOT NULL,
	reported_date	TIMESTAMP		NOT NULL,
	reported_version VARCHAR(10)	NOT NULL,
	assigned_to		VARCHAR(15),
	resolved_by		VARCHAR(15),
	resolved_date	TIMESTAMP,
	resolved_version VARCHAR(10),
	PRIMARY KEY(issue_id),
	FOREIGN KEY(product_id)		REFERENCES products(product_id),
	FOREIGN KEY(reported_by)	REFERENCES users(user_id),
	FOREIGN KEY(resolved_by)	REFERENCES users(user_id),
	FOREIGN KEY(assigned_to)	REFERENCES users(user_id),
	FOREIGN KEY(reported_version) REFERENCES versions(version),
	FOREIGN KEY(resolved_version) REFERENCES versions(version)
);

-- ============================================================
-- Table: comment
-- ============================================================
CREATE TABLE comment
(
	comment_id		VARCHAR(15)		NOT NULL,
	issue_id		VARCHAR(15)		NOT NULL,
	type			SMALLINT		NOT NULL,
	comment_date	TIMESTAMP		NOT NULL,
	comment			TEXT			NOT NULL,
	FOREIGN KEY(issue_id)			REFERENCES issues(issue_id)
);

-- ============================================================
-- Table: acls
-- ============================================================
CREATE TABLE acls
(
	user_id			VARCHAR(15)	NOT NULL,
	product_id		VARCHAR(15)	NOT NULL,
	role_id			SMALLINT	NOT NULL,
	FOREIGN KEY(user_id)		REFERENCES users(user_id),
	FOREIGN KEY(product_id)		REFERENCES products(product_id),
	FOREIGN KEY(role_id)		REFERENCES roles(role_id)
);

-- ============================================================
-- Privileges
-- ============================================================
GRANT SELECT, INSERT, UPDATE, DELETE on products TO webuser;
GRANT SELECT, INSERT, UPDATE, DELETE on versions TO webuser;
GRANT SELECT, INSERT, UPDATE, DELETE on issues TO webuser;
GRANT SELECT, INSERT, DELETE on comments TO webuser;
GRANT SELECT, INSERT, UPDATE, DELETE on acls TO webuser;
