DROP TABLE Leasing;
DROP TABLE Car;

CREATE TABLE Leasing (
    id VARCHAR PRIMARY KEY,
    status VARCHAR,
    car_brand VARCHAR,
    car_model VARCHAR,
    manufacture_year INTEGER,
    car_cost DECIMAL(19, 4),
    leasing_period INTEGER,
    car_seller_name VARCHAR,
    education VARCHAR, -- ENUM
    held_position VARCHAR, -- ENUM
    job_title VARCHAR,
    time_employed INTEGER,
    employer_business_area VARCHAR, -- ENUM
    marital_status VARCHAR, -- ENUM
    number_of_children INTEGER,
    monthly_income_after_taxes DECIMAL(19, 4)
);

CREATE TABLE Obligation (
    id VARCHAR PRIMARY KEY,
    leasing_id VARCHAR NOT NULL REFERENCES Leasing(id),
    obligation_type VARCHAR, -- ENUM
    outstanding_debt DECIMAL(19, 4),
    monthly_payment DECIMAL(19, 4)
);