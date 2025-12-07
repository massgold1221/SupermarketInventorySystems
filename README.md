# Supermarket Inventory System

A comprehensive Java-based inventory management system for supermarkets with remote access capabilities, real-time stock tracking, and advanced reporting features.

## Features

### Core Features
- **Product Management**: Add, update, and manage products with categories and suppliers
- **Stock Management**: Real-time inventory tracking with min/max stock levels
- **Transaction Tracking**: Complete history of all inventory movements (purchase, sale, adjustment, return, transfer, damage)
- **User Management**: Role-based access control (Admin, Manager, Staff, Viewer)
- **Stock Alerts**: Automatic alerts for low stock, overstock, and out-of-stock conditions
- **Reporting**: Generate comprehensive inventory, sales, and analysis reports

### Advanced Features
- **Network Remote Access**: Client-server architecture for remote inventory operations
- **Connection Pooling**: Efficient database and network connection management
- **Concurrent Operations**: Thread-safe operations with transaction retry logic
- **Session Management**: Secure session handling with timeout protection
- **Data Serialization**: Object serialization for network communication
- **Security**: Password hashing and basic encryption utilities
- **Validation**: Comprehensive input validation for all data types
- **Logging**: Multi-level logging with file output
- **Configuration Management**: Externalized configuration for easy deployment

## Project Structure

```
SupermarketInventorySystem/
├── src/
│   ├── core/
│   │   ├── model/              # Entity models
│   │   ├── dao/                # Database access layer
│   │   ├── service/            # Business logic layer
│   │   └── dto/                # Data transfer objects
│   ├── network/
│   │   ├── protocol/           # Network communication protocol
│   │   ├── server/             # Server-side components
│   │   └── client/             # Client-side components
│   ├── concurrent/             # Threading and concurrent operations
│   ├── ui/                     # (Future) User interface components
│   ├── util/                   # Utility classes
│   └── MainApp.java           # Application entry point
├── lib/                        # External libraries
├── database/                   # Database schema and scripts
├── config/                     # Configuration files
├── logs/                       # Application logs
├── reports/                    # Report templates and generated reports
├── exports/                    # Data exports
├── backups/                    # Database backups
└── README.md
```

## Model Classes

### Core Models
- **BaseEntity**: Abstract base class for all entities with timestamp tracking
- **Product**: Product information with pricing and stock levels
- **Category**: Product categorization
- **Supplier**: Supplier information and credit terms
- **User**: User accounts with role-based access
- **InventoryTransaction**: Tracks all inventory movements
- **StockAlert**: Alerts for inventory anomalies

## Data Access Layer (DAO)

- **BaseDAO**: Template pattern base class for CRUD operations
- **DatabaseConnection**: Connection management with pooling
- **ProductDAO**: Product data operations with batch support
- **CategoryDAO**: Category management
- **SupplierDAO**: Supplier data operations
- **UserDAO**: User account management
- **TransactionDAO**: Transaction logging with isolation levels
- **AlertDAO**: Stock alert management

## Service Layer

- **BaseService**: Common service methods and error handling
- **InventoryService**: Core inventory business logic
- **AuthService**: Authentication and authorization
- **ReportService**: Report generation with async support
- **AlertService**: Stock alert management and notifications

## Network Components

### Protocol
- **CommandType**: Enumeration of supported remote commands
- **RemoteCommand**: Command messages sent over network
- **RemoteResponse**: Response messages with status codes

### Server
- **InventoryServer**: Main server with graceful shutdown
- **ClientHandler**: Individual client connection handler
- **CommandProcessor**: Command processing logic
- **SessionManager**: Client session management with timeout

### Client
- **InventoryClient**: Client with auto-reconnect logic
- **ConnectionPool**: Connection pooling for efficient resource usage

## Concurrent Components

- **InventoryUpdateThread**: Background inventory updates with retry logic
- **ReportGenerationThread**: Async report generation with progress tracking
- **NotificationThread**: Background alert notification handler
- **ThreadPoolManager**: Thread pool management with monitoring
- **TaskScheduler**: Scheduled task management

## Utilities

- **Logger**: Multi-level logging system
- **ConfigLoader**: Configuration file management
- **Validator**: Input validation for common data types
- **DateUtil**: Date and time operations
- **SecurityUtil**: Password hashing and encryption
- **FileUtil**: File operations
- **SerializationUtil**: Object serialization helpers
- **NotificationUtil**: Alert notification system

## Database Schema

### Tables
- **categories**: Product categories
- **suppliers**: Supplier information
- **products**: Product inventory
- **users**: System users
- **inventory_transactions**: Transaction history
- **stock_alerts**: Stock alerts

### Stored Procedures
- GetLowStockProducts
- UpdateProductStock
- RecordTransaction
- GetInventorySummary
- GetSalesReport

## Configuration Files

- **database.properties**: Database connection settings
- **application.properties**: Application configuration
- **server.properties**: Server configuration
- **client.properties**: Client configuration
- **logging.properties**: Logging configuration
- **alerts.properties**: Stock alert thresholds

## Getting Started

### Prerequisites
- Java 8 or higher
- MySQL 5.7 or higher
- Maven (optional, for dependency management)

### Setup

1. **Create Database**
   ```bash
   mysql -u root -p < database/supermarket_inventory.sql
   mysql -u root -p < database/stored_procedures.sql
   mysql -u root -p < database/sample_data.sql
   ```

2. **Update Configuration**
   - Edit `config/database.properties` with your database credentials
   - Update `config/server.properties` for server settings
   - Configure `config/alerts.properties` for alert thresholds

3. **Compile**
   ```bash
   javac -d bin src/**/*.java
   ```

4. **Run Server**
   ```bash
   java -cp bin:lib/* MainApp server
   ```

5. **Run Client** (programmatic access)
   ```java
   InventoryClient client = new InventoryClient("localhost", 5000);
   client.connect();
   client.login("admin", "admin123");
   ```

## API Examples

### Server Commands

#### Login
```java
RemoteCommand cmd = new RemoteCommand(CommandType.LOGIN);
Map<String, String> credentials = new HashMap<>();
credentials.put("username", "admin");
credentials.put("password", "admin123");
cmd.setCommandData(credentials);
RemoteResponse response = client.sendCommand(cmd);
```

#### Get Products
```java
RemoteCommand cmd = new RemoteCommand(CommandType.GET_ALL_PRODUCTS);
RemoteResponse response = client.sendCommand(cmd);
```

#### Update Stock
```java
RemoteCommand cmd = new RemoteCommand(CommandType.UPDATE_STOCK);
Map<String, Object> data = new HashMap<>();
data.put("productId", 1);
data.put("quantity", 100);
cmd.setCommandData(data);
RemoteResponse response = client.sendCommand(cmd);
```

## Features Implemented

### ✅ Completed
- Core entity models with validation
- DAO layer with database operations
- Service layer with business logic
- Network protocol and communication
- Server with client handling
- Client with auto-reconnect
- Connection pooling
- Threading and concurrent operations
- Scheduling system
- Comprehensive utilities
- Configuration management
- Database schema and stored procedures

### 🔄 In Progress/Future
- Swing GUI components
- Desktop application UI
- Advanced reporting with PDF export
- Email notification system
- Dashboard and analytics
- Barcode scanning support
- Mobile app integration
- REST API endpoints

## Performance Considerations

- **Connection Pooling**: Reduces database connection overhead
- **Batch Operations**: Bulk insert/update for improved throughput
- **Thread Pooling**: Efficient resource management for concurrent tasks
- **Session Timeout**: Prevents resource exhaustion from inactive connections
- **Indexed Queries**: Database indexes for faster lookups

## Security Features

- Password hashing with SHA-256
- Session-based authentication
- Role-based access control
- SQL injection prevention through prepared statements
- Account lockout after failed login attempts
- Audit logging capabilities

## Error Handling

- Exception handling at all layers
- Retry logic for network operations
- Graceful degradation
- Comprehensive logging for debugging

## Testing Recommendations

1. Unit tests for DAO and Service layers
2. Integration tests for database operations
3. Network communication tests
4. Load testing for concurrent operations
5. Security testing for authentication

## Deployment

### Single Server
```bash
java -cp bin:lib/* MainApp server
```

### Load Balancing (Future)
- Deploy multiple server instances
- Use connection pooling on client side
- Implement session replication

## Troubleshooting

### Database Connection Issues
- Verify MySQL is running
- Check database credentials in config/database.properties
- Ensure database and tables are created

### Network Issues
- Check server is running and listening on configured port
- Verify firewall settings
- Check client configuration for correct host/port

### Performance Issues
- Monitor thread pool statistics
- Check database query performance
- Increase connection pool size if needed
- Review log files for errors

## Contributing

To extend this system:
1. Add new model classes extending BaseEntity
2. Create corresponding DAO classes extending BaseDAO
3. Implement service methods in appropriate Service class
4. Add network commands if needed
5. Update configuration files as required

## License

This project is provided as an educational inventory management system.

## Support

For issues or questions:
1. Check log files in `logs/` directory
2. Review configuration in `config/` directory
3. Verify database schema is correctly created
4. Check database connectivity

## Version History

### v1.0.0
- Initial release
- Core functionality implemented
- Server and client components
- Database and network layers
- Threading and scheduling
- Comprehensive utilities