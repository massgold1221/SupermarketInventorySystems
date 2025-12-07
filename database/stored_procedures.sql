-- Stored Procedures for Supermarket Inventory System

USE supermarket_inventory;

-- Procedure to get low stock products
DELIMITER //
CREATE PROCEDURE GetLowStockProducts()
BEGIN
    SELECT id, product_name, product_code, current_stock, min_stock
    FROM products
    WHERE current_stock <= min_stock AND is_active = 1
    ORDER BY current_stock ASC;
END//
DELIMITER ;

-- Procedure to update product stock
DELIMITER //
CREATE PROCEDURE UpdateProductStock(
    IN p_product_id INT,
    IN p_new_quantity INT
)
BEGIN
    UPDATE products
    SET current_stock = p_new_quantity,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_product_id;
END//
DELIMITER ;

-- Procedure to create inventory transaction
DELIMITER //
CREATE PROCEDURE RecordTransaction(
    IN p_product_id INT,
    IN p_quantity INT,
    IN p_transaction_type VARCHAR(50),
    IN p_user_id INT
)
BEGIN
    INSERT INTO inventory_transactions (
        product_id, quantity, transaction_type, user_id, transaction_date
    ) VALUES (
        p_product_id, p_quantity, p_transaction_type, p_user_id, CURRENT_TIMESTAMP
    );
END//
DELIMITER ;

-- Procedure to get inventory summary
DELIMITER //
CREATE PROCEDURE GetInventorySummary()
BEGIN
    SELECT
        COUNT(*) as total_products,
        SUM(current_stock * unit_price) as total_inventory_value,
        AVG(current_stock) as average_stock_level,
        COUNT(CASE WHEN current_stock <= min_stock THEN 1 END) as low_stock_count
    FROM products
    WHERE is_active = 1;
END//
DELIMITER ;

-- Procedure to get sales report
DELIMITER //
CREATE PROCEDURE GetSalesReport(
    IN start_date DATE,
    IN end_date DATE
)
BEGIN
    SELECT
        DATE(transaction_date) as sale_date,
        product_name,
        SUM(quantity) as total_quantity,
        SUM(total_value) as total_sales
    FROM inventory_transactions
    WHERE transaction_type = 'SALE'
        AND DATE(transaction_date) BETWEEN start_date AND end_date
    GROUP BY DATE(transaction_date), product_name
    ORDER BY sale_date DESC;
END//
DELIMITER ;
