DELIMITER $$
CREATE PROCEDURE `sp_create_customer`(
    IN full_name VARCHAR(200),
    IN email VARCHAR(100),
    IN phone VARCHAR(20),
    IN address VARCHAR(500)
)
BEGIN
    INSERT INTO customers (
        full_name,
        email,
        phone,
        address,
        balance,
        created_at,
        updated_at
    )
    VALUES (
       full_name,
       email,
       phone,
       address,
       0,
       NOW(),
       NOW()
   );
END;