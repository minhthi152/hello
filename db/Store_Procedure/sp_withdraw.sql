DELIMITER $$
CREATE PROCEDURE `sp_withdraw`(
    IN customerId INT,
    IN transactionAmount DECIMAL(12,0)
)
BEGIN
    INSERT INTO withdraws (
        customer_id,
        transaction_amount,
        created_at,
        updated_at
    )
    VALUES (
        customerId,
        transactionAmount,
        NOW(),
        NOW()
    );

    UPDATE customers AS c
    SET c.balance = c.balance - transactionAmount
    WHERE c.id = customerId;

END;