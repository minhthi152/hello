DELIMITER $$
CREATE PROCEDURE `sp_transfer`(
    IN senderId INT,
    IN recipientId INT,
    IN transferAmount DECIMAL(12,0),
    IN fees INT,
    IN feesAmount DECIMAL(12,0),
    IN transactionAmount DECIMAL(12,0)
)
BEGIN
    INSERT INTO transfers (
        sender_id,
        recipient_id,
        transfer_amount,
        fees,
        fees_amount,
        transaction_amount,
        created_at,
        updated_at
    )
    VALUES (
        senderId,
        recipientId,
        transferAmount,
        fees,
        feesAmount,
        transactionAmount,
        NOW(),
        NOW()
    );

    UPDATE customers AS c
    SET c.balance = c.balance - transactionAmount
    WHERE c.id = senderId;

    UPDATE customers AS c
    SET c.balance = c.balance + transferAmount
    WHERE c.id = recipientId;

END;