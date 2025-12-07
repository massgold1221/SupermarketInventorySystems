package network.protocol;

/**
 * CommandType: Enum for different command types in network communication
 */
public enum CommandType {
    // Authentication
    LOGIN,
    LOGOUT,
    
    // Product operations
    GET_PRODUCT,
    GET_ALL_PRODUCTS,
    ADD_PRODUCT,
    UPDATE_PRODUCT,
    DELETE_PRODUCT,
    SEARCH_PRODUCTS,
    
    // Stock operations
    UPDATE_STOCK,
    CHECK_STOCK,
    GET_LOW_STOCK_ITEMS,
    
    // Transaction operations
    RECORD_TRANSACTION,
    GET_TRANSACTION_HISTORY,
    
    // Alert operations
    GET_ALERTS,
    ACKNOWLEDGE_ALERT,
    
    // Report operations
    GENERATE_REPORT,
    GET_REPORT_STATUS,
    
    // System operations
    PING,
    SERVER_INFO,
    SYNC_DATA,
    
    // Response types
    SUCCESS,
    ERROR,
    ACKNOWLEDGMENT
}
