ALTER TABLE Obligation
ADD FOREIGN KEY (leasing_id) REFERENCES Leasing(id) ON DELETE CASCADE;