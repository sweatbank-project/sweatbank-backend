ALTER TABLE users
DROP COLUMN personal_data_id;
ALTER TABLE users
ADD COLUMN personal_data_id VARCHAR NULL REFERENCES PersonalData(id);
