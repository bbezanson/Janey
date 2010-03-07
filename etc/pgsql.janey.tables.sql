-- ============================================================
-- tables for janey project
-- ============================================================
-- Copyright 2010, Wade Girard, Brian Bezanson
-- Last updated: March, 2010
--
-- First create the database 
-- >>> createdb janey;
--
-- use the following command to load these tables
--
-- psql -d janey -f pgsql.janey.tables.sql
--

-- ============================================================
-- Table: janey_user
-- ============================================================
CREATE TABLE janey_user
(
	user_id		VARCHAR(24) NOT NULL,
	password	VARCHAR(64) NOT NULL,
	PRIMARY KEY(user_id)
);

-- ============================================================
-- Table: product
-- ============================================================
CREATE TABLE product
(
	product_id		BIGINT			NOT NULL,
	company_id		BIGINT,
	owner			VARCHAR(24),
	name			VARCHAR(64)		NOT NULL,
	description		TEXT,
	release_date	TIMESTAMP,
	eos_date		TIMESTAMP,
	platforms		VARCHAR(64),
	PRIMARY KEY(product_id),
	FOREIGN KEY(owner)			REFERENCES janey_user(user_id)
);

-- ============================================================
-- Table: version
-- ============================================================
CREATE TABLE version
(
	product_id		BIGINT	NOT NULL,
	version			VARCHAR(64)	NOT NULL,
	PRIMARY KEY(product_id, version),
	FOREIGN KEY(product_id)		REFERENCES product(product_id)
);

-- ============================================================
-- Table: issue
-- ============================================================
CREATE TABLE issue
(
	issue_id		BIGINT		NOT NULL,
	product_id		BIGINT		NOT NULL,
	status			SMALLINT		NOT NULL,
	type			SMALLINT		NOT NULL,
	severity		SMALLINT		NOT NULL,
	platform		SMALLINT		NOT NULL,
	title			VARCHAR(64)		NOT NULL,
	description		TEXT			NOT NULL,
	reported_by		VARCHAR(24)		NOT NULL,
	reported_date	TIMESTAMP		NOT NULL,
	reported_version VARCHAR(64)	NOT NULL,
	assigned_to		VARCHAR(24),
	resolved_by		VARCHAR(24),
	resolved_date	TIMESTAMP,
	resolved_version VARCHAR(64),
	PRIMARY KEY(issue_id),
	FOREIGN KEY(product_id)		REFERENCES product(product_id),
	FOREIGN KEY(reported_by)	REFERENCES janey_user(user_id),
	FOREIGN KEY(resolved_by)	REFERENCES janey_user(user_id),
	FOREIGN KEY(assigned_to)	REFERENCES janey_user(user_id)
);

-- ============================================================
-- Table: comment
-- ============================================================
CREATE TABLE comment
(
	comment_id		BIGINT		NOT NULL,
	issue_id		BIGINT		NOT NULL,
	type			SMALLINT		NOT NULL,
	comment_date	TIMESTAMP		NOT NULL,
	comment			TEXT			NOT NULL,
	FOREIGN KEY(issue_id)			REFERENCES issue(issue_id)
);

-- ============================================================
-- Table: acl
-- ============================================================
CREATE TABLE acl
(
	user_id			VARCHAR(15)	NOT NULL,
	product_id		BIGINT	NOT NULL,
	role_id			SMALLINT	NOT NULL,
	FOREIGN KEY(user_id)		REFERENCES janey_user(user_id),
	FOREIGN KEY(product_id)		REFERENCES product(product_id)
);

-- ============================================================
-- Privileges
-- ============================================================
GRANT SELECT, INSERT, UPDATE, DELETE on product TO webuser;
GRANT SELECT, INSERT, UPDATE, DELETE on version TO webuser;
GRANT SELECT, INSERT, UPDATE, DELETE on issue TO webuser;
GRANT SELECT, INSERT, DELETE on comment TO webuser;
GRANT SELECT, INSERT, UPDATE, DELETE on janey_user to webuser;
GRANT SELECT, INSERT, UPDATE, DELETE on acl TO webuser;
