-- Create Supermarket Inventory Database
CREATE DATABASE IF NOT EXISTS supermarket_inventory;
USE supermarket_inventory;

-- Categories Table
CREATE TABLE IF NOT EXISTS categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category_code VARCHAR(50) UNIQUE NOT NULL,
    category_name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Suppliers Table
CREATE TABLE IF NOT EXISTS suppliers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    supplier_name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255),
    city VARCHAR(50),
    country VARCHAR(50),
    credit_limit DECIMAL(15,2) DEFAULT 0,
    current_debt DECIMAL(15,2) DEFAULT 0,
    payment_terms_days INT DEFAULT 30,
    is_active BOOLEAN DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Products Table
CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_code VARCHAR(50) UNIQUE NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    description TEXT,
    unit_price DECIMAL(10,2) NOT NULL,
    category_id INT,
    current_stock INT DEFAULT 0,
    min_stock INT DEFAULT 10,
    max_stock INT DEFAULT 100,
    unit VARCHAR(20),
    supplier_id INT,
    barcode VARCHAR(50) UNIQUE,
    discontinued BOOLEAN DEFAULT 0,
    is_active BOOLEAN DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    role ENUM('ADMIN', 'MANAGER', 'STAFF', 'VIEWER') DEFAULT 'STAFF',
    department VARCHAR(50),
    locked BOOLEAN DEFAULT 0,
    failed_login_attempts INT DEFAULT 0,
    is_active BOOLEAN DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Inventory Transactions Table
CREATE TABLE IF NOT EXISTS inventory_transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    product_name VARCHAR(100),
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2),
    total_value DECIMAL(15,2),
    transaction_type ENUM('PURCHASE', 'SALE', 'ADJUSTMENT', 'RETURN', 'TRANSFER', 'DAMAGE') NOT NULL,
    reference_number VARCHAR(100),
    supplier_id INT,
    user_id INT,
    remarks TEXT,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX (transaction_date),
    INDEX (product_id)
);

-- Stock Alerts Table
CREATE TABLE IF NOT EXISTS stock_alerts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    product_name VARCHAR(100),
    alert_type ENUM('LOW_STOCK', 'OVERSTOCK', 'OUT_OF_STOCK', 'NEAR_EXPIRY', 'DAMAGED') NOT NULL,
    current_stock INT,
    threshold INT,
    severity VARCHAR(20) DEFAULT 'MEDIUM',
    acknowledged BOOLEAN DEFAULT 0,
    acknowledged_by INT,
    acknowledged_at TIMESTAMP NULL,
    remarks TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id),
    INDEX (product_id),
    INDEX (alert_type),
    INDEX (acknowledged)
);

-- Create indexes for better performance
CREATE INDEX idx_product_code ON products(product_code);
CREATE INDEX idx_product_name ON products(product_name);
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_category_name ON categories(category_name);
CREATE INDEX idx_supplier_name ON suppliers(supplier_name);
