DELIMITER $$
CREATE PROCEDURE `sp_deposit`(
    IN customerId INT,
    IN transactionAmount DECIMAL(12,0)
)
BEGIN
    INSERT INTO deposits (
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
    SET c.balance = c.balance + transactionAmount
    WHERE c.id = customerId;

END;