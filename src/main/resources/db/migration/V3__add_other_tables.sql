CREATE TABLE PersonalData (
      id VARCHAR PRIMARY KEY,
      name VARCHAR NOT NULL,
      surname VARCHAR NOT NULL,
      birthdate DATE NOT NULL,
      identityNumber VARCHAR NOT NULL UNIQUE,
      salary DECIMAL(10, 2) NOT NULL,
      education VARCHAR NOT NULL,
      positionHeld VARCHAR NOT NULL,
      jobTitle VARCHAR
);

CREATE TABLE Car (
     id VARCHAR PRIMARY KEY,
     make VARCHAR NOT NULL,
     model VARCHAR NOT NULL,
     year INTEGER NOT NULL,
     cost DECIMAL(10, 2) NOT NULL
);

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id VARCHAR PRIMARY KEY,
    personal_data_id VARCHAR NOT NULL REFERENCES PersonalData(id),
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    role roles NOT NULL
);

CREATE TABLE Leasing (
     id VARCHAR PRIMARY KEY,
     user_id VARCHAR NOT NULL REFERENCES users(id),
     car_id VARCHAR NOT NULL REFERENCES Car(id),
     startDate DATE NOT NULL,
     endDate DATE NOT NULL,
     monthlyPayment DECIMAL(10, 2) NOT NULL,
     deposit DECIMAL(10, 2) NOT NULL,
     status VARCHAR NOT NULL
);