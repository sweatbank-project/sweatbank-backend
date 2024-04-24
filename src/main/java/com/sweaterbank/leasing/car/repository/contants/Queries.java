package com.sweaterbank.leasing.car.repository.contants;

public class Queries
{
    public static final String SAVE_USER_QUERY =
            """
                INSERT INTO users(
                id,
                username,
                phone_number,
                personal_id,
                password,
                first_name,
                last_name,
                birth_date,
                address,
                role,
                account_expiration_date,
                account_locked,
                enabled)
       
                VALUES(
                :id,
                :username,
                :phone_number,
                :personal_id,
                :password,
                :first_name,
                :last_name,
                :birth_date,
                :address,
                :role,
                CAST(:account_expiration_date AS TIMESTAMP),
                :account_locked,
                :enabled)
            """;

    public static final String SELECT_BY_EMAIL_QUERY =
            """
                    SELECT * FROM users
                    WHERE username = :username
                    LIMIT 1
            """;

    public static final String SELECT_USER_ID_BY_EMAIL_QUERY =
            """
                SELECT id FROM users
                WHERE username = :username
            """;
    public static final String GET_LEASING_QUERY =
            """
                    SELECT * FROM Leasing
                    WHERE id = :id
                    LIMIT 1
            """;

    public static final String GET_ALL_LEASING_QUERY =
            """
                    SELECT *, Obligation.id AS obligation_id
                    FROM Leasing
                    LEFT JOIN Obligation ON Leasing.id = Obligation.leasing_id
                    ORDER BY Leasing.id
            """;

    public static final String GET_ALL_LEASINGS_WITH_USER_INFO_QUERY =
            """     
                    SELECT user_id, users.username, users.first_name, users.last_name, users.phone_number, users.personal_id,
                    lease_id, leasing.application_id, leasing.status, leasing.car_brand, leasing.car_model, leasing.manufacture_year, leasing.car_cost, leasing.leasing_period,
                    leasing.car_seller_name, leasing.education, leasing.held_position, leasing.job_title, leasing.time_employed, leasing.employer_business_area,
                    leasing.marital_status, leasing.number_of_children, leasing.monthly_income_after_taxes,
                    obligation.id AS obligation_id, obligation.obligation_type, obligation.outstanding_debt, obligation.monthly_payment
                    FROM user_leases
                    INNER JOIN users ON user_id = users.id
                    INNER JOIN leasing ON user_leases.lease_id = leasing.id
                    LEFT JOIN obligation ON leasing.id = obligation.leasing_id
            """;

    public static final String SAVE_LEASING_QUERY =
            """
                    INSERT INTO Leasing (id, application_id, status, car_brand, car_model, manufacture_year, car_cost, leasing_period, car_seller_name,
                    education, held_position, job_title, time_employed, employer_business_area, marital_status, number_of_children,
                    monthly_income_after_taxes)
                    VALUES (:id, :application_id, :status, :car_brand, :car_model, :manufacture_year, :car_cost, :leasing_period, :car_seller_name,
                    :education, :held_position, :job_title, :time_employed, :employer_business_area, :marital_status, :number_of_children,
                    :monthly_income_after_taxes)
            """;

    public static final String SAVE_OBLIGATIONS_QUERY =
            """
                INSERT INTO Obligation (id, leasing_id, obligation_type, outstanding_debt, monthly_payment)
                VALUES (:id, :leasing_id, :obligation_type, :outstanding_debt, :monthly_payment)
            """;
    public static final String SAVE_USER_ID_WITH_APPLICATION_ID_QUERY =
            """
                INSERT INTO user_leases(user_id, lease_id)
                VALUES(:user_id, :lease_id)
            """;


    public static final String GET_LEASES_COUNT_QUERY =
            """
                SELECT COUNT(application_id) FROM Leasing
            """;

    public static final String UPDATE_LEASE_QUERY =
            """
                UPDATE Leasing
                SET status = :status
                WHERE id = :id
            """;

    public static final String GET_PENDING_STATUS_COUNT_BY_ID_QUERY =
            """
                SELECT COUNT(leasing.status) FROM user_leases
                INNER JOIN leasing ON user_leases.lease_id = leasing.id
                WHERE leasing.status = 'pending' AND user_leases.user_id = :user_id
            """;
    public static final String GET_NEW_STATUS_COUNT_BY_ID_QUERY =
            """
                SELECT COUNT(leasing.status) FROM user_leases
                INNER JOIN leasing ON user_leases.lease_id = leasing.id
                WHERE leasing.status = 'new' AND user_leases.user_id = :user_id
            """;
}
