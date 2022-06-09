DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_all_transfers_by_date`(
    IN inDate DATE
)
BEGIN
    DECLARE existDate BOOLEAN DEFAULT false;

    SET existDate = false;

    IF (SELECT DATE(inDate))
    THEN
        SET existDate = true;
    END IF;

    IF existDate
    THEN
        SELECT
            trans.id AS id,
            trans.sender_id AS sender_id,
            send.full_name AS sender_name,
            trans.recipient_id AS recipient_id,
            rep.full_name AS recipient_name,
            trans.created_at AS created_at,
            trans.transfer_amount AS transfer_amount,
            trans.fees AS fees,
            trans.fees_amount AS fees_amount,
            trans.transaction_amount AS transaction_amount
        FROM transfers AS trans
        JOIN customers AS rep
            ON trans.recipient_id = rep.id
        JOIN customers AS send
            ON trans.sender_id = send.id
        WHERE
            DATE(trans.created_at) = inDate;
    END IF;
END;