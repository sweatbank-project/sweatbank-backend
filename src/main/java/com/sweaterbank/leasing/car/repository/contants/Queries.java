package com.sweaterbank.leasing.car.repository.contants;

public class Queries
{
    public static final String SAVE_USER_QUERY =
            """
                INSERT INTO users(id, username, personal_data_id, password, role, account_expiration_date, account_locked, enabled)
                VALUES(:id, :username, :personal_data_id, :password, :role, CAST(:account_expiration_date AS TIMESTAMP), :account_locked, :enabled)
            """;

    public static final String SELECT_BY_EMAIL_QUERY =
            """
                    SELECT * FROM users
                    WHERE username = :username
            """;

}
