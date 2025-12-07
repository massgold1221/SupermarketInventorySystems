package util;

import java.io.*;

/**
 * SerializationUtil: Object serialization and deserialization helpers
 */
public class SerializationUtil {
    
    /**
     * Serialize object to byte array
     */
    public static byte[] serialize(Object obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            oos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            Logger.error("Error serializing object", e);
            return null;
        }
    }
    
    /**
     * Deserialize byte array to object
     */
    public static Object deserialize(byte[] data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Logger.error("Error deserializing object", e);
            return null;
        }
    }
    
    /**
     * Serialize object to file
     */
    public static boolean serializeToFile(Object obj, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
            oos.flush();
            return true;
        } catch (IOException e) {
            Logger.error("Error serializing to file: " + filePath, e);
            return false;
        }
    }
    
    /**
     * Deserialize object from file
     */
    public static Object deserializeFromFile(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Logger.error("Error deserializing from file: " + filePath, e);
            return null;
        }
    }
}
