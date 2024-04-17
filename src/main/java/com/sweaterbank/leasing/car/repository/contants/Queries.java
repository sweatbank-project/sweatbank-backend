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
                    LIMIT 1
            """;

    public static final String GET_LEASING_QUERY =
            """
                    SELECT * FROM Leasing
                    WHERE id = :id
                    LIMIT 1
            """;

    public static final String GET_ALL_LEASING_QUERY =
            """
                    SELECT * FROM Leasing
                    LIMIT :limit OFFSET :offset
            """;

    public static final String GET_OBLIGATIONS_FOR_LEASING_QUERY =
            """
                    SELECT * FROM Obligation
                    WHERE leasing_id = :leasing_id
            """;
}
