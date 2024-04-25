ALTER TABLE users
DROP COLUMN personal_data_id,
ADD COLUMN phone_number VARCHAR(32),
ADD COLUMN personal_id VARCHAR(32),
ADD COLUMN first_name VARCHAR(64),
ADD COLUMN last_name VARCHAR(64),
ADD COLUMN birth_date DATE,
ADD COLUMN address VARCHAR(256);
DROP TABLE PersonalData;
