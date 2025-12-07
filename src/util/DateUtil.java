package util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * DateUtil: Enhanced date and time operations
 */
public class DateUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Get current date
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    
    /**
     * Get current datetime
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
    
    /**
     * Format date to string
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Format datetime to string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATETIME_FORMATTER);
    }
    
    /**
     * Parse date from string
     */
    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }
    
    /**
     * Parse datetime from string
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DATETIME_FORMATTER);
    }
    
    /**
     * Get days between two dates
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }
    
    /**
     * Add days to date
     */
    public static LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }
    
    /**
     * Add months to date
     */
    public static LocalDate addMonths(LocalDate date, int months) {
        return date.plusMonths(months);
    }
    
    /**
     * Check if date is in the past
     */
    public static boolean isPast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
    
    /**
     * Check if date is in the future
     */
    public static boolean isFuture(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
}
