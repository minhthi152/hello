DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_transfer_rollback`(
    IN senderId INT,
    IN recipientId INT,
    IN transferAmount DECIMAL (10, 0),
    OUT message VARCHAR(100),
    OUT success BOOLEAN
)
BEGIN
    DECLARE senderCredit DECIMAL(12,0) DEFAULT 0;
    DECLARE fees INT DEFAULT 10;
    DECLARE feesAmount DECIMAL(10,0) DEFAULT 0;
    DECLARE transactionAmount DECIMAL(12,0) DEFAULT 0;
    DECLARE minMoney DECIMAL(4,0);
    DECLARE maxMoney DECIMAL(10,0);

    DECLARE _rollback BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _rollback = 1;

    SET success = false;
    SET minMoney = 49;
    SET maxMoney = 1000000001;

    START TRANSACTION;

    IF (transferAmount > minMoney AND transferAmount < maxMoney)
    THEN
        IF (senderId <> recipientId)
        THEN
            IF EXISTS (SELECT 1 FROM customers WHERE id = senderId)
            THEN
                IF EXISTS (SELECT 1 FROM customers WHERE id = recipientId)
                THEN
                    SELECT balance
                    INTO senderCredit
                    FROM customers
                    WHERE id = senderId;

                    SET feesAmount = transferAmount * fees / 100;
                    SET transactionAmount = transferAmount + feesAmount;

                    IF (senderCredit >= transactionAmount)
                    THEN
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

                    ELSE
                        SET message = 'The Sender balance is not enough to make the transfer';
                    END IF;

                    IF _rollback
                    THEN
                        SET message = 'Transfer failed, please contact the system administrator';
                        ROLLBACK;
                    ELSE
                        SET message = 'Transfer successful';
                        SET success = true;
                        COMMIT;
                    END IF;
                ELSE
                    SET message = 'Invalid Recipient information';
                END IF;
            ELSE
                SET message = 'Invalid Sender information';
            END IF;
        ELSE
            SET message = 'ID of the Sender must be different with ID of Receipient';
        END IF;
    ELSE
        SET message = 'The transaction amount is not valid';
    END IF;
END;