ALTER TABLE Leasing
ADD COLUMN down_payment_percentage INTEGER,
ADD COLUMN contract_fee DECIMAL(10, 2),
ADD COLUMN euribor_type VARCHAR,
ADD COLUMN euribor_rate DECIMAL(10, 2),
ADD COLUMN margin DECIMAL(10, 2),
ADD COLUMN interest_rate DECIMAL(10, 2),
ADD COLUMN monthly_payment DECIMAL(10, 2);