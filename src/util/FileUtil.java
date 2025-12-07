package util;

import java.io.*;
import java.nio.file.*;

/**
 * FileUtil: File operations utilities
 */
public class FileUtil {
    
    /**
     * Create a directory if it doesn't exist
     */
    public static boolean createDirectory(String path) {
        try {
            Files.createDirectories(Paths.get(path));
            return true;
        } catch (IOException e) {
            Logger.error("Error creating directory: " + path, e);
            return false;
        }
    }
    
    /**
     * Check if file exists
     */
    public static boolean fileExists(String path) {
        return Files.exists(Paths.get(path));
    }
    
    /**
     * Read file as string
     */
    public static String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            Logger.error("Error reading file: " + path, e);
            return null;
        }
    }
    
    /**
     * Write string to file
     */
    public static boolean writeFile(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes());
            return true;
        } catch (IOException e) {
            Logger.error("Error writing file: " + path, e);
            return false;
        }
    }
    
    /**
     * Append string to file
     */
    public static boolean appendToFile(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes(), 
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            Logger.error("Error appending to file: " + path, e);
            return false;
        }
    }
    
    /**
     * Delete file
     */
    public static boolean deleteFile(String path) {
        try {
            Files.delete(Paths.get(path));
            return true;
        } catch (IOException e) {
            Logger.error("Error deleting file: " + path, e);
            return false;
        }
    }
    
    /**
     * Get file size
     */
    public static long getFileSize(String path) {
        try {
            return Files.size(Paths.get(path));
        } catch (IOException e) {
            Logger.error("Error getting file size: " + path, e);
            return -1;
        }
    }
}
