ALTER TABLE Leasing
ADD COLUMN application_id VARCHAR(10) NOT NULL DEFAULT 'SB0000' || CAST(floor(random() * (10000 - 1000 + 1) + 1000)::int AS VARCHAR(4));
