package util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger: Enhanced logging utility with different log levels
 */
public class Logger {
    public enum LogLevel {
        DEBUG, INFO, WARN, ERROR, FATAL
    }
    
    private static final String LOG_FILE_PATH = "logs/application.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static LogLevel currentLevel = LogLevel.INFO;
    
    /**
     * Set the current log level
     */
    public static void setLogLevel(LogLevel level) {
        currentLevel = level;
    }
    
    /**
     * Log debug message
     */
    public static void debug(String message) {
        if (isLoggable(LogLevel.DEBUG)) {
            log(LogLevel.DEBUG, message);
        }
    }
    
    /**
     * Log info message
     */
    public static void info(String message) {
        if (isLoggable(LogLevel.INFO)) {
            log(LogLevel.INFO, message);
        }
    }
    
    /**
     * Log warning message
     */
    public static void warn(String message) {
        if (isLoggable(LogLevel.WARN)) {
            log(LogLevel.WARN, message);
        }
    }
    
    /**
     * Log error message
     */
    public static void error(String message, Exception e) {
        if (isLoggable(LogLevel.ERROR)) {
            log(LogLevel.ERROR, message);
            if (e != null) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Log fatal message
     */
    public static void fatal(String message) {
        if (isLoggable(LogLevel.FATAL)) {
            log(LogLevel.FATAL, message);
        }
    }
    
    /**
     * Check if log level is enabled
     */
    private static boolean isLoggable(LogLevel level) {
        return level.ordinal() >= currentLevel.ordinal();
    }
    
    /**
     * Write log message
     */
    private static void log(LogLevel level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logMessage = "[" + timestamp + "] [" + level + "] " + message;
        
        // Console output
        System.out.println(logMessage);
        
        // File output
        try (FileWriter fw = new FileWriter(LOG_FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(logMessage);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
