DELIMITER $$
CREATE PROCEDURE `sp_deposit_rollback`(
    IN customerId INT,
    IN transactionAmount DECIMAL(12,0),
    OUT message VARCHAR(100),
    OUT success boolean
)
BEGIN
    DECLARE _rollback BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _rollback = 1;

    SET success = false;

    START TRANSACTION;

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

    IF _rollback
    THEN
        SET message = 'Deposit failed, please contact the system administrator';
        ROLLBACK;
    ELSE
        SET message = 'Successful deposit transaction';
        SET success = true;
        COMMIT;
    END IF;

END;