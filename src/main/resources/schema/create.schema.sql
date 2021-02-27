CREATE SCHEMA IF NOT EXISTS takeHome;

commit;

-- -----------------------------------------------------
-- Table takeHome.customers
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS takeHome.customers (
  id BIGINT NOT NULL IDENTITY,
  name VARCHAR(255) NOT NULL,
  active BOOLEAN NOT NULL,
  contact_email VARCHAR(255) NULL,
  contact_firstname VARCHAR(255) NULL,
  contact_lastname VARCHAR(255) NULL,
  PRIMARY KEY (id));

-- -----------------------------------------------------
-- Table takeHome.departments
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS takeHome.departments (
  id BIGINT NOT NULL IDENTITY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NULL,
  active BOOLEAN NOT NULL,
  customer_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_departments_customers1
    FOREIGN KEY (customer_id)
    REFERENCES takeHome.customers (id)
    ON DELETE CASCADE);

CREATE INDEX IF NOT EXISTS fk_departments_customer1_idx ON takeHome.departments (customer_id ASC);

-- -----------------------------------------------------
-- Table takeHome.users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS takeHome.users (
  id BIGINT NOT NULL IDENTITY,
  customer_id BIGINT NOT NULL,
  username VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NULL,
  last_name VARCHAR(255) NULL,
  email VARCHAR(255) NULL,
  telephone_number VARCHAR(20) NULL,
  mobile_number VARCHAR(20) NULL,
  fax_number VARCHAR(20) NULL,
  active BOOLEAN NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_users_customers1
    FOREIGN KEY (customer_id)
    REFERENCES takeHome.customers (id)
    ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS takeHome.user_department (
  user_id BIGINT NULL,
  department_id BIGINT NULL,
  CONSTRAINT fk_user_department_departmentid
    FOREIGN KEY (department_id)
    REFERENCES takeHome.departments (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_user_department_userid
    FOREIGN KEY (user_id)
    REFERENCES takeHome.users (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE INDEX IF NOT EXISTS fk_users_customers1_idx ON takeHome.users (customer_id ASC);
CREATE INDEX IF NOT EXISTS fk_user_department_departmentid_idx ON takeHome.user_department (department_id ASC);
CREATE INDEX IF NOT EXISTS fk_user_department_userid_idx ON takeHome.user_department (user_id ASC);

commit;
