-- Sample Data for Supermarket Inventory System

USE supermarket_inventory;

-- Insert Categories
INSERT INTO categories (category_code, category_name, description) VALUES
('GRO', 'Groceries', 'General grocery items'),
('DAI', 'Dairy', 'Milk, cheese, yogurt products'),
('BEV', 'Beverages', 'Drinks and beverages'),
('MEA', 'Meat', 'Fresh and frozen meat'),
('FRU', 'Fruits', 'Fresh fruits'),
('VEG', 'Vegetables', 'Fresh vegetables'),
('BAK', 'Bakery', 'Bread and bakery products'),
('FRZ', 'Frozen', 'Frozen items');

-- Insert Suppliers
INSERT INTO suppliers (supplier_name, contact_person, email, phone, city, country, credit_limit, payment_terms_days) VALUES
('Fresh Foods Inc', 'John Smith', 'john@freshfoods.com', '555-0001', 'New York', 'USA', 50000, 30),
('Quality Dairy', 'Mary Johnson', 'mary@qualitydairy.com', '555-0002', 'Wisconsin', 'USA', 30000, 15),
('Beverage World', 'Robert Brown', 'robert@bevworld.com', '555-0003', 'California', 'USA', 40000, 30),
('Meat Masters', 'Linda Davis', 'linda@meatmasters.com', '555-0004', 'Texas', 'USA', 60000, 7);

-- Insert Users
INSERT INTO users (username, password_hash, full_name, email, role, department) VALUES
('admin', 'admin123', 'Administrator', 'admin@inventory.com', 'ADMIN', 'Management'),
('manager1', 'pass123', 'Store Manager', 'manager@inventory.com', 'MANAGER', 'Store'),
('staff1', 'staff123', 'Inventory Staff', 'staff@inventory.com', 'STAFF', 'Warehouse'),
('viewer1', 'viewer123', 'Report Viewer', 'viewer@inventory.com', 'VIEWER', 'Accounting');

-- Insert Products
INSERT INTO products (product_code, product_name, description, unit_price, category_id, current_stock, min_stock, max_stock, unit, supplier_id, barcode) VALUES
('GRO001', 'Pasta', 'Premium pasta 500g', 2.50, 1, 150, 20, 200, 'BOX', 1, '1234567890123'),
('DAI001', 'Milk 1L', 'Whole milk 1 liter', 3.50, 2, 80, 30, 100, 'BOTTLE', 2, '1234567890124'),
('BEV001', 'Orange Juice', 'Fresh orange juice 1L', 4.00, 3, 60, 15, 80, 'BOTTLE', 3, '1234567890125'),
('MEA001', 'Chicken Breast', 'Fresh chicken breast kg', 8.99, 4, 45, 20, 60, 'KG', 4, '1234567890126'),
('FRU001', 'Apples', 'Red apples per kg', 2.99, 5, 120, 30, 150, 'KG', 1, '1234567890127'),
('VEG001', 'Tomatoes', 'Fresh tomatoes per kg', 1.99, 6, 100, 25, 120, 'KG', 1, '1234567890128'),
('BAK001', 'Bread', 'Whole wheat bread', 2.50, 7, 90, 20, 100, 'LOAF', 1, '1234567890129'),
('FRZ001', 'Ice Cream', 'Vanilla ice cream 500ml', 5.99, 8, 35, 10, 50, 'BUCKET', 2, '1234567890130');
