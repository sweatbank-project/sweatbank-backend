CREATE TABLE user_leases(
    user_id VARCHAR NOT NULL,
    lease_id VARCHAR NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (lease_id) REFERENCES Leasing(id) ON DELETE CASCADE
)