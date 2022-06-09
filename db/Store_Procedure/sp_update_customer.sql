DELIMITER $$
CREATE PROCEDURE `sp_update_customer`(
    IN id INT,
    IN full_name VARCHAR(200),
    IN email VARCHAR(100),
    IN phone VARCHAR(20),
    IN address VARCHAR(500)
)
BEGIN
    UPDATE customers AS c
    SET
        c.full_name = full_name,
        c.email = email,
        c.phone = phone,
        c.address = address,
        c.updated_at = NOW()
    WHERE c.id = id;
END;