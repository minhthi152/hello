CREATE VIEW vw_get_all_customers AS
SELECT
    c.id, 
    c.full_name,
    c.email,
    c.phone,
    c.address,
    c.balance
FROM customers AS c
WHERE c.deleted = 0;